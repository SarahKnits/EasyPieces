package Parser
/**
 * Created by Sarah Gilkinson on 11/30/14.
 */

case class PGException(Throwable: String) extends Exception(Throwable: String) {
  override def fillInStackTrace() = this
}
