package kid

import akka.actor._

// case classes provide a mechanism for pattern matching
case object Poke
case object Feed

class Kid(initialSize :Int) extends Actor {
  var kidSize = initialSize

	def receive = {
		case Feed => {
			println("Yummy, yummy! ... BURP!")
			kidSize += 1
		}
		case Poke => {
			println("Ow! STOP THAT!!!")
		}
		case "size" => {
			println("Current size is " + kidSize)
		}
	}

	def getKidSize:Int = {
		return kidSize
	}
}
