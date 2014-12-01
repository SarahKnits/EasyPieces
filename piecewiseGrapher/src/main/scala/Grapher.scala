import Parser.Parser
import scala.tools.nsc.EvalLoop

object Grapher extends App {
  System.out.println("Enter the name of the file to graph:")
  val fileName: String = Console.readLine()
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
    case ex: Exception => println("Unable to open file.")
  }
}
