package kid

import akka.actor._

// case classes provide a mechanism for pattern matching
object Kid {
  case object Poke
  case object Feed

	def probs(initialSize: Int) :Props = Props(classOf[Kid], initialSize)
}

class Kid(initialSize :Int) extends Actor {
  var kidSize = initialSize

	def receive = {
		case Kid.Feed => {
			println("Yummy, yummy! ... BURP!")
			kidSize += 1
		}
		case Kid.Poke => {
			println("Ow! STOP THAT!!!")
		}
		case "size" => {
			println("Current size is " + getKidSize)
		}
	}

	def getKidSize:Int = {
		return kidSize
	}
}
