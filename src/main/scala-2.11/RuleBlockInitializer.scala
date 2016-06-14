import com.fuzzylite.Engine
import com.fuzzylite.norm.{SNorm, TNorm}
import com.fuzzylite.rule.{Rule, RuleBlock}

/**
  * Created by Andrii on 6/15/2016.
  */
trait RuleBlockInitializer {
  def init(ruleBlock: RuleBlock): RuleBlock
  def apply(name: String, conjunction: TNorm, disjunction: SNorm, activation: TNorm): RuleBlock = {
    init(new RuleBlock(name, conjunction, disjunction, activation))
  }

  def r(rule: String)(implicit engine: Engine, ruleBlock: RuleBlock): Unit = {
    ruleBlock.addRule(Rule.parse(rule, engine))
  }
}
