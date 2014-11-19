package Semantics

import IR._
import breeze.numerics.pow
import org.sameersingh.scalaplot._
import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.XYSeriesImplicits.Y

package object Semantics {
  // Maps a street to a state number
  var mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
  val step = 0.01
  val graphColor = Some(Color.Black)
  val title = "Second Program"
  val fileName = "SecondProgram"
  val location = "docs/img"
  var plotList: XYData


  def eval(ast: AST): scala.collection.mutable.Map[String, List[Function]] = ast match {
    // Consumes commands until it reaches "null", which terminates the evaluation
    case PGFunction(bounds, funcName, variable, expression, next) =>
      // Build rule from parts of the command
      val function: Function = PGBoundsVarAndExpression(bounds, variable, expression)
      val currFunctions: Option[List[Function]] = mapFunctions.get(extractString(funcName))
      currFunctions match {
        case Some(x) => mapFunctions.put(extractString(funcName), x :+ function)
        case None => mapFunctions.put(extractString(funcName), List(function))
      }
      next match {
        case Some(x) => eval(x)
        case None => mapFunctions
      }
    case _ => mapFunctions
  }

  def extractString(function:Function): String = function match {
    case PGFunctionName(x) => x
    case PGVariable(x) => x
    case PGComparator(x) => x
    case _ => "Invalid"
  }

  def extractNumber(number:Function): Integer = number match {
    case PGNumber(x) => x
    case _ => -1
  }

  def evalExpression(expression:Function, input:Double) : Double = {
    expression match {
      case PGExpression(left, "+", right) => evalExpression(left, input) + evalExpression(right, input)
      case PGExpression(left, "-", right) => evalExpression(left, input) - evalExpression(right, input)
      case PGExpression(left, "*", right) => evalExpression(left, input) - evalExpression(right, input)
      case PGExpression(left, "/", right) => evalExpression(left, input) / evalExpression(right, input)
      case PGExpression(left, "^", right) => pow(evalExpression(left, input), evalExpression(right, input))
      case PGNumber(i:Integer) => i*1.0
      case PGVariable(j:String) => input
      case _ => -1.0
    }
  }

  def addToPlotList(funcList: Option[List[Function]]): Unit = {
    funcList match {
      case None => return
      case Some(x) => x.foreach(f =>
      f match {
        case PGBoundsVarAndExpression(PGBounds(less, comp1, variable, comp2, more), variable2, expression) =>
          val x: Seq[Double] = extractNumber(less) * 1.0 until extractNumber(more) * 1.0 by step
          plotList += (x -> Y(x.map(i => evalExpression(expression, i))))
          if (extractString(comp1) == "<=") {
            // plotList += filled point at first bound
          } else {
            // plotList += empty point at first bound
          }
        case _ => return
      })
    }
  }

  def graph(functionMap: scala.collection.mutable.Map[String, List[Function]]): Unit = {
    (functionMap.keySet).foreach(i => addToPlotList(mapFunctions.get(i)))
    output(PNG(location, fileName), plot(plotList,
      x = Axis(label = "x"), y = Axis(label = "f(x)"), title = "Second Program"))
  }
}