package nl.biopet.utils.test.tools

import java.io.File

import nl.biopet.test.BiopetTest
import nl.biopet.utils.tool.{AbstractOptParser, ToolCommand}
import org.scalatest.exceptions.TestFailedException
import org.testng.annotations.Test

class ToolTestTest extends BiopetTest {

  case class Args(num: Int = 0,
                  inputFile: File = null)

  trait TestTool extends ToolCommand[Args] {
    def main(args: Array[String]): Unit = ???

    def emptyArgs: Args = Args()
  }

  @Test
  def noDescriptionOption(): Unit = {
    class NoDescriptionOption extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] = new AbstractOptParser[Args]("test") {
          opt[Int]('n', "num") action { (x,c) => c.copy(num = x) }
        }
      }
    }

    val noDescriptionTest = new NoDescriptionOption()
    intercept[TestFailedException] {
      noDescriptionTest.testArgs()
    }.getMessage shouldBe "\"\" was empty"
  }

  @Test
  def shortDescriptionOption(): Unit = {
    class ShortDescriptionOption() extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] = new AbstractOptParser[Args]("test") {
          opt[Int]('n', "num") text "Bla bla" action { (x,c) => c.copy(num = x) }
        }
      }
    }

    val shortDescriptionTest = new ShortDescriptionOption()
    intercept[TestFailedException] {
      shortDescriptionTest.testArgs()
    }.getMessage shouldBe "7 was not greater than 10"
  }
}
