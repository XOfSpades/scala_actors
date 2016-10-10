package kid

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
