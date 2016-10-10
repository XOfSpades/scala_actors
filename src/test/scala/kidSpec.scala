import akka.testkit.TestActorRef
import scala.concurrent.duration._
import scala.concurrent.Await
import akka.pattern.ask

import akka.actor._

import org.scalatest.FunSuite
import scala.collection.mutable.Stack
 
import kid.Kid
import kid.Feed

class KidSpec extends FunSuite {
 
  test("pop is invoked on a non-empty stack") { 
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    val oldSize = stack.size
    val result = stack.pop()
    assert(result === 2)
    assert(stack.size === oldSize - 1)
  }
 
  test("pop is invoked on an empty stack") {
    val emptyStack = new Stack[Int]
    intercept[NoSuchElementException] {
      emptyStack.pop()
    }
    assert(emptyStack.isEmpty)
  }

  test ("A kid has kidSize after initializing") {
  	implicit val system = ActorSystem()

  	val testKidRef = TestActorRef(new Kid(52))
  	val testKid = testKidRef.underlyingActor

  	assert(testKid.getKidSize == 52)

  	testKidRef ! Feed
  	assert(testKid.getKidSize == 53)
	}
}
