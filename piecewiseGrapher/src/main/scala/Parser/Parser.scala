package Parser

import scala.util.parsing.combinator._
import IR._

object Parser extends JavaTokenParsers with PackratParsers {

  // Starts the parser with command
  def apply(s: String): ParseResult[AST] = parseAll(function, s)

  lazy val function: PackratParser[Function] =
    (functionName~"("~variable~")"~"="~"{"~expression~","~bounds~function ^^ {case fn~"("~v~")"~"="~"{"~e~","~b~f => PGFunction(b,fn,v,e,Some(f))}
      | functionName~"("~variable~")"~"="~"{"~expression~","~bounds ^^ {case fn~"("~v~")"~"="~"{"~e~","~b => PGFunction(b,fn,v,e,None)})

  lazy val functionName: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PGFunctionName(x)}

  lazy val variable: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PGVariable(x)}

  lazy val comparator: PackratParser[Function] =
    ("<"~"=" ^^ {case "<"~"=" => PGComparator("<=")}
      | "<" ^^ {case "<" => PGComparator("<")})

  lazy val bounds: PackratParser[Function] =
    (expression~comparator~variable~comparator~expression ^^ {case l~comp1~v~comp2~m => PGBounds(l, comp1, v, comp2, m)}
      | expression~"="~variable ^^ {case n~"="~v => PGBounds(n, PGComparator("="), v, PGComparator("="), n)})

  lazy val number: PackratParser[Function] =
    floatingPointNumber ^^ {case x => PGNumber(x.toDouble)}

  lazy val expression: PackratParser[Function] =
    (expression~"+"~expression ^^ {case e~"+"~e2 => PGExpression(e, "+", e2)}
      | expression~"-"~expression ^^ {case e~"-"~e2 => PGExpression(e, "-", e2)}
      | expression~"*"~expression ^^ {case e~"*"~e2 => PGExpression(e, "*", e2)}
      | expression~"/"~expression ^^ {case e~"/"~e2 => PGExpression(e, "/", e2)}
      | expression~"^"~expression ^^ {case e~"^"~n => PGExpression(e, "^", n)}
      | "("~expression~")" ^^ {case "("~e~")" => PGParens(e)}
      | "abs("~expression~")" ^^ {case "abs("~e~")" => PGSingleApply("abs", e)}
      | "sqrt("~expression~")" ^^ {case "sqrt("~e~")" => PGSingleApply("sqrt", e)}
      | "sin("~expression~")" ^^ {case "sin("~e~")" => PGSingleApply("sin", e)}
      | "cos("~expression~")" ^^ {case "cos("~e~")" => PGSingleApply("cos", e)}
      | "pi" ^^ {case "pi" => PGNumber(3.14159)}
      | "e" ^^ {case "e" => PGNumber(2.7172)}
      | number ^^ {case PGNumber(n) => PGNumber(n)}
      | variable ^^ {case PGVariable(v) => PGVariable(v)})
}
