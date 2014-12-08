package Semantics

import IR._
import breeze.numerics.pow
import org.sameersingh.scalaplot._
import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.XYSeriesImplicits.Y

package object Semantics {
  var mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
  val step = 0.01
  var colorIndex = 0
  var title = "Easy Pieces"
  var fileName = "Graph"
  var xLabel = "x"
  var yLabel = "y"
  var location = "docs/img/"
  var plotList: XYData = new XYData()

  var colorMap = Map((0, Some(Color.Black)), (1, Some(Color.Blue)), (2, Some(Color.Red)), (3, Some(Color.Magenta)),
    (4, Some(Color.Green)), (5, Some(Color.Purple)), (6, Some(Color.Gold)), (7, Some(Color.Cyan)))

  def reset(): Unit = {
    mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
    colorIndex = 0
    plotList = new XYData()
  }


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
    case PGData(options, function) =>
      setOptions(options)
      eval(function)
    case _ => mapFunctions
  }

  def setOptions(options: Function): Unit = options match {
    case PGOptions(filename, titleName, xName, yName, loc) =>
      fileName = extractString(filename)
      title = extractString(titleName)
      xLabel = extractString(xName)
      yLabel = extractString(yName)
      location = extractString(loc)
    case _ => "Invalid"
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

  def evalExpression(expression:Function, input:Double, variable:String) : Double = {
    expression match {
      case PGParens(e) => evalExpression(e, input, variable)
      case PGExpression(left, "+", right) => evalExpression(left, input, variable) + evalExpression(right, input, variable)
      case PGExpression(left, "-", right) => evalExpression(left, input, variable) - evalExpression(right, input, variable)
      case PGExpression(left, "*", right) => evalExpression(left, input, variable) * evalExpression(right, input, variable)
      case PGExpression(left, "/", right) => evalExpression(left, input, variable) / evalExpression(right, input, variable)
      case PGExpression(left, "^", right) => pow(evalExpression(left, input, variable), evalExpression(right, input, variable))
      case PGSingleApply("sqrt", left) => pow(evalExpression(left, input, variable), 0.5)
      case PGSingleApply("abs", left) => Math.abs(evalExpression(left, input, variable))
      case PGSingleApply("sin", left) => Math.sin(evalExpression(left, input, variable))
      case PGSingleApply("cos", left) => Math.cos(evalExpression(left, input, variable))
      case PGSingleApply("ln", left) => Math.log(evalExpression(left, input, variable))
      case PGSingleApply("log", left) => Math.log10(evalExpression(left, input, variable))
      case PGNumber(i:Double) => i
      case PGVariable(j:String) =>
        if (j==variable) {
          input
        }
        else {
          println("Incorrect variable: " + j)
          System.exit(1)
          -1.0
        }
      case x => println("Invalid input: " + x )
        System.exit(1)
        -1.0
    }
  }

  def addToPlotList(funcList: Option[List[Function]], functionName: String): Unit = {
    val graphColor:Option[Color.Type] = colorMap.getOrElse(colorIndex % colorMap.size, Color.Black)
    colorIndex += 1
    funcList match {
      case None => return
      case Some(x) => x.foreach(f =>
      f match {
        case PGBoundsVarAndExpression(PGBounds(less, comp1, variable, comp2, more), variable3, expression) =>
          if (variable != variable3) {
            println("Invalid variable: " + extractString(variable) + " in " + functionName + "(" + extractString(variable3) + ")")
            System.exit(-1)
          }
          var variable2 = extractString(variable3)
          val stepSize = (evalExpression(more, 0, variable2) - evalExpression(less, 0, variable2))/500
          var x: Seq[Double] = (evalExpression(less, 0, variable2) until (evalExpression(more, 0, variable2)+stepSize) by stepSize)
          plotList += (x -> Y(x.map(i => evalExpression(expression, i, variable2)), ps= Some(0.1), pt = PointType.*, color = graphColor, lt=Some(LineType.Solid), style=XYPlotStyle.LinesPoints, label="f"))
          if (extractString(comp1) == "<=") {
            x = evalExpression(less, 0, variable2) * 1.0 until (evalExpression(less, 0, variable2) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i, variable2)), pt=PointType.fullO, ps= Some(2.0), color = graphColor))
          } else {
            x = evalExpression(less, 0, variable2) * 1.0 until (evalExpression(less, 0, variable2) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i, variable2)), pt=PointType.emptyO, ps= Some(2.0), color = graphColor))
          }
          if (extractString(comp2) == "<=") {
            x = evalExpression(more, 0, variable2) * 1.0 until (evalExpression(more, 0, variable2) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i, variable2)), pt=PointType.fullO, ps= Some(2.0), color = graphColor))
          } else {
            x = evalExpression(more, 0, variable2) * 1.0 until (evalExpression(more, 0, variable2) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i, variable2)), pt=PointType.emptyO, ps= Some(2.0), color = graphColor))
          }
        case _ => return
      })
    }
  }

  def graph(functionMap: scala.collection.mutable.Map[String, List[Function]]): Unit = {
    (functionMap.keySet).foreach(i => addToPlotList(mapFunctions.get(i), i))
    output(PNG(location, fileName), plot(plotList,
      x = Axis(label = xLabel), y = Axis(label = yLabel), title = title))
    mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
    plotList = new XYData()
    colorIndex = 0
  }
}