package deftfitf.domain.model.ticket

sealed abstract class Coupon(val discountAmount: Int)

object Coupon {
  case class Combined(override val discountAmount: Int) extends Coupon(discountAmount)
  case class InCombined(override val discountAmount: Int) extends Coupon(discountAmount)
}