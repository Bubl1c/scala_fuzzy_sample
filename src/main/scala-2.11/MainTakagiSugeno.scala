import com.fuzzylite.{Engine, FuzzyLite, Op}
import com.fuzzylite.defuzzifier.WeightedAverage
import com.fuzzylite.rule.{Rule, RuleBlock}
import com.fuzzylite.term.{Constant, Function, Triangle}
import com.fuzzylite.variable.{InputVariable, OutputVariable}

/**
  * Created by Andrii on 6/12/2016.
  */
object MainTakagiSugeno extends App {
  val engine = new Engine("approximation of sin(x)/x")

  val inputVariable = new InputVariable("inputX", 0.000, 10.000)
  inputVariable.setEnabled(true)
  inputVariable.addTerm(new Triangle("NEAR_1", 0.000, 1.000, 2.000))
  inputVariable.addTerm(new Triangle("NEAR_2", 1.000, 2.000, 3.000))
  inputVariable.addTerm(new Triangle("NEAR_3", 2.000, 3.000, 4.000))
  inputVariable.addTerm(new Triangle("NEAR_4", 3.000, 4.000, 5.000))
  inputVariable.addTerm(new Triangle("NEAR_5", 4.000, 5.000, 6.000))
  inputVariable.addTerm(new Triangle("NEAR_6", 5.000, 6.000, 7.000))
  inputVariable.addTerm(new Triangle("NEAR_7", 6.000, 7.000, 8.000))
  inputVariable.addTerm(new Triangle("NEAR_8", 7.000, 8.000, 9.000))
  inputVariable.addTerm(new Triangle("NEAR_9", 8.000, 9.000, 10.000))
  engine.addInputVariable(inputVariable)

  val outputVariable1 = new OutputVariable("outputFx", -1.000, 1.000)
  outputVariable1.setEnabled(true)
  outputVariable1.fuzzyOutput.setAccumulation(null)
  outputVariable1.setDefuzzifier(new WeightedAverage("TakagiSugeno"))
  outputVariable1.setDefaultValue(Double.NaN)
  outputVariable1.setLockPreviousOutputValue(true)
  outputVariable1.setLockOutputValueInRange(false)
  outputVariable1.addTerm(new Constant("f1", 0.840))
  outputVariable1.addTerm(new Constant("f2", 0.450))
  outputVariable1.addTerm(new Constant("f3", 0.040))
  outputVariable1.addTerm(new Constant("f4", -0.180))
  outputVariable1.addTerm(new Constant("f5", -0.190))
  outputVariable1.addTerm(new Constant("f6", -0.040))
  outputVariable1.addTerm(new Constant("f7", 0.090))
  outputVariable1.addTerm(new Constant("f8", 0.120))
  outputVariable1.addTerm(new Constant("f9", 0.040))
  engine.addOutputVariable(outputVariable1)

  val outputVariable2 = new OutputVariable("trueFx", -1.000, 1.000)
  outputVariable2.setEnabled(true)
  outputVariable2.fuzzyOutput.setAccumulation(null)
  outputVariable2.setDefuzzifier(new WeightedAverage("Automatic"))
  outputVariable2.setDefaultValue(Double.NaN)
  outputVariable2.setLockPreviousOutputValue(true)
  outputVariable2.setLockOutputValueInRange(false)
  outputVariable2.addTerm(Function.create("fx", "sin(inputX)/inputX", engine))
  engine.addOutputVariable(outputVariable2)

  val outputVariable3 = new OutputVariable("diffFx", -1.000, 1.000)
  outputVariable3.setEnabled(true)
  outputVariable3.fuzzyOutput.setAccumulation(null)
  outputVariable3.setDefuzzifier(new WeightedAverage("Automatic"))
  outputVariable3.setDefaultValue(Double.NaN)
  outputVariable3.setLockPreviousOutputValue(false)
  outputVariable3.setLockOutputValueInRange(false)
  outputVariable3.addTerm(Function.create("diff", "fabs(outputFx-trueFx)", engine))
  engine.addOutputVariable(outputVariable3)

  val ruleBlock: RuleBlock = new RuleBlock("", null, null, null)
  ruleBlock.setEnabled(true)
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_1 then outputFx is f1", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_2 then outputFx is f2", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_3 then outputFx is f3", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_4 then outputFx is f4", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_5 then outputFx is f5", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_6 then outputFx is f6", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_7 then outputFx is f7", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_8 then outputFx is f8", engine))
  ruleBlock.addRule(Rule.parse("if inputX is NEAR_9 then outputFx is f9", engine))
  ruleBlock.addRule(Rule.parse("if inputX is any  then trueFx is fx and diffFx is diff", engine))
  engine.addRuleBlock(ruleBlock)

  def toJD(v: Double): java.lang.Double = v

  for (i <- 0 until 50) {
    val inputVariableVal = inputVariable.getMinimum + i * (inputVariable.range / 50)
    inputVariable.setInputValue(inputVariableVal)
    engine.process
    FuzzyLite.logger.info(String.format(
      "input = %s -> outputFx = %s, trueFx = %s, diffFx = %s",
      Op.str(toJD(inputVariableVal)),
      Op.str(toJD(outputVariable1.getOutputValue)),
      Op.str(toJD(outputVariable2.getOutputValue)),
      Op.str(toJD(outputVariable3.getOutputValue))
    ))
  }
}
