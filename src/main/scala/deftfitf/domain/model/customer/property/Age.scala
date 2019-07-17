package deftfitf.domain.model.customer.property

case class Age(value: Int) {

  def isSenior: Boolean =
    value >= 70

  def isOver60: Boolean =
    value >= 60

}