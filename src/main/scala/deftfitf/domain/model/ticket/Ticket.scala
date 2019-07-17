package deftfitf.domain.model.ticket

import deftfitf.domain.model.cinema.Cinema
import deftfitf.domain.model.customer.Customer
import deftfitf.domain.model.customer.Customer.PreschoolChildren
import deftfitf.domain.model.ticket.TicketPrice._
import deftfitf.domain.model.time.Time
import deftfitf.domain.model.time.day.Day.{ CinemaDay, WeekEndAndHoliday, Weekday }

sealed abstract class Ticket(
  val dayTimePrice: TicketPrice,
  val lateShowPrice: TicketPrice,
  val dayTimePriceOnWeekend: TicketPrice,
  val lateShowPriceOnWeekend: TicketPrice,
  val cinemaDayPrice: Option[TicketPrice]) {

  def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
    time.day match {
      case Weekday =>
        if (time.isLateShowTime && !cinema.isBigSoundScreeming) Some(lateShowPrice)
        else Some(dayTimePrice)
      case WeekEndAndHoliday =>
        if (time.isLateShowTime && !cinema.isBigSoundScreeming) Some(lateShowPriceOnWeekend)
        else Some(dayTimePriceOnWeekend)
      case CinemaDay => cinemaDayPrice
    }

}

object Ticket {

  case object GeneralTicket
    extends Ticket(Price(1800), Price(1300), Price(1800), Price(1300), Some(Price(1100)))

  case object SeniorTicket
    extends Ticket(Price(1100), Price(1100), Price(1100), Price(1100), Some(Price(1100))) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer.iDCard.fold(None: Option[TicketPrice]) { card =>
        if (card.age(time).isSenior)
          super.priceOf(time, customer, cinema)
        else
          None
      }
  }

  case object StudentTicket
    extends Ticket(Price(1500), Price(1300), Price(1500), Price(1300), Some(Price(1100))) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer.studentCard.fold(None: Option[TicketPrice])(_ =>
        super.priceOf(time, customer, cinema))
  }

  case object MiddleHighSchoolStudentTicket
    extends Ticket(Price(1000), Price(1000), Price(1000), Price(1000), Some(Price(1000))) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer.studentNote.fold(None: Option[TicketPrice])(_ =>
        super.priceOf(time, customer, cinema))
  }

  case object PreschoolTicket
    extends Ticket(Price(1000), Price(1000), Price(1000), Price(1000), Some(Price(1000))) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer match {
        case _: PreschoolChildren =>
          super.priceOf(time, customer, cinema)
        case _ => None
      }
  }

  case object MICardTicket
    extends Ticket(FixedPrice(1600), FixedPrice(1300), FixedPrice(1600), FixedPrice(1300), None) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer.mICard.fold(None: Option[TicketPrice])(_ =>
        super.priceOf(time, customer, cinema))
  }

  case object DiscountedParkingLot80Ticket
    extends Ticket(Price(1400), Price(1100), Price(1400), Price(1100), None) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer.parkingTicket.fold(None: Option[TicketPrice])(_ =>
        super.priceOf(time, customer, cinema))
  }

  sealed abstract class CinemaCitizenTicket(
    override val dayTimePrice: Price,
    override val lateShowPrice: Price,
    override val dayTimePriceOnWeekend: Price,
    override val lateShowPriceOnWeekend: Price,
    override val cinemaDayPrice: Option[Price])
    extends Ticket(
      dayTimePrice: Price,
      lateShowPrice: Price,
      dayTimePriceOnWeekend: Price,
      lateShowPriceOnWeekend: Price,
      cinemaDayPrice: Option[Price]) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer.mICard.fold(None: Option[TicketPrice])(_ =>
        super.priceOf(time, customer, cinema))
  }

  object CinemaCitizenTicket {
    case object CinemaCitizenGeneralTicket
      extends CinemaCitizenTicket(Price(1000), Price(1000), Price(1300), Price(1000), Some(Price(1100)))
    case object CinemaCitizenSeniorTicket
      extends CinemaCitizenTicket(Price(1000), Price(1000), Price(1000), Price(1000), Some(Price(1000)))
  }

  sealed abstract class DisabledPersonTicket(
    override val dayTimePrice: Price,
    override val lateShowPrice: Price,
    override val dayTimePriceOnWeekend: Price,
    override val lateShowPriceOnWeekend: Price,
    override val cinemaDayPrice: Option[Price])
    extends Ticket(
      dayTimePrice: Price,
      lateShowPrice: Price,
      dayTimePriceOnWeekend: Price,
      lateShowPriceOnWeekend: Price,
      cinemaDayPrice: Option[Price]) {
    override def priceOf(time: Time, customer: Customer, cinema: Cinema): Option[TicketPrice] =
      customer.disabilityHandbook.fold(None: Option[TicketPrice])(_ =>
        super.priceOf(time, customer, cinema))
  }

  object DisabledPersonTicket {
    case object DisabledStudentOrMoreTicket
      extends DisabledPersonTicket(Price(1000), Price(1000), Price(1000), Price(1000), Some(Price(1000)))
    case object DisabledHighSchoolAndBelowTicket
      extends DisabledPersonTicket(Price(900), Price(900), Price(900), Price(900), Some(Price(1000)))
  }

}
