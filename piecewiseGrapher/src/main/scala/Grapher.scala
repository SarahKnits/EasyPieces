import Parser.Parser
import scala.tools.nsc.EvalLoop

object Grapher extends App {
/*  override def prompt = "> "

  loop { line =>
    Parser(line) match {
      case Parser.Success(t, _) =>
        Semantics.Semantics.graph(Semantics.Semantics.eval(t))
      case e: Parser.NoSuccess  => println(e)
    }
  } */
  System.out.println("Enter the name of the file to graph:")
  val fileName: String = Console.readLine()
  val source = scala.io.Source.fromFile(fileName)
  val lines = source.mkString
  source.close()
  Parser(lines) match {
    case Parser.Success(t, _) =>
      Semantics.Semantics.graph(Semantics.Semantics.eval(t))
    case e: Parser.NoSuccess => println(e)
  }
}
