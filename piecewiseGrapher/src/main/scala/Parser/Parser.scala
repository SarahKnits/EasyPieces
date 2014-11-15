package Parser

import scala.util.parsing.combinator._
import IR._

object Parser extends JavaTokenParsers with PackratParsers {

  // Starts the parser with command
  def apply(s: String): ParseResult[AST] = parseAll(function, s)

  lazy val function: PackratParser[Function] =
    functionName~"("~variable~")"~"="~"{"~expression~","~bounds ^^ {case fn~"("~v~")"~"="~"{"~e~","~b => PGFunction(b,fn,v,e)}

  lazy val functionName: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PGFunctionName(x)}

  lazy val variable: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PGVariable(x)}

  lazy val bounds: PackratParser[Function] =
    (variable~"<="~number ^^ {case v~"<="~n => PGBounds(v,"<=", n)}
      | variable~"<"~number ^^ {case v~"<"~n => PGBounds(v,"<", n)}
      | variable~">="~number ^^ {case v~">="~n => PGBounds(v,">=", n)}
      | variable~">"~number ^^ {case v~">"~n => PGBounds(v,">", n)}
      | variable~"="~number ^^ {case v~"="~n => PGBounds(v,"=", n)}
      | number~"<="~variable ^^ {case n~"<="~v => PGBounds(v,">=", n)}
      | number~"<"~variable ^^ {case n~"<"~v => PGBounds(v,">", n)}
      | number~">="~variable ^^ {case n~">="~v => PGBounds(v,"<=", n)}
      | number~">"~variable ^^ {case n~">"~v => PGBounds(v,"<", n)}
      | number~"="~variable ^^ {case n~"="~v => PGBounds(v,"=", n)})

  lazy val number: PackratParser[Function] =
    """[0-9]\w*""".r ^^ {case x => PGNumber(x.toInt)}

  lazy val expression: PackratParser[Function] =
    (expression~"*"~expression ^^ {case e~"*"~e2 => PGExpression(e, "*", e2)}
      | expression~"+"~expression ^^ {case e~"+"~e2 => PGExpression(e, "+", e2)}
      | expression~"-"~expression ^^ {case e~"-"~e2 => PGExpression(e, "-", e2)}
      | expression~"/"~expression ^^ {case e~"/"~e2 => PGExpression(e, "/", e2)}
      | expression~"^"~number ^^ {case e~"^"~n => PGExpression(e, "^", n)}
      | number ^^ {case PGNumber(n) => PGNumber(n)}
      | variable ^^ {case PGVariable(v) => PGVariable(v)})

}

