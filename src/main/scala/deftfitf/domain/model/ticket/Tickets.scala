package deftfitf.domain.model.ticket

import deftfitf.domain.model.cinema.Cinema
import deftfitf.domain.model.customer.Customer
import deftfitf.domain.model.time.Time

class Tickets(tickets: Seq[Ticket]) {

  def pricesOf(time: Time, customer: Customer, cinema: Cinema): Seq[TicketPrice] =
    tickets flatMap { ticket =>
      ticket.priceOf(time, customer, cinema)
    }

}
