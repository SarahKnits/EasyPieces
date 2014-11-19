package Parser

/**
 * Sarah Gilkinson
 */
import org.scalatest._
import edu.hmc.langtools._
import IR._

class ParserTest extends FunSpec with LangParseMatchers[AST] {
  override val parser = Parser.apply _
  describe("A single function") {

    it("can be parsed to a single PGFunction") {
      program("f(x) = { 2*x, x>2") should parseAs(
        PGFunction(PGBounds(PGVariable("x"), ">", PGNumber(2)), PGFunctionName("f"), PGVariable("x"), PGExpression(PGNumber(2), "*", PGVariable("x")), None))
    }

    it("can be parsed with reversed bounds") {
      program("f(x) = { 2-x, 2<=x") should parseAs(
        PGFunction(PGBounds(PGVariable("x"), ">=", PGNumber(2)), PGFunctionName("f"), PGVariable("x"), PGExpression(PGNumber(2), "-", PGVariable("x")), None))
    }

    it("can parse exponents") {
      program("g(y) = { y^2, y<4") should parseAs(
        PGFunction(PGBounds(PGVariable("y"), "<", PGNumber(4)), PGFunctionName("g"), PGVariable("y"), PGExpression(PGVariable("y"), "^", PGNumber(2)), None))
    }

    it("can handle multiple equations") {
      program("f(x) = { 3+x, x = 0\nf(x) = { x^2, x > 0") should parseAs(
        PGFunction(PGBounds(PGVariable("x"), "=", PGNumber(0)), PGFunctionName("f"), PGVariable("x"), PGExpression(PGNumber(3), "+", PGVariable("x")),
        Some(PGFunction(PGBounds(PGVariable("x"), ">", PGNumber(0)), PGFunctionName("f"), PGVariable("x"), PGExpression(PGVariable("x"), "^", PGNumber(2)), None))))
    }
  }
}

