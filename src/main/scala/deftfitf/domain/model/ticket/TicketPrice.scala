package deftfitf.domain.model.ticket

import deftfitf.domain.model.ticket.Coupon.{ Combined, InCombined }

sealed abstract class TicketPrice(val value: Int) {

  def apply(coupon: Coupon): TicketPrice

  def min(ticketPrice: TicketPrice): TicketPrice =
    if (value < ticketPrice.value) this
    else ticketPrice

  def plus(ticketPrice: TicketPrice): TicketPrice

}

object TicketPrice {

  def apply(value: Int): TicketPrice = Price(value)

  private def discount(value: Int, discountValue: Int): Int =
    if (value >= discountValue) value - discountValue
    else 0

  case class FixedPrice(override val value: Int) extends TicketPrice(value) {
    override def apply(coupon: Coupon): TicketPrice = this
    override def plus(ticketPrice: TicketPrice): TicketPrice =
      FixedPrice(value + ticketPrice.value)
  }

  case class Price(override val value: Int) extends TicketPrice(value) {
    override def apply(coupon: Coupon): TicketPrice =
      coupon match {
        case Combined(discountAmount) =>
          DiscountedPrice(value, value, discount(value, discountAmount))
        case InCombined(discountAmount) =>
          DiscountedPrice(value, discount(value, discountAmount), value)
      }
    override def plus(ticketPrice: TicketPrice): TicketPrice =
      Price(value + ticketPrice.value)
  }

  case class DiscountedPrice(
    originalValue: Int, maxFixedPriceValue: Int, discountedPriceValue: Int)
    extends TicketPrice(maxFixedPriceValue min discountedPriceValue) {

    override def apply(coupon: Coupon): TicketPrice =
      coupon match {
        case Combined(discountAmount) =>
          DiscountedPrice(
            originalValue,
            maxFixedPriceValue,
            discount(discountedPriceValue, discountAmount))
        case InCombined(discountAmount) =>
          DiscountedPrice(
            originalValue,
            discount(originalValue, discountAmount) min maxFixedPriceValue,
            discountedPriceValue)
      }
    override def plus(ticketPrice: TicketPrice): TicketPrice =
      DiscountedPrice(
        originalValue,
        maxFixedPriceValue + ticketPrice.value,
        discountedPriceValue + ticketPrice.value)

  }

}
