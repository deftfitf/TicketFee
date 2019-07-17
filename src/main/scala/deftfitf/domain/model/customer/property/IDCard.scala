package deftfitf.domain.model.customer.property

import deftfitf.domain.model.time.Time

case class IDCard(yyyyMMdd: Int) {

  def age(time: Time): Age =
    Age((time.yyyyMMdd - yyyyMMdd) / 10000)

}
