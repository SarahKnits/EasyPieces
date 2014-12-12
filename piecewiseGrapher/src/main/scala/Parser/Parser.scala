package Parser

import scala.util.parsing.combinator._
import IR._

object Parser extends JavaTokenParsers with PackratParsers {

  var lineNumber = 0

  def reset() = lineNumber = 0

  // Starts the parser with command
  def apply(s: String): ParseResult[AST] = parseAll(program, s)

  lazy val program: PackratParser[Function] =
    options~function ^^ {case o~f => PGData(o, f)}

  lazy val function: PackratParser[Function] =
    (functionName~openParens~variable~closeParens~equals~curly~expression~comma~bounds~function ^^ {case fn~op~v~cp~eq~c~e~co~b~f => PGFunction(b,fn,v,e,Some(f))}
      | functionName~openParens~variable~closeParens~equals~curly~expression~comma~bounds ^^ {case fn~op~v~cp~eq~c~e~co~b => PGFunction(b,fn,v,e,None)})

  lazy val openParens: PackratParser[Function] =
    ("(" ^^ {case "(" => PGVariable("{")}
      | "" ^^ {case x => throw PGException("Error: Missing ( in function line " + lineNumber)})

  lazy val closeParens: PackratParser[Function] =
    (")" ^^ {case ")" => PGVariable("{")}
      | "" ^^ {case x => throw PGException("Error: Missing ) in function line " + lineNumber)})

  lazy val equals: PackratParser[Function] =
    ("=" ^^ {case "=" => PGVariable("{")}
      | "" ^^ {case x => throw PGException("Error: Missing = in function line " + lineNumber)})

  lazy val curly: PackratParser[Function] =
    ("{" ^^ {case "{" => PGVariable("{")}
      | "" ^^ {case x => throw PGException("Error: Missing { in function line " + lineNumber)})

  lazy val comma: PackratParser[Function] =
    ("," ^^ {case "," => PGVariable(",")}
      | "" ^^ {case x => throw PGException("Error: Missing , in function line " + lineNumber)})

  lazy val quote: PackratParser[Function] =
    ("\"" ^^ {case "\"" => PGVariable("\"")}
    | "" ^^ {case x => throw PGException("Error: Missing \"")})

  lazy val options: PackratParser[Function] =
    ("Filename:" ~ quote ~ string2 ~ quote ~options ^^ {case "Filename:" ~ q1 ~ v ~ q2 ~PGOptions(a,b,c,d,e,f) => PGOptions(v,b,c,d,e,f)}
      | "Title:" ~ quote ~ string2 ~ quote ~ options ^^ {case "Title:" ~ q1 ~ v ~ q2 ~PGOptions(a,b,c,d,e,f) => PGOptions(a,v,c,d,e,f)}
      | "xLabel:" ~ quote ~ string2 ~ quote ~ options ^^ {case "xLabel:" ~ q1 ~ v ~ q2 ~PGOptions(a,b,c,d,e,f) => PGOptions(a,b,v,d,e,f)}
      | "yLabel:" ~ quote ~ string2 ~ quote ~ options ^^ {case "yLabel:" ~ q1 ~ v ~ q2 ~PGOptions(a,b,c,d,e,f) => PGOptions(a,b,c,v,e,f)}
      | "Location:"~ quote ~ string2 ~ quote ~ options ^^ {case "Location:"~ q1 ~ v ~ q2 ~PGOptions(a,b,c,d,e,f) => PGOptions(a,b,c,d,v,f)}
      | "Format:"~ quote ~ string2 ~ quote ~ options ^^ {case "Format:"~ q1 ~ v ~ q2 ~PGOptions(a,b,c,d,e,f) => PGOptions(a,b,c,d,e,v)}
      | "" ^^ {case "" => PGOptions(PGVariable("GraphDemo"), PGVariable("Graph"), PGVariable("x"), PGVariable("y"), PGVariable("docs/img/"), PGVariable("PNG"))})

  lazy val string2: PackratParser[Function] =
    ("""[\w\s_/]*""".r ^^ {case x => PGVariable(x)}
      | "" ^^ {case x => throw PGException("Invalid input")})

  lazy val functionName: PackratParser[Function] =
    ("""[A-Za-z_]\w*""".r ^^ {case x => lineNumber = lineNumber + 1; PGFunctionName(x)}
      | """.+""".r ^^ {case x => lineNumber = lineNumber + 1; throw PGException("Error: Invalid character in function name, line " + lineNumber)})

  lazy val variable: PackratParser[Function] =
    ("""[A-Za-z_]\w*""".r ^^ {case x => PGVariable(x)})

  lazy val comparator: PackratParser[Function] =
    ("<"~"=" ^^ {case "<"~"=" => PGComparator("<=")}
      | "<" ^^ {case "<" => PGComparator("<")}
      | "" ^^ {case x => throw PGException("Error: Invalid comparator in function line " + lineNumber)})

  lazy val bounds: PackratParser[Function] =
    (expression2~comparator~variable~comparator~expression2 ^^ {case l~comp1~v~comp2~m => PGBounds(l, comp1, v, comp2, m)}
      | variable~"="~expression2 ^^ {case n~"="~v => PGBounds(n, PGComparator("="), v, PGComparator("="), n)})

  lazy val number: PackratParser[Function] =
    (floatingPointNumber ^^ {case x => PGNumber(x.toDouble)}
      | "pi" ^^ {case "pi" => PGNumber(Math.PI)}
      | "π" ^^ {case "π" => PGNumber(Math.PI)}
      | "e" ^^ {case "e" => PGNumber(Math.E)})

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
      | number~variable ^^ {case n~v => PGExpression(n, "*", v)}
      | number ^^ {case n => n}
      | variable ^^ {case v => v}
      | "" ^^ {case x => throw PGException("Error: Invalid expression in function line " + lineNumber)})

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
      | number ^^ {case n => n}
      | "" ^^ {case x => throw PGException("Error: Invalid expression in function line " + lineNumber)})
}
