package IR

/**
 * Intermediate representation for Piecewise Grapher
 * Sarah Gilkinson
 *
 * f ∈ Function ::= fn, "(", v, ")", "=", "{" b, ",", e
 * b ∈ Bounds ::= v, ">", n | v, ">=", n | v, "<", n | v, "<=", n | v, "=", n |
 *                n, ">", v | n, ">=", v | n, "<", v | n, "<=", v | n, "=", v
 * fn ∈ funcName ::= String (no spaces)
 * v ∈ Variable ::= String (no Spaces)
 * e ∈ Expression ::= e, "+", e | e, "-", e | e, "*", e | e, "/", e | e, "^", n | n | v
 * n ∈ Number ::= [0-9]
 */


sealed abstract class AST
sealed abstract class Function extends AST

case class PicoFunction(bounds: Function, funcName: Function, variable: Function, expression: Function) extends Function
case class PicoVariable(string:String) extends Function
case class PicoExpression(left:Function, operator:String, right:Function) extends Function
case class PicoFunctionName(string:String) extends Function
case class PicoBounds(variable:Function, comparator:String, number:Function) extends Function
case class PicoNumber(int:Integer) extends Function
