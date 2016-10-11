import akka.testkit.TestActorRef
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask

import akka.actor._

import org.scalatest.FunSuite
 
import kid.Kid
import kid.Feed

class KidSpec extends FunSuite {
  test ("A kid has kidSize after initializing") {
  	implicit val system = ActorSystem()

  	val testKidRef = TestActorRef(new Kid(52))
  	val testKid = testKidRef.underlyingActor

  	assert(testKid.getKidSize == 52)

  	testKidRef ! Feed
  	assert(testKid.getKidSize == 53)
	}
}
