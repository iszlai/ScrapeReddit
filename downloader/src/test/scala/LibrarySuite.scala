/*
 * This Scala Testsuite was auto generated by running 'gradle init --type scala-library'
 * by 'leheli' at '7/19/15 1:46 PM' with Gradle 2.5
 *
 * @author leheli, @date 7/19/15 1:46 PM
 */

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.scrape.config.DBConfig

@RunWith(classOf[JUnitRunner])
class LibrarySuite extends FunSuite {
  test("someLibraryMethod is always true") {
    def library = new DBConfig()
    assert(library.someLibraryMethod)
  }
}