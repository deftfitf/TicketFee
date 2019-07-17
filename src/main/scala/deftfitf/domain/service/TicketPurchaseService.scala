package deftfitf.domain.service

import deftfitf.domain.model.cinema.Cinema
import deftfitf.domain.model.cinema.Cinema.ThreeDimCinema
import deftfitf.domain.model.customer.Customer
import deftfitf.domain.model.ticket.{TicketPrice, Tickets}
import deftfitf.domain.model.time.Time

class TicketPurchaseService(tickets: Tickets) {

  import TicketPurchaseService._

  def purchase(customers: Seq[Customer], time: Time, cinema: Cinema): Seq[TicketPrice] =
    for {
      customer <- customers
      ticketPrices = tickets.pricesOf(time, customer, cinema)
      minTicketPrice = ticketPrices.reduce(
        (minPrice, nextPrice) => minPrice min customer.coupons.foldLeft(nextPrice)(
          (appliedPrice, nextCoupon) => appliedPrice apply nextCoupon))
    } yield cinema match {
      case ThreeDimCinema =>
        minTicketPrice plus
          customer.threeDimGlass
            .fold(ThreeDimCinemaAppendPrice)(_ => ThreeDimGlassDiscountedPrice)
      case _ => minTicketPrice
    }

}

object TicketPurchaseService {

  private val ThreeDimCinemaAppendPrice = TicketPrice(400)
  private val ThreeDimGlassDiscountedPrice = TicketPrice(300)

}