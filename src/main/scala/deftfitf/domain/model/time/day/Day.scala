package deftfitf.domain.model.time.day

sealed trait Day

object Day {
  case object WeekEndAndHoliday extends Day
  case object Weekday extends Day
  case object CinemaDay extends Day
}