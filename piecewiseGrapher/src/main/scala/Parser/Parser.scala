package Parser

import scala.util.parsing.combinator._
import IR._

object Parser extends JavaTokenParsers with PackratParsers {

  // Starts the parser with command
  def apply(s: String): ParseResult[AST] = parseAll(function, s)

  lazy val function: PackratParser[Function] =
    functionName~"("~variable~")"~"="~"{"~bounds~","~expression ^^ {case fn~"("~v~")"~"="~"{"~b~","~e => PicoFunction(b,fn,v,e)}

  lazy val functionName: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PicoFunctionName(x)}

  lazy val variable: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PicoVariable(x)}

  lazy val bounds: PackratParser[Function] =
    (variable~"<="~number ^^ {case v~"<="~n => PicoBounds(v,"<=", n)}
      | variable~"<"~number ^^ {case v~"<"~n => PicoBounds(v,"<", n)}
      | variable~">="~number ^^ {case v~">="~n => PicoBounds(v,">=", n)}
      | variable~">"~number ^^ {case v~">"~n => PicoBounds(v,">", n)}
      | number~"<="~variable ^^ {case n~"<="~v => PicoBounds(v,">=", n)}
      | number~"<"~variable ^^ {case n~"<"~v => PicoBounds(v,">", n)}
      | number~">="~variable ^^ {case n~">="~v => PicoBounds(v,"<=", n)}
      | number~">"~variable ^^ {case n~">"~v => PicoBounds(v,"<", n)})

  lazy val number: PackratParser[Function] =
    """[0-9]\w*""".r ^^ {case x => PicoNumber(x.toInt)}

  lazy val expression: PackratParser[Function] =
    (expression~"*"~expression ^^ {case e~"*"~e2 => PicoExpression(e, "*", e2)}
      | expression~"+"~expression ^^ {case e~"+"~e2 => PicoExpression(e, "+", e2)}
      | expression~"-"~expression ^^ {case e~"-"~e2 => PicoExpression(e, "-", e2)}
      | expression~"/"~expression ^^ {case e~"/"~e2 => PicoExpression(e, "/", e2)}
      | expression~"^"~number ^^ {case e~"^"~n => PicoExpression(e, "^", n)}
      | number ^^ {case PicoNumber(n) => PicoNumber(n)}
      | variable ^^ {case PicoVariable(v) => PicoVariable(v)})

}

