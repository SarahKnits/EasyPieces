package Parser

case class PGException(Throwable: String) extends Exception(Throwable: String) {
  override def fillInStackTrace() = this
  override def toString() = Throwable
}
