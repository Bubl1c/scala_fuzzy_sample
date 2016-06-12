import com.fuzzylite.Engine
import com.fuzzylite.term.{Term, Triangle}
import com.fuzzylite.variable.{InputVariable, OutputVariable}

/**
  * Created by Andrii on 6/12/2016.
  */
class SpeedKnowledgeBase {
  val engine = new Engine("Speed knowledge base")

  private object input {
    val F = InitF("F", 0.000, 10.000)
    val n = InitN("n", 0.000, 10.000)
    val K = InitK("K", 0.000, 10.000)
  }

  private val v = InitV("v", 0.000, 10.000)

}

trait InputVariableInitializer extends VariableInitializer[InputVariable]
trait OutputVariableInitializer extends VariableInitializer[OutputVariable]

object InitF extends InputVariableInitializer {
  override def init(variable: InputVariable): InputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Triangle("NEAR_1", 0.000, 1.000, 2.000))
    variable.addTerm(new Triangle("NEAR_2", 1.000, 2.000, 3.000))
    variable.addTerm(new Triangle("NEAR_3", 2.000, 3.000, 4.000))
    variable.addTerm(new Triangle("NEAR_4", 3.000, 4.000, 5.000))
    variable.addTerm(new Triangle("NEAR_5", 4.000, 5.000, 6.000))
    variable.addTerm(new Triangle("NEAR_6", 5.000, 6.000, 7.000))
    variable.addTerm(new Triangle("NEAR_7", 6.000, 7.000, 8.000))
    variable.addTerm(new Triangle("NEAR_8", 7.000, 8.000, 9.000))
    variable.addTerm(new Triangle("NEAR_9", 8.000, 9.000, 10.000))
    variable
  }
}

object InitN extends InputVariableInitializer {
  override def init(variable: InputVariable): InputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Triangle("NEAR_1", 0.000, 1.000, 2.000))
    variable.addTerm(new Triangle("NEAR_2", 1.000, 2.000, 3.000))
    variable.addTerm(new Triangle("NEAR_3", 2.000, 3.000, 4.000))
    variable.addTerm(new Triangle("NEAR_4", 3.000, 4.000, 5.000))
    variable.addTerm(new Triangle("NEAR_5", 4.000, 5.000, 6.000))
    variable.addTerm(new Triangle("NEAR_6", 5.000, 6.000, 7.000))
    variable.addTerm(new Triangle("NEAR_7", 6.000, 7.000, 8.000))
    variable.addTerm(new Triangle("NEAR_8", 7.000, 8.000, 9.000))
    variable.addTerm(new Triangle("NEAR_9", 8.000, 9.000, 10.000))
    variable
  }

  def Triangle(inputVariable: InputVariable,
               name: String, vertexA: Double, vertexB: Double, vertexC: Double): Unit = {
    inputVariable.addTerm(new Triangle(name, vertexA, vertexB, vertexC))
  }
}

object InitK extends InputVariableInitializer {
  override def init(variable: InputVariable): InputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Triangle("NEAR_1", 0.000, 1.000, 2.000))
    variable.addTerm(new Triangle("NEAR_2", 1.000, 2.000, 3.000))
    variable.addTerm(new Triangle("NEAR_3", 2.000, 3.000, 4.000))
    variable.addTerm(new Triangle("NEAR_4", 3.000, 4.000, 5.000))
    variable.addTerm(new Triangle("NEAR_5", 4.000, 5.000, 6.000))
    variable.addTerm(new Triangle("NEAR_6", 5.000, 6.000, 7.000))
    variable.addTerm(new Triangle("NEAR_7", 6.000, 7.000, 8.000))
    variable.addTerm(new Triangle("NEAR_8", 7.000, 8.000, 9.000))
    variable.addTerm(new Triangle("NEAR_9", 8.000, 9.000, 10.000))
    variable
  }
}

object InitV extends OutputVariableInitializer {
  override def init(variable: OutputVariable): OutputVariable = {
    variable.setEnabled(true)
    variable.addTerm(new Triangle("NEAR_1", 0.000, 1.000, 2.000))
    variable.addTerm(new Triangle("NEAR_2", 1.000, 2.000, 3.000))
    variable.addTerm(new Triangle("NEAR_3", 2.000, 3.000, 4.000))
    variable.addTerm(new Triangle("NEAR_4", 3.000, 4.000, 5.000))
    variable.addTerm(new Triangle("NEAR_5", 4.000, 5.000, 6.000))
    variable.addTerm(new Triangle("NEAR_6", 5.000, 6.000, 7.000))
    variable.addTerm(new Triangle("NEAR_7", 6.000, 7.000, 8.000))
    variable.addTerm(new Triangle("NEAR_8", 7.000, 8.000, 9.000))
    variable.addTerm(new Triangle("NEAR_9", 8.000, 9.000, 10.000))
    variable
  }
}
