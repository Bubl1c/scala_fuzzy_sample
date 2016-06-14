import com.fuzzylite.{Engine, FuzzyLite, Op}
import com.fuzzylite.norm.TNorm
import com.fuzzylite.norm.s.Maximum
import com.fuzzylite.norm.t.Minimum
import com.fuzzylite.rule.{Rule, RuleBlock}
import com.fuzzylite.term.{Gaussian, Term, Triangle}
import com.fuzzylite.variable.{InputVariable, OutputVariable}

/**
  * Created by Andrii on 6/12/2016.
  */
class SpeedKnowledgeBase {
  val engine = new Engine("Speed knowledge base")

  private object input {
    val F = InitF("F", 0.000, 40.000)
    val n = InitN("n", 0.000, 50.000)
    val K = InitK("K", 0.000, 100.000)
  }

  private val v = InitV("v", 0.000, 10.000)

  private val ruleBlock: RuleBlock = InitRB("", Minimum, Maximum, Minimum)

  def toJD(v: Double): java.lang.Double = v

//  val FVals = InputDataProvider.provide(input.F)
//  val nVals = InputDataProvider.provide(input.n)
//  val KVals = InputDataProvider.provide(input.K)

  def eval(f: Double, n: Double, k: Double): Double = {
    input.F.setInputValue(toJD(f))
    input.n.setInputValue(toJD(n))
    input.K.setInputValue(toJD(k))
    engine.process
    v.getOutputValue
  }

}

object

object InputDataProvider extends DataProvider[InputVariable]

trait InputVariableInitializer extends VariableInitializer[InputVariable]
trait OutputVariableInitializer extends VariableInitializer[OutputVariable]

object InitRB extends RuleBlockInitializer {
  override def init(ruleBlock: RuleBlock): RuleBlock = {
    ruleBlock.setEnabled(true)
    r("if F is Low and n is Slow and K is Soft then v is Slow")
    r("if F is Low and n is Average and K is Soft then v is Average")
    r("if F is Low and n is Fast and K is Soft then v is Average")

    r("if F is Average and n is Slow and K is Average then v is Slow")
    r("if F is Average and n is Average and K is Average then v is Average")
    r("if F is Average and n is Fast and K is Average then v is Fast")

    r("if F is Fast and n is Slow and K is Hard then v is Slow")
    r("if F is Fast and n is Average and K is Hard then v is Average")
    r("if F is Fast and n is Fast and K is Hard then v is Fast")
    ruleBlock
  }
}

object InitF extends InputVariableInitializer {
  override def init(variable: InputVariable): InputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Gaussian("Low", 5, 0.6))
    variable.addTerm(new Gaussian("Average", 13, 0.6))
    variable.addTerm(new Gaussian("High", 35, 0.6))
    variable
  }
}

object InitN extends InputVariableInitializer {
  override def init(variable: InputVariable): InputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Gaussian("Slow", 5, 0.8))
    variable.addTerm(new Gaussian("Average", 20, 0.8))
    variable.addTerm(new Gaussian("Fast", 45, 0.8))
    variable
  }
}

object InitK extends InputVariableInitializer {
  override def init(variable: InputVariable): InputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Gaussian("Soft", 11, 0.7))
    variable.addTerm(new Gaussian("Average", 40, 0.7))
    variable.addTerm(new Gaussian("Hard", 70, 0.7))
    variable
  }
}

object InitV extends OutputVariableInitializer {
  override def init(variable: OutputVariable): OutputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Gaussian("Slow", 2, 0.9))
    variable.addTerm(new Gaussian("Average", 5, 0.9))
    variable.addTerm(new Gaussian("Fast", 7, 0.9))
    variable
  }
}
