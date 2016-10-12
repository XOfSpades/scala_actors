package human

import akka.testkit.{ TestActorRef, TestActors, TestKit, ImplicitSender }
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask

import akka.actor.{ ActorSystem, Actor, Props }

import org.scalatest.{ WordSpecLike, Matchers, BeforeAndAfterAll }

class KidSpec extends TestKit(ActorSystem("KidSpec")) with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll {
 
  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }
 
  "A kid" must {
 
    "have a kidsize after initializing" in {
      val testKidRef = TestActorRef(new Kid(52))
      val testKid = testKidRef.underlyingActor

      assert(testKid.getKidSize == 52)
    }

    "grow when feeded" in {
	    val testKidRef = TestActorRef(new Kid(52))
	    val testKid = testKidRef.underlyingActor

	    testKidRef ! Kid.Feed
	    assert(testKid.getKidSize == 53)
    }
    
    "menance the sender when poked" in {
    	val testKidRef = TestActorRef(new Kid(52))
	    val testKid = testKidRef.underlyingActor

	    testKidRef ! Kid.Poke

	    expectMsg(
	      Adult.Menance("Stop that or I will sue you. This is child abuse!!!")
	    )
    } 
  }
}
