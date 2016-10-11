package runtime

import akka.actor._
import akka.actor.Props
import akka.pattern._
import akka.pattern.BackoffOptionsImpl
import akka.pattern.Backoff
import scala.concurrent.duration.DurationInt
import kid.Kid
import kid.Poke
import kid.Feed

object Main extends App {

	val system = ActorSystem("KidsActorSystem")

	val kid1Props = Props(new Kid(52))
	val kid2Props = Props(new Kid(54))

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
  	backoffBuilder("Kid1", kid1Props)
  )

  val supervisor2 = BackoffSupervisor.props(
  	backoffBuilder("Kid2", kid2Props)
  )

  val kid1 = system.actorOf(supervisor1, "Kid1Supervisor")
  val kid2 = system.actorOf(supervisor2, "Kid2Supervisor")

	kid1 ! Poke
	kid2 ! Feed
	kid1 ! Feed
	kid2 ! Poke
	kid1 ! Poke
	kid2 ! Feed
	kid1 ! Feed
	kid2 ! Poke
	kid1 ! "size"
	kid2 ! "size"

	// val caller = self
	// val words = List("Hey", "Ho", "Let's", "Go")
	// for (word <- words) {
	// 	actor { caller ! println(word) }
	// }

	// system.scheduler.scheduleOnce(500 milliseconds, kid1, Poke)
	// system.scheduler.scheduleOnce(500 milliseconds, kid1, Poke)
	// system.scheduler.scheduleOnce(500 milliseconds, kid1, Poke)
	// system.scheduler.scheduleOnce(500 milliseconds, kid1, Poke)

	Thread.sleep(1000)

	system.shutdown
}
