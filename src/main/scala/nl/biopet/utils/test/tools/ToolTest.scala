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
  @Test
  def testDocs(): Unit = {
    val descriptionWords = toolCommand.descriptionText.split("\\s+").length
    val manualWords = toolCommand.manualText.split("\\s+").length
    val exampleWords = toolCommand.exampleText.split("\\s+").length

    descriptionWords should be >= 25
    descriptionWords should be <= 250

    manualWords should be >= 25
    exampleWords should be >= 25
  }
}
