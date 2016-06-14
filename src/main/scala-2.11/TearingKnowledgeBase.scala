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
class TearingKnowledgeBase {
  val engine = new Engine("Tearing knowledge base")

  private object input {
    val F = InitF("F", 0.000, 40.000)
    val n = InitN("n", 0.000, 50.000)
    val Ka = InitKa("Ka", 0.000, 30.000)
  }

  private val t = InitT("t", 0.000, 1.000)

  private val ruleBlock: RuleBlock = InitRB("", Minimum, Maximum, Minimum)

  def toJD(v: Double): java.lang.Double = v

//  val FVals = InputDataProvider.provide(input.F)
//  val nVals = InputDataProvider.provide(input.n)
//  val KVals = InputDataProvider.provide(input.K)

  def eval(f: Double, n: Double, ka: Double): Double = {
    input.F.setInputValue(toJD(f))
    input.n.setInputValue(toJD(n))
    input.Ka.setInputValue(toJD(ka))
    engine.process
    t.getOutputValue
  }

}

object

object InputDataProvider extends DataProvider[InputVariable]

trait InputVariableInitializer extends VariableInitializer[InputVariable]
trait OutputVariableInitializer extends VariableInitializer[OutputVariable]

object InitRB extends RuleBlockInitializer {
  override def init(ruleBlock: RuleBlock): RuleBlock = {
    ruleBlock.setEnabled(true)
    r("if F is Low and n is Slow and Ka is Soft then v is Low")
    r("if F is Low and n is Average and Ka is Soft then v is Average")
    r("if F is Low and n is Fast and Ka is Soft then v is High")

    r("if F is Average and n is Slow and Ka is Average then v is Slow")
    r("if F is Average and n is Average and Ka is Average then v is Average")
    r("if F is Average and n is Fast and Ka is Average then v is High")

    r("if F is High and n is Slow and Ka is Hard then v is Slow")
    r("if F is High and n is Average and Ka is Hard then v is Average")
    r("if F is High and n is Fast and Ka is Hard then v is High")
    ruleBlock
  }
}

object InitKa extends InputVariableInitializer {
  override def init(variable: InputVariable): InputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Gaussian("Soft", 5, 0.8))
    variable.addTerm(new Gaussian("Average", 14, 0.8))
    variable.addTerm(new Gaussian("Hard", 24, 0.8))
    variable
  }
}

object InitT extends OutputVariableInitializer {
  override def init(variable: OutputVariable): OutputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Gaussian("Low", 0.2, 0.3))
    variable.addTerm(new Gaussian("Average", 0.5, 0.3))
    variable.addTerm(new Gaussian("High", 0.8, 0.3))
    variable
  }
}
