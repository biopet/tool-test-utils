package nl.biopet.utils.test.tools

import java.io.File

import nl.biopet.test.BiopetTest
import nl.biopet.utils.tool.{AbstractOptParser, ToolCommand}
import org.scalatest.exceptions.TestFailedException
import org.testng.annotations.Test

class ToolTestTest extends BiopetTest {

  val loremIpsum: String =
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
      |incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
      |exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
      |dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
      |Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit
      |anim id est laborum.""".stripMargin

  case class Args(num: Int = 0,
                  inputFile: File = null,
                  someBoolean: Boolean = false)

  trait TestTool extends ToolCommand[Args] {
    def main(args: Array[String]): Unit = {}

    def emptyArgs: Args = Args()
  }

  @Test
  def noDescriptionOption(): Unit = {
    class NoDescriptionOption extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") action { (x, c) =>
              c.copy(num = x)
            }
          }
        def descriptionText: String = loremIpsum
        def manualText: String = loremIpsum
        def exampleText: String = loremIpsum
      }
    }

    val noDescriptionTest = new NoDescriptionOption()
    intercept[TestFailedException] {
      noDescriptionTest.testArgs()
    }.getMessage shouldBe "'--num' description: \"\" was empty"
  }

  @Test
  def shortDescriptionOption(): Unit = {
    class ShortDescriptionOption() extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") text "Bla bla" action { (x, c) =>
              c.copy(num = x)
            }
          }
        def descriptionText: String = loremIpsum
        def manualText: String = loremIpsum
        def exampleText: String = loremIpsum
      }
    }

    val shortDescriptionTest = new ShortDescriptionOption()
    intercept[TestFailedException] {
      shortDescriptionTest.testArgs()
    }.getMessage shouldBe "'--num' description: 7 was not greater than 10"
  }

  @Test
  def validTool(): Unit = {
    class ValidOption() extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") text "This is a numeric value" action {
              (x, c) =>
                c.copy(num = x)
            }
            opt[File]('I', "inputFile") required () maxOccurs 1 valueName "<file>" action {
              (x, c) =>
                c.copy(inputFile = x.getAbsoluteFile)
            } validate { x =>
              if (x.exists) success else failure("Input required")
            } text "Input file (required)"
            opt[Unit]("someBoolean") unbounded () action { (_, c) =>
              c.copy(someBoolean = true)
            }
          }
        def descriptionText: String = loremIpsum
        def manualText: String = loremIpsum
        def exampleText: String = loremIpsum
      }

      val validToolTest = new ValidOption()
      validToolTest.testArgs()
      validToolTest.testManual()
      validToolTest.testExample()
      validToolTest.testDescription()
    }
  }

  @Test
  def descriptionTextTooShort(): Unit = {
    class ShortDescriptionText() extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") text "This is a numeric value" action {
              (x, c) =>
                c.copy(num = x)
            }
          }

        def descriptionText = "Way too short."

        def manualText: String = loremIpsum

        def exampleText: String = loremIpsum
      }
    }
    val shortDescriptionTest = new ShortDescriptionText()
    intercept[TestFailedException] {
      shortDescriptionTest.testDescription()
    }.getMessage shouldBe "Description too short: 3 was not greater than or equal to 25"
  }

  @Test
  def descriptionTextTooLong(): Unit = {
    class LongDescriptionText() extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") text "This is a numeric value" action {
              (x, c) =>
                c.copy(num = x)
            }
          }

        def descriptionText: String = loremIpsum * 10

        def manualText: String = loremIpsum

        def exampleText: String = loremIpsum
      }
    }
    val longDescriptionTest = new LongDescriptionText()
    intercept[TestFailedException] {
      longDescriptionTest.testDescription()
    }.getMessage shouldBe "Description too long: 681 was not less than or equal to 250"
  }

  @Test
  def manualTextTooShort(): Unit = {
    class ShortManualText() extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") text "This is a numeric value" action {
              (x, c) =>
                c.copy(num = x)
            }
          }

        def descriptionText: String = loremIpsum

        def manualText: String = "Way too short."

        def exampleText: String = loremIpsum
      }
    }
    val shortManualTest = new ShortManualText()
    intercept[TestFailedException] {
      shortManualTest.testManual()
    }.getMessage shouldBe "Manual too short: 3 was not greater than or equal to 25"
  }

  @Test
  def exampleTextTooShort(): Unit = {
    class ShortExampleText() extends ToolTest[Args] {
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") text "This is a numeric value" action {
              (x, c) =>
                c.copy(num = x)
            }
          }

        def descriptionText: String = loremIpsum

        def manualText: String = loremIpsum

        def exampleText = "Way too short."
      }
    }
    val shortExampleTest = new ShortExampleText()
    intercept[TestFailedException] {
      shortExampleTest.testExample()
    }.getMessage shouldBe "Example too short: 3 was not greater than or equal to 25"
  }

  @Test
  def overrideTextTooShort(): Unit = {
    class ShortDocumentation() extends ToolTest[Args] {
      override def minManualWords = 3
      override def minExampleWords = 3
      override def minDescriptionWords = 3
      def toolCommand: ToolCommand[Args] = new TestTool {
        def argsParser: AbstractOptParser[Args] =
          new AbstractOptParser[Args](this) {
            opt[Int]('n', "num") text "This is a numeric value" action {
              (x, c) =>
                c.copy(num = x)
            }
          }

        def descriptionText: String = "Way too short."

        def manualText: String = "Way too short."

        def exampleText: String = "Way too short."
      }
    }
    val overrideShortDocumentationTest = new ShortDocumentation()

    overrideShortDocumentationTest.testManual()
    overrideShortDocumentationTest.testExample()
    overrideShortDocumentationTest.testDescription()
  }

}
