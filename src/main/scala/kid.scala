package kid

import akka.actor.Props
import akka.actor.Actor
import akka.event.Logging

// case classes provide a mechanism for pattern matching
object Kid {
  case object Poke
  case object Feed

	def probs(initialSize: Int) :Props = Props(classOf[Kid], initialSize)
	def defaultProps() :Props = Props(classOf[Kid])
}

class Kid(initialSize :Int) extends Actor {
	// Setting defaults can only be achieved by overriding te constructor. 
	// Using Kid(initialSize :Int = 53) will cause an exception (WTF???)
  def this() {
  	this(53)
  }

	val log = Logging(context.system, this)
	// import context._

  var kidSize = initialSize

	def receive = {
		case Kid.Feed => {
			log.info("Kid got feeded.")
			println("Yummy, yummy! ... BURP!")
			kidSize += 1
		}
		case Kid.Poke => {
			log.info("Kid was poked.")
			println("Ow! STOP THAT!!!")
		}
		case "size" => {
			log.info("Check kids size.")
			println("Current size is " + getKidSize)
		}
		case _ => {
			log.error("Unknown message received")
		}
	}

  // def getPath {
  // 	var kid1Path = context.actorSelection("/user/Kid1Supervisor/Kid1")
  //   println(kid1Path)
  // }

	def getKidSize:Int = {
		return kidSize
	}
}
