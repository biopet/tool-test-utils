package nl.biopet.utils.test.tools

import java.io.File

import nl.biopet.test.BiopetTest
import nl.biopet.utils.tool.{AbstractOptParser, ToolCommand}
import org.testng.annotations.Test

class ToolTestTest extends BiopetTest {

  case class Args(num: Int = 0,
                  inputFile: File = null)

  object Tool extends ToolCommand[Args] {
    def main(args: Array[String]): Unit = ???

    def argsParser: AbstractOptParser[Args] = new AbstractOptParser[Args]("test") {
      opt[Int]('n', "num") action { (x,c) => c.copy(num = x) }
      opt[File]('I', "inputFile") required () unbounded () maxOccurs 1 valueName "<file>" action {
        (x, c) =>
          c.copy(inputFile = x.getAbsoluteFile)
      } validate { x =>
        if (x.exists) success else failure("Input file required")
      } text "Input file (required)"
    }

    def emptyArgs: Args = Args()
  }

  @Test
  def test(): Unit = {
    ToolTest
  }
}
