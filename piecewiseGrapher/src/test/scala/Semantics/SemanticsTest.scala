package Semantics

import IR._
import Parser.Parser
import org.scalatest.FunSpec
import edu.hmc.langtools._

/*class SemanticsTest extends FunSpec with LangInterpretMatchers[AST,scala.collection.mutable.Map[String, List[PGBoundsVarAndExpression]]] {
  override val parser = Parser.apply _
  override val interpreter = Semantics.eval _

  describe("A single function") {
    it("can parse a simple command with one option") {
      program("If you are on First St. and can go uptown, go uptown on First St.") should compute(
        scala.collection.mutable.Map("f" -> List(PGBoundsVarAndExpression(PGBounds(PGNumber(0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(4)),PGVariable("x"),PGExpression(PGNumber(2),"*",PGVariable("x"))), PGBoundsVarAndExpression(PGBounds(PGNumber(4),PGComparator("<="),PGVariable("x"),PGComparator("<="),PGNumber(5)),PGVariable("x"),PGVariable("x")), PGBoundsVarAndExpression(PGBounds(PGNumber(5),PGComparator("<"),PGVariable("x"),PGComparator("<"),PGNumber(10)),PGVariable("x"),PGExpression(PGNumber(3),"*",PGVariable("x"))), PGBoundsVarAndExpression(PGBounds(PGNumber(0),PGComparator("<="),PGVariable("x"),PGComparator("<"),PGNumber(4)),PGVariable("x"),PGExpression(PGNumber(2),"*",PGVariable("x"))), PGBoundsVarAndExpression(PGBounds(PGNumber(4),PGComparator("<="),PGVariable("x"),PGComparator("<="),PGNumber(5)),PGVariable("x"),PGVariable("x")), PGBoundsVarAndExpression(PGBounds(PGNumber(5),PGComparator("<"),PGVariable("x"),PGComparator("<"),PGNumber(10)),PGVariable("x"),PGExpression(PGNumber(3),"*",PGVariable("x"))))))

    }
  }
}*/
