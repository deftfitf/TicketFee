package deftfitf.domain.model.cinema

sealed trait Cinema {
  def isBigSoundScreeming: Boolean = false
}

object Cinema {
  case object SpecialPerformance extends Cinema
  case object Cinema extends Cinema
  case object ThreeDimCinema extends Cinema
  case object BigSoundScreeming extends Cinema {
    override def isBigSoundScreeming: Boolean = true
  }
}