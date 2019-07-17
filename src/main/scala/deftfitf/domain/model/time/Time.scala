package deftfitf.domain.model.time

import com.github.nscala_time.time.Imports._
import deftfitf.domain.model.time.day.Day

case class Time(dateTime: DateTime, day: Day) {

  import Time._
  private val localTime = dateTime.toLocalTime

  def isLateShowTime: Boolean =
    localTime isAfter LateShowStartTime

  def yyyyMMdd: Int =
    dateTime.toString("yyyyMMdd").toInt

}

object Time {

  private final val LateShowStartTime = LocalTime.parse("20:00:00")

}