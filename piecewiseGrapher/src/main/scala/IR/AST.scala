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

case class PGBoundsVarAndExpression(bounds:Function, variable:Function, expression:Function) extends Function
case class PGFunction(bounds: Function, funcName: Function, variable: Function, expression: Function, next: Option[Function]) extends Function
case class PGVariable(string:String) extends Function
case class PGExpression(left:Function, operator:String, right:Function) extends Function
case class PGFunctionName(string:String) extends Function
case class PGBounds(less:Function, comp1:Function, variable:Function, comp2:Function, more:Function) extends Function
case class PGNumber(int:Integer) extends Function
case class PGParens(expression:Function) extends Function
case class PGComparator(comp:String) extends Function
