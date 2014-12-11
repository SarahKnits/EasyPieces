import Parser._

import scala.io.StdIn

object Grapher extends App {
  System.out.println("Enter the name of the file to graph:")
  val fileName: String = StdIn.readLine()
  try {
    val source = scala.io.Source.fromFile(fileName)
    val lines = source.mkString
    source.close()
    Parser(lines) match {
      case Parser.Success(t, _) =>
        Semantics.Semantics.graph(Semantics.Semantics.eval(t))
      case e: Parser.NoSuccess => println(e)
    }
  }
  catch {
    case ex: PGException => println(Console.RED + ex.toString + Console.RESET)
    case ex: Exception => println(Console.BLUE + "Unable to open file. Please verify that the file exists and try again." + Console.RESET)
  }
}
