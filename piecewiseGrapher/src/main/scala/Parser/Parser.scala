package Parser

import scala.util.parsing.combinator._
import IR._

object Parser extends JavaTokenParsers with PackratParsers {

  // Starts the parser with command
  def apply(s: String): ParseResult[AST] = parseAll(program, s)

  lazy val program: PackratParser[Function] =
    options~function ^^ {case o~f => PGData(o, f)}

  lazy val function: PackratParser[Function] =
    (functionName~"("~variable~")"~"="~"{"~expression~","~bounds~function ^^ {case fn~"("~v~")"~"="~"{"~e~","~b~f => PGFunction(b,fn,v,e,Some(f))}
      | functionName~"("~variable~")"~"="~"{"~expression~","~bounds ^^ {case fn~"("~v~")"~"="~"{"~e~","~b => PGFunction(b,fn,v,e,None)})

  lazy val options: PackratParser[Function] =
    ("Filename:" ~ "\"" ~ string2 ~ "\""~options ^^ {case "Filename:" ~ "\"" ~ v ~ "\""~PGOptions(a,b,c,d) => PGOptions(v,b,c,d)}
      | "Title:" ~ "\"" ~ string2 ~ "\"" ~ options ^^ {case "Title:" ~ "\"" ~ v ~ "\""~PGOptions(a,b,c,d) => PGOptions(a,v,c,d)}
      | "xLabel:" ~ "\"" ~ string2 ~ "\"" ~ options ^^ {case "xLabel:" ~ "\"" ~ v ~ "\""~PGOptions(a,b,c,d) => PGOptions(a,b,v,d)}
      | "yLabel:" ~ "\"" ~ string2 ~ "\"" ~ options ^^ {case "yLabel:" ~ "\"" ~ v ~ "\""~PGOptions(a,b,c,d) => PGOptions(a,b,c,v)}
      | "" ^^ {case "" => PGOptions(PGVariable("GraphDemo"), PGVariable("Graph"), PGVariable("x"), PGVariable("y"))})

  lazy val string2: PackratParser[Function] =
    """[\w\s_]+""".r ^^ {case x => PGVariable(x)}

  lazy val functionName: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PGFunctionName(x)}

  lazy val variable: PackratParser[Function] =
    """[A-Za-z_]\w*""".r ^^ {case x => PGVariable(x)}

  lazy val comparator: PackratParser[Function] =
    ("<"~"=" ^^ {case "<"~"=" => PGComparator("<=")}
      | "<" ^^ {case "<" => PGComparator("<")})

  lazy val bounds: PackratParser[Function] =
    (expression2~comparator~variable~comparator~expression2 ^^ {case l~comp1~v~comp2~m => PGBounds(l, comp1, v, comp2, m)}
      | variable~"="~expression2 ^^ {case n~"="~v => PGBounds(n, PGComparator("="), v, PGComparator("="), n)})

  lazy val number: PackratParser[Function] =
    (floatingPointNumber ^^ {case x => PGNumber(x.toDouble)}
      | "pi" ^^ {case "pi" => PGNumber(Math.PI)})

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
      | "ln("~expression~")" ^^ {case "ln("~e~")" => PGSingleApply("ln", e)}
      | "log("~expression~")" ^^ {case "log("~e~")" => PGSingleApply("log", e)}
      | "pi" ^^ {case "pi" => PGNumber(Math.PI)}
      | "e" ^^ {case "e" => PGNumber(Math.E)}
      | number~variable ^^ {case n~v => PGExpression(n, "*", v)}
      | number ^^ {case PGNumber(n) => PGNumber(n)}
      | variable ^^ {case PGVariable(v) => PGVariable(v)}
      | string2 ^^ {case PGVariable(x) => throw PGException("Invalid expression: ")})

  lazy val expression2: PackratParser[Function] =
    (expression2~"+"~expression2 ^^ {case e~"+"~e2 => PGExpression(e, "+", e2)}
      | expression2~"-"~expression2 ^^ {case e~"-"~e2 => PGExpression(e, "-", e2)}
      | expression2~"*"~expression2 ^^ {case e~"*"~e2 => PGExpression(e, "*", e2)}
      | expression2~"/"~expression2 ^^ {case e~"/"~e2 => PGExpression(e, "/", e2)}
      | expression2~"^"~expression2 ^^ {case e~"^"~n => PGExpression(e, "^", n)}
      | "("~expression2~")" ^^ {case "("~e~")" => PGParens(e)}
      | "abs("~expression2~")" ^^ {case "abs("~e~")" => PGSingleApply("abs", e)}
      | "sqrt("~expression2~")" ^^ {case "sqrt("~e~")" => PGSingleApply("sqrt", e)}
      | "sin("~expression2~")" ^^ {case "sin("~e~")" => PGSingleApply("sin", e)}
      | "cos("~expression2~")" ^^ {case "cos("~e~")" => PGSingleApply("cos", e)}
      | "ln("~expression2~")" ^^ {case "ln("~e~")" => PGSingleApply("ln", e)}
      | "log("~expression2~")" ^^ {case "log("~e~")" => PGSingleApply("log", e)}
      | "pi" ^^ {case "pi" => PGNumber(Math.PI)}
      | "e" ^^ {case "e" => PGNumber(Math.E)}
      | number ^^ {case PGNumber(n) => PGNumber(n)})
}
