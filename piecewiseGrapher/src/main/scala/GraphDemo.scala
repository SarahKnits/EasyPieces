import org.sameersingh.scalaplot.Implicits._
import org.sameersingh.scalaplot._

object GraphDemo {

  def main(args: Array[String]): Unit = {
    val x = 0.0 until 2.0 * math.Pi by 0.1
    output(PNG("docs/img/", "test"), plot(x -> (math.sin(_), math.cos(_))))

    val data: XYData = ((1.0 until 3.0 by 0.01) -> (1.0 until 3.0 by 0.01).map(j => 2.0*j))

    data += (3.0 until 4.0 by 0.01) -> (3.0 until 4.0 by 0.01).map(j => 3.0*j)

    val d : XYData = ((1 until 100).map(_.toDouble) -> (1 until 100).map(j => math.pow(j,1)))

    output(GUI, plot(data))
  }

}
