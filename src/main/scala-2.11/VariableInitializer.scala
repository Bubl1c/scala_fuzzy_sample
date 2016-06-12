import com.fuzzylite.variable.Variable

/**
  * Created by Andrii on 6/12/2016.
  */
trait VariableInitializer[T <: Variable] {
  def init(variable: T): T
  def apply(name: String, min: Double, max: Double): T = init(new T(name, min, max))
}
