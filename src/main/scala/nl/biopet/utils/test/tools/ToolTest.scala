/*
 * Copyright (c) 2014 Biopet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.biopet.utils.test.tools

import nl.biopet.test.BiopetTest
import nl.biopet.utils.tool.ToolCommand
import org.testng.annotations.Test

trait ToolTest[T] extends BiopetTest {
  def toolCommand: ToolCommand[T]

  /**
    * This function sets the minimal amount of words that should be in the tool description
    * @return the amount of words: Int
    */
  def minDescriptionWords: Int = 25

  /**
    * This function sets the maximal amount of words that should be in the tool description
    * @return the amount of words: Int
    */
  def maxDescriptionWords: Int = 250

  /**
    * This function sets the minimal amount of words that should be in the tool manual
    * @return the amount of words: Int
    */
  def minManualWords: Int = 25

  /**
    * This function sets the minimal amount of words that should be in the tool example
    * @return the amount of words: Int
    */
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
