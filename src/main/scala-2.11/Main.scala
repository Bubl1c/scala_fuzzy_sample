import com.fuzzylite.{Engine, FuzzyLite, Op}
import com.fuzzylite.rule.{Rule, RuleBlock}
import com.fuzzylite.term.Triangle
import com.fuzzylite.variable.{InputVariable, OutputVariable}

/**
  * Created by Andrii on 6/12/2016.
  */
object Main extends App {
  val engine = new Engine()
  engine.setName("simple-dimmer")

  val ambient = new InputVariable()
  ambient.setName("Ambient")
  ambient.setRange(0.000, 1.000)
  ambient.addTerm(new Triangle("DARK", 0.000, 0.250, 0.500))
  ambient.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750))
  ambient.addTerm(new Triangle("BRIGHT", 0.500, 0.750, 1.000))
  engine.addInputVariable(ambient)

  val power = new OutputVariable()
  power.setName("Power")
  power.setRange(0.000, 1.000)
  power.setDefaultValue(Double.NaN)
  power.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500))
  power.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750))
  power.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000))
  engine.addOutputVariable(power)

  val ruleBlock = new RuleBlock()
  ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine))
  ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine))
  ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine))
  engine.addRuleBlock(ruleBlock)

  engine.configure("", "", "Minimum", "Maximum", "Centroid")

  val statusBuilder = new java.lang.StringBuilder()
  if (!engine.isReady(statusBuilder)) {
    throw new RuntimeException("Engine not ready. "
      + "The following errors were encountered:\n" + statusBuilder.toString())
  }

  for (i <- 0 until 50) {
    val light: java.lang.Double = ambient.getMinimum() + i * (ambient.range() / 50)
    ambient.setInputValue(light)
    engine.process()
    val powerOutput: java.lang.Double = power.getOutputValue()
    FuzzyLite.logger().info(String.format(
      "Ambient.input = %s -> Power.output = %s",
      Op.str(light), Op.str(powerOutput)))
  }
}
