package nl.biopet.utils.test.tools

import nl.biopet.test.BiopetTest
import nl.biopet.utils.tool.ToolCommand
import org.testng.annotations.Test

trait ToolTest[T] extends BiopetTest {
  def toolCommand: ToolCommand[T]

  @Test
  def testArgs(): Unit = {

    for (option <- toolCommand.argsParser.optionsForRender) {
      withClue(s"'${option.fullName}' description: ") {
        option.desc should not be empty
        option.desc.length() should be > 10
      }
    }
  }
}
