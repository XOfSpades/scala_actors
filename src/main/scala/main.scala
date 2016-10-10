package runtime

import akka.actor._
import kid.Kid
import kid.Poke
import kid.Feed

object Main extends App {

	val system = ActorSystem("KidsActorSystem")

  val kid1 = system.actorOf(Props[Kid], name = "Kid1")
  val kid2 = system.actorOf(Props[Kid], name = "Kid2")			

	kid1 ! Poke
	kid2 ! Feed
	kid1 ! Feed
	kid2 ! Poke
	kid1 ! Poke
	kid2 ! Feed
	kid1 ! Feed
	kid2 ! Poke

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
