package human

import akka.actor.{ Props, Actor, Terminated }
import akka.event.Logging
import human.Kid

object Adult {
	case object MakeKid
	case object FeedKids
	case object PokeKids
	case object MeasureKids
	case object CountKids
	case object KillKids

	def props() :Props = Props(classOf[Adult])
}

class Adult extends Actor {
  val log = Logging(context.system, this)

	def receive = {
		case Adult.MakeKid => {
			log.info("Adult received MakeChild")
			val kid = context.actorOf(Kid.defaultProps())
			context.watch(kid)
		}
		case Adult.FeedKids => {
			doForAllChildren(Kid.Feed)
		}
		case Adult.PokeKids => {
			doForAllChildren(Kid.Poke)
		}
		case Adult.MeasureKids => {
			doForAllChildren(Kid.Size)
		}
		case Adult.KillKids => {
			log.info ("Adult received KillKids")
			context.children.foreach {
				kid => context.stop(kid)
			}
		}
		case Adult.CountKids => {
			println("I have " + context.children.size + " kids")
		}
		case Terminated(reference) => println("Killed my kid: " + reference)
		case _ => log.error("Human received unknown message")
	}

	def doForAllChildren(action: AnyRef) {
		log.info("Adult takes action " + action)
		context.children foreach { 
		 	kid => kid ! action
		}
	}
}