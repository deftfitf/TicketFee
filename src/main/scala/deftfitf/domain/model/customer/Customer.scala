package deftfitf.domain.model.customer

import deftfitf.domain.model.customer.property.{ IDCard, MICard, _ }
import deftfitf.domain.model.ticket.Coupon

sealed abstract class Customer(
  val threeDimGlass: Option[ThreeDimGlass.type],
  val studentNote: Option[StudentNote.type],
  val studentCard: Option[StudentCard.type],
  val mICard: Option[MICard.type],
  val disabilityHandbook: Option[DisabilityHandbook.type],
  val iDCard: Option[IDCard],
  val parkingTicket: Option[ParkingTicket.type],
  val coupons: Seq[Coupon])

object Customer {

  case class General(
    override val threeDimGlass: Option[ThreeDimGlass.type],
    override val studentNote: Option[StudentNote.type],
    override val studentCard: Option[StudentCard.type],
    override val mICard: Option[MICard.type],
    override val disabilityHandbook: Option[DisabilityHandbook.type],
    override val iDCard: Option[IDCard],
    override val parkingTicket: Option[ParkingTicket.type],
    override val coupons: Seq[Coupon])
    extends Customer(
      threeDimGlass, studentNote, studentCard,
      mICard, disabilityHandbook, iDCard, parkingTicket, coupons)

  case class PreschoolChildren(
    override val threeDimGlass: Option[ThreeDimGlass.type],
    override val coupons: Seq[Coupon])
    extends Customer(
      threeDimGlass, None, None, None, None, None, None, coupons)

}

