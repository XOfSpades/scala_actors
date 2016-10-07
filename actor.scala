import akka.actor._
// import scala.concurrent.duration._
// import system.dispatcher

// case classes provide a mechanism for pattern matching
case object Poke
case object Feed

class Kid extends Actor {
	def receive = {
		case Feed => {
			println("Yummy, yummy! ... BURP!")
		}
		case Poke => {
			println("Ow! STOP THAT!!!")
		}
	}
}

object ActorTest {

  def main(args: Array[String]) {
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

		Thread.sleep(5000)

		system.shutdown
	}
}