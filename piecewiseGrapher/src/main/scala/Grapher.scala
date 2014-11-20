import Parser.Parser
import scala.tools.nsc.EvalLoop

object Grapher extends EvalLoop with App {
  override def prompt = "> "

  loop { line =>
    Parser(line) match {
      case Parser.Success(t, _) =>
        Semantics.Semantics.graph(Semantics.Semantics.eval(t))
      case e: Parser.NoSuccess  => println(e)
    }
  }
}
