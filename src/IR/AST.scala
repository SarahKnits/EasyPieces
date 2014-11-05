package IR

/**
 * Intermediate representation for Piecewise Grapher
 * Sarah Gilkinson
 *
 * f ∈ Function ::= fn, "(", v, ")", "=", "{" b, ",", e
 * b ∈ Bounds ::= v, ">", n | v, ">=", n | v, "<", n | v, "<=", n | v, "=", n
 * fn ∈ funcName ::= String (no spaces)
 * v ∈ Variable ::= String (no Spaces)
 * e ∈ Expression ::= e, "+", e | e, "-", e | e, "*", e | e, "/", e | e, "^", n | n | v
 * n ∈ Number ::= [0-9]
 */


sealed abstract class AST
sealed abstract class Function extends AST

case class PicoFunction(bounds: Function, funcName: Function, variable: Function, expression: Function)
case class PicoVariable(string:String) extends Function
case class PicoExpression(string:String) extends Function
