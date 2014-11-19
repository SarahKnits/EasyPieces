import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot._

object GraphDemo {

  def main(args: Array[String]): Unit = {

    val step = 0.01
    val graphColor = Some(Color.Black)

    val x1 = (-10.0 until 2.0 by step)
    val x2 = (2.0+step until 4.0-step by step)
    val x3 = (4.0 until 10.0 by step)

    var y1 = x1.map(j => j + 2.0)

    var y2 = x2.map(k => k*k)

    var y3 = x3.map(l => l+4)

    val x4 = (2.0 until 3.0 by 1.0)

    val y4 = x4.map(i => i*i)

    val point1: Seq[(Double, Double)] = Seq((2.0, 4.0))

    val x5 = (4.0 until 5.0 by 1.0)

    val y5 = x5.map(i => i*i)

    val y6 = x5.map(i => i+4.0)

    output(PNG("docs/img/", "SecondProgram"), plot(List(
      x1 -> Y(y1, pt = Some(PointType.Dot), color = graphColor),
      x2 -> Y(y2, pt = Some(PointType.Dot), color = graphColor),
      x3 -> Y(y3, pt = Some(PointType.Dot), color = graphColor),
      x4 -> Y(y4, pt = Some(PointType.fullO), ps = Some(2.0), color = graphColor),
      x5 -> Y(y5, pt = Some(PointType.emptyO), ps = Some(2.0), color = graphColor),
      x5 -> Y(y6, pt = Some(PointType.fullO), ps = Some(2.0), color = graphColor)),
      x = Axis(label = "x"), y = Axis(label = "f(x)"), title = "Second Program"))

  }

}
