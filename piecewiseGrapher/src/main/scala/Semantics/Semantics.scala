package Semantics

import IR._

package object Semantics {
  // Maps a street to a state number
  var mapFunctions = scala.collection.mutable.Map[String, List[Function]]()

  //case class PGFunction(bounds: Function, funcName: Function, variable: Function, expression: Function) extends Function

  def eval(ast: AST): scala.collection.mutable.Map[String, List[Function]] = ast match {
    // Consumes commands until it reaches "null", which terminates the evaluation
    case PGFunction(bounds, funcName, variable, expression) =>
      // Build rule from parts of the command
      val function: Function = PGBoundsVarAndExpression(bounds, variable, expression)
      val currFunctions: Option[List[Function]] = mapFunctions.get(extractString(funcName))
      currFunctions match {
        case Some(x) => mapFunctions.put(extractString(funcName), x :+ function)
        case None => mapFunctions.put(extractString(funcName), List(function))
      }
      mapFunctions
    case _ => mapFunctions
  }

  def extractString(function:Function): String = function match {
    case PGFunctionName(x) => x
    case PGVariable(x) => x
    case _ => "Invalid"
  }
}