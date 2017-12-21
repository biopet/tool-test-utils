package nl.biopet.utils.test.tools

import nl.biopet.test.BiopetTest
import nl.biopet.utils.tool.ToolCommand
import org.testng.annotations.Test

trait ToolTest[T] extends BiopetTest {
  def toolCommand: ToolCommand[T]

  def minDescriptionWords: Int = 25
  def maxDescriptionWords: Int = 250
  def minManualWords: Int = 25
  def minExampleWords: Int = 25

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
  def testExample(): Unit = {
    val exampleWords = toolCommand.exampleText.split("\\s+").length

    withClue("Example too short: ") {
      exampleWords should be >= minExampleWords
    }
  }

  @Test
  def testDescription(): Unit = {
    val descriptionWords = toolCommand.descriptionText.split("\\s+").length
    withClue("Description too short: ") {
      descriptionWords should be >= minDescriptionWords
    }
    withClue("Description too long: ") {
      descriptionWords should be <= maxDescriptionWords
    }
  }

  @Test
  def testManual(): Unit = {
    val manualWords = toolCommand.manualText.split("\\s+").length
    withClue("Manual too short: ") {
      manualWords should be >= minManualWords
    }
  }



}
