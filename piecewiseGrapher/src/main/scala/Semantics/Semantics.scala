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
  var colorIndex = 0
  val title = "My Graph"
  val fileName = "Graph"
  val location = "docs/img/"
  var plotList: XYData = new XYData()

  var colorMap = Map((0, Some(Color.Black)), (1, Some(Color.Blue)), (2, Some(Color.Red)), (3, Some(Color.Magenta)),
    (4, Some(Color.Green)), (5, Some(Color.Purple)), (6, Some(Color.Gold)))


  def eval(ast: AST): scala.collection.mutable.Map[String, List[Function]] = ast match {
    case PGFunction(bounds, funcName, variable, expression, next) =>
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

  def extractNumber(number:Function): Double = number match {
    case PGNumber(x) => x
    case _ => -1.0
  }

  def evalExpression(expression:Function, input:Double) : Double = {
    expression match {
      case PGParens(e) => evalExpression(e, input)
      case PGExpression(left, "+", right) => evalExpression(left, input) + evalExpression(right, input)
      case PGExpression(left, "-", right) => evalExpression(left, input) - evalExpression(right, input)
      case PGExpression(left, "*", right) => evalExpression(left, input) * evalExpression(right, input)
      case PGExpression(left, "/", right) => evalExpression(left, input) / evalExpression(right, input)
      case PGExpression(left, "^", right) => pow(evalExpression(left, input), evalExpression(right, input))
      case PGSingleApply("sqrt", left) => pow(evalExpression(left, input), 0.5)
      case PGSingleApply("abs", left) => Math.abs(evalExpression(left, input))
      case PGSingleApply("sin", left) => Math.sin(evalExpression(left, input))
      case PGSingleApply("cos", left) => Math.cos(evalExpression(left, input))
      case PGSingleApply("e", left) => Math.exp(evalExpression(left, input))
      case PGNumber(i:Double) => i * 1.0
      case PGVariable(j:String) => input
      case _ => -1.0
    }
  }

  def addToPlotList(funcList: Option[List[Function]]): Unit = {
    val graphColor:Option[Color.Type] = colorMap.getOrElse(colorIndex % 7, Color.Black)
    colorIndex += 1
    funcList match {
      case None => return
      case Some(x) => x.foreach(f =>
      f match {
        case PGBoundsVarAndExpression(PGBounds(less, comp1, variable, comp2, more), variable2, expression) =>
          var x: Seq[Double] = evalExpression(less, 0) * 1.0 until evalExpression(more, 0) * 1.0 by step
          plotList += (x -> Y(x.map(i => evalExpression(expression, i)), ps= Some(0.5), pt = PointType.Dot, color = graphColor))
          if (extractString(comp1) == "<=") {
            x = evalExpression(less, 0) * 1.0 until (evalExpression(less, 0) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i)), pt=PointType.fullO, ps= Some(2.0), color = graphColor))
          } else {
            x = evalExpression(less, 0) * 1.0 until (evalExpression(less, 0) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i)), pt=PointType.emptyO, ps= Some(2.0), color = graphColor))
          }
          if (extractString(comp2) == "<=") {
            x = evalExpression(more, 0) * 1.0 until (evalExpression(more, 0) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i)), pt=PointType.fullO, ps= Some(2.0), color = graphColor))
          } else {
            x = evalExpression(more, 0) * 1.0 until (evalExpression(more, 0) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i)), pt=PointType.emptyO, ps= Some(2.0), color = graphColor))
          }
        case _ => return
      })
    }
  }

  def graph(functionMap: scala.collection.mutable.Map[String, List[Function]]): Unit = {
    (functionMap.keySet).foreach(i => addToPlotList(mapFunctions.get(i)))
    output(PNG(location, fileName), plot(plotList,
      x = Axis(label = "x"), y = Axis(label = "y"), title = title))
    mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
    plotList = new XYData()
    colorIndex = 0
  }
}