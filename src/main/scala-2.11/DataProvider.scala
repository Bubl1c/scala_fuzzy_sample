import com.fuzzylite.variable.InputVariable

/**
  * Created by Andrii on 6/15/2016.
  */
trait DataProvider[T <: InputVariable] {
  def provide(variable: T): Array[Double] = {
    var i = 0
    Seq.fill(50)(eval).toArray
    def eval: Double = {
      i=i+1
      variable.getMinimum + i * (variable.range / 50)
    }
  }
}
