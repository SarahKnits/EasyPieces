package Semantics

import IR._
import Parser.Parser
import org.scalatest.FunSpec
import edu.hmc.langtools._

class SemanticsTest extends FunSpec with LangInterpretMatchers[AST,scala.collection.mutable.Map[String, List[Function]]] {
  override val parser = Parser.apply _
  override val interpreter = Semantics.eval _

  describe("A list of functions") {
    it("can be parsed as a single function") {
      program("f(x)= {2*x, 0 <= x < 4") should compute(
        scala.collection.mutable.Map("f" -> List(PGBoundsVarAndExpression(PGBounds(PGNumber(0.0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(4.0)),PGVariable("x"),PGExpression(PGNumber(2.0),"*",PGVariable("x"))))))
    }
    Semantics.reset()
    it("can be parsed as two different functions with the same name") {
      program("f(x)= {2*x, 0 <= x < 4 f(x) = { 2^x, 4 <= x < 10") should compute(
        scala.collection.mutable.Map("f" -> List(PGBoundsVarAndExpression(PGBounds(PGNumber(0.0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(4.0)),PGVariable("x"),PGExpression(PGNumber(2.0),"*",PGVariable("x"))), PGBoundsVarAndExpression(PGBounds(PGNumber(4.0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(10.0)),PGVariable("x"),PGExpression(PGNumber(2.0),"^",PGVariable("x"))))))
    }
    Semantics.reset()
    it("can be parsed as two different functions with different names") {
      program("f(x)= {2*x, 0 <= x < 4 g(x) = { 2^x, 4 <= x < 10") should compute(
        scala.collection.mutable.Map("f" -> List(PGBoundsVarAndExpression(PGBounds(PGNumber(0.0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(4.0)),PGVariable("x"),PGExpression(PGNumber(2.0),"*",PGVariable("x")))), "g" -> List(PGBoundsVarAndExpression(PGBounds(PGNumber(4.0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(10.0)),PGVariable("x"),PGExpression(PGNumber(2.0),"^",PGVariable("x"))))))
    }
    Semantics.reset()
  }
  describe("A function with options") {
    it("can be parsed as a single function") {
      program("Filename: \"SarahFile\" Title: \"Sarah's File\" f(x)= {2*x, 0 <= x < 4") should compute(
        scala.collection.mutable.Map("f" -> List(PGBoundsVarAndExpression(PGBounds(PGNumber(0.0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(4.0)),PGVariable("x"),PGExpression(PGNumber(2.0),"*",PGVariable("x"))))))
    }
  }

}