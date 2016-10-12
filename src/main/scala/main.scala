package runtime

import akka.actor._
import akka.pattern._
import scala.concurrent.duration.DurationInt
import kid.Kid

object Main extends App {

	val system = ActorSystem("KidsActorSystem")

	/*
	You can watch other actor with ActorContext.watch(targetActorRef). 
	To watching listening, invoke ActorContext.unwatch(targetActorRef)
	*/

  val backoffBuilder = 
    (childName: String, childProbs: Props) => {
	  	Backoff.onStop(
	  		childProps = childProbs,
	  		childName = childName,
	  		minBackoff = 1.second,
	  		maxBackoff = 16.seconds,
	  		randomFactor = 0.0 // creates some noise to vary intervals (in %)
	  	)
	  }

  val supervisor1 = BackoffSupervisor.props(
  	backoffBuilder("Kid1", Kid.probs(52))
  )

  val supervisor2 = BackoffSupervisor.props(
  	backoffBuilder("Kid2", Kid.defaultProps())
  )

  val kid1 = system.actorOf(supervisor1, "Kid1Supervisor")
  val kid2 = system.actorOf(supervisor2, "Kid2Supervisor")

	kid1 ! Kid.Poke
	kid2 ! Kid.Feed
	kid1 ! Kid.Feed
	kid2 ! Kid.Poke
	kid1 ! Kid.Poke
	kid2 ! Kid.Feed
	kid1 ! Kid.Feed
	kid2 ! Kid.Poke
	kid1 ! "size"
	kid2 ! "size"

	Thread.sleep(1000)

	system.shutdown
}
