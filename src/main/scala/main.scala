package runtime

import akka.actor._
import akka.pattern._
import scala.concurrent.duration.DurationInt
import human.{ Kid, Adult } 

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

  val supervisor = BackoffSupervisor.props(
  	backoffBuilder("Adult1", Adult.props())
  )

  val adult = system.actorOf(supervisor, "AdultSupervisor")

  adult ! Adult.MakeKid
  adult ! Adult.CountKids
  adult ! Adult.PokeKids
  adult ! Adult.FeedKids
  adult ! Adult.FeedKids
  adult ! Adult.MakeKid
  adult ! Adult.CountKids
  adult ! Adult.PokeKids
  adult ! Adult.FeedKids
  adult ! Adult.FeedKids
  adult ! Adult.PokeKids
  adult ! Adult.MeasureKids
  // adult ! Adult.KillKids

	Thread.sleep(1000)

	system.shutdown
}
