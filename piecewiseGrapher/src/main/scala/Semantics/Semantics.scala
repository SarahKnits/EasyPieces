package Semantics

import IR._
import Parser.PGException
import breeze.numerics.pow
import org.sameersingh.scalaplot._
import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot.XYSeriesImplicits.Y

package object Semantics {

  // Global variables
  var mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
  var colorIndex = 0
  var title = "Easy Pieces"
  var fileName = "Graph"
  var xLabel = "x"
  var yLabel = "y"
  var location = "docs/img/"
  var format = "PNG"
  var xMin = Double.MaxValue
  var xMax = Double.MinValue
  var yMin = Double.MaxValue
  var yMax = Double.MinValue
  var plotList: XYData = new XYData()
  var colorMap = Map((0, Some(Color.Black)), (1, Some(Color.Blue)), (2, Some(Color.Red)), (3, Some(Color.Magenta)),
    (4, Some(Color.Green)), (5, Some(Color.Purple)), (6, Some(Color.Gold)), (7, Some(Color.Cyan)))

  // Used in testing to reset necessary variables
  def reset(): Unit = {
    mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
    colorIndex = 0
    plotList = new XYData()
  }

  /*
   * Main evaluation method, sets options and evaluates all functions
   */
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

  /*
   * Sets options based on parsed inputs
   */
  def setOptions(options: Function): Unit = options match {
    case PGOptions(filename, titleName, xName, yName, loc, form) =>
      fileName = extractString(filename)
      title = extractString(titleName)
      xLabel = extractString(xName)
      yLabel = extractString(yName)
      location = extractString(loc)
      format = extractString(form)
      if (!(format == "PNG" || format == "GUI" || format == "ASCII" || format == "SVG" || format == "PDF")) format = "PNG"
    case _ => // Do nothing
  }

  /*
   * Extracts strings from variables, function names, and comparators
   */
  def extractString(function:Function): String = function match {
    case PGFunctionName(x) => x
    case PGVariable(x) => x
    case PGComparator(x) => x
    case _ => "Invalid"
  }

  /*
   * Extracts numbers from PGNumber
   */
  def extractNumber(number:Function): Double = number match {
    case PGNumber(x) => x
    case _ => -1.0
  }

  /*
   * Updates yMin and yMax if necessary
   */
  def updateLimits(expression:Function, input:Double, variable:String) : Double = {
    val value = evalExpression(expression, input, variable)
    if (value < yMin) yMin = value
    if (value > yMax) yMax = value
    value
  }

  /*
   * Evaluates expression for a given input and variable
   */
  def evalExpression(expression:Function, input:Double, variable:String) : Double = {
    expression match {
      case PGParens(e) => evalExpression(e, input, variable)
      case PGExpression(left, "+", right) => evalExpression(left, input, variable) + evalExpression(right, input, variable)
      case PGExpression(left, "-", right) => evalExpression(left, input, variable) - evalExpression(right, input, variable)
      case PGExpression(left, "*", right) => evalExpression(left, input, variable) * evalExpression(right, input, variable)
      case PGExpression(left, "/", right) => safeDivide(evalExpression(left, input, variable), evalExpression(right, input, variable))
      case PGExpression(left, "^", right) => pow(evalExpression(left, input, variable), evalExpression(right, input, variable))
      case PGSingleApply("sqrt", left) => safeRoot(evalExpression(left, input, variable))
      case PGSingleApply("abs", left) => Math.abs(evalExpression(left, input, variable))
      case PGSingleApply("sin", left) => Math.sin(evalExpression(left, input, variable))
      case PGSingleApply("cos", left) => Math.cos(evalExpression(left, input, variable))
      case PGSingleApply("ln", left) => safeNaturalLog(evalExpression(left, input, variable))
      case PGSingleApply("log", left) => safeLog(evalExpression(left, input, variable))
      case PGNumber(i: Double) => i
      case PGVariable(j: String) =>
        if (j == variable) {
          input
        }
        else {
          throw new PGException("Incorrect variable: " + j)
        }
      case x => throw new PGException("Invalid input: " + x)
    }
  }

  /*
   * Adds a function to the list of points to plot
   */
  def addToPlotList(funcList: Option[List[Function]], functionName: String): Unit = {
    val graphColor:Option[Color.Type] = colorMap.getOrElse(colorIndex % colorMap.size, Color.Black)
    colorIndex += 1
    funcList match {
      case None => return
      case Some(x) => x.foreach(f =>
      f match {
        case PGBoundsVarAndExpression(PGBounds(less, comp1, variable, comp2, more), variable3, expression) =>
          if (variable != variable3) {
            throw new PGException("Invalid variable: " + extractString(variable) + " in " + functionName + "(" + extractString(variable3) + ")")
          }
          var variable2 = extractString(variable3)

          // Update xMin and xMax if necessary
          if (evalExpression(less, 0, variable2) < xMin) xMin = evalExpression(less, 0, variable2)
          if (evalExpression(more, 0, variable2) > xMax) xMax = evalExpression(more, 0, variable2)

          val stepSize = (evalExpression(more, 0, variable2) - evalExpression(less, 0, variable2))/500
          var x: Seq[Double] = evalExpression(less, 0, variable2) until (evalExpression(more, 0, variable2)+stepSize) by stepSize
          // Adds main plot to point list
          plotList += (x -> Y(x.map(i => updateLimits(expression, i, variable2)), ps= Some(0.1), pt = PointType.*, color = graphColor, lt=Some(LineType.Solid), style=XYPlotStyle.LinesPoints, label="f"))
          // Produces first boundary point (open or closed)
          if (extractString(comp1) == "<=") {
            x = evalExpression(less, 0, variable2) * 1.0 until (evalExpression(less, 0, variable2) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i, variable2)), pt=PointType.fullO, ps= Some(2.0), color = graphColor))
          } else {
            x = evalExpression(less, 0, variable2) * 1.0 until (evalExpression(less, 0, variable2) + 1) * 1.0 by 1.0
            plotList += (x -> Y(x.map(i=> evalExpression(expression, i, variable2)), pt=PointType.emptyO, ps= Some(2.0), color = graphColor))
          }
          // Produces second boundary point (open or closed)
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

  /*
   * If denominator is 0, throws an exception
   * Otherwise, returns the result of the division
   */
  def safeDivide(first:Double, second:Double): Double = {
    if (second != 0.0) {
      first / second
    } else {
      throw new PGException("Invalid operation: Division by zero")
    }
  }

  /*
   * If the input is 0, returns -2
   * Otherwise, returns the result of taking the log (base 10)
   */
  def safeLog(value:Double): Double = {
    if (value <= 0.0) {
      -2.0
    } else {
      Math.log10(value)
    }
  }

  /*
   * If the input is 0, returns -2
   * Otherwise, returns the result of taking the natural log
   */
  def safeNaturalLog(value:Double): Double = {
    if (value <= 0.0) {
      -2.0
    } else {
      Math.log(value)
    }
  }

  /*
   * If the input is negative, throws an exception
   * Otherwise, returns the result of taking the square root
   */
  def safeRoot(first:Double) : Double = {
    if (!(first < 0)) {
      Math.pow(first, 0.5)
    } else {
      throw new PGException("Invalid operation: Square root of a negative number.")
    }
  }

  /*
   * Produces the graph
   */
  def graph(functionMap: scala.collection.mutable.Map[String, List[Function]]): Unit = {
    (functionMap.keySet).foreach(i => addToPlotList(mapFunctions.get(i), i))
    val xRange = Some(xMin - ((xMax - xMin)/10.0), xMax + ((xMax - xMin)/10.0))
    val yRange = Some(yMin - ((yMax - yMin)/10.0), yMax + ((yMax - yMin)/10.0))
    format match {
      case "PNG" =>
        output(PNG(location, fileName), plot(plotList,
          x = Axis(label = xLabel, range = xRange), y = Axis(label = yLabel, range = yRange), title = title))
      case "PDF" =>
        output(PDF(location, fileName), plot(plotList,
          x = Axis(label = xLabel, range = xRange), y = Axis(label = yLabel, range = yRange), title = title))
      case "ASCII" =>
        System.out.println(
        output(ASCII, plot(plotList,
          x = Axis(label = xLabel, range = xRange), y = Axis(label = yLabel, range = yRange), title = title)))
      case "SVG" =>
        output(SVG, plot(plotList,
          x = Axis(label = xLabel, range = xRange), y = Axis(label = yLabel, range = yRange), title = title))
      case "GUI" =>
        output(GUI, plot(plotList,
          x = Axis(label = xLabel, range = xRange), y = Axis(label = yLabel, range = yRange), title = title))
    }
    mapFunctions = scala.collection.mutable.Map[String, List[Function]]()
    plotList = new XYData()
    colorIndex = 0
  }
}