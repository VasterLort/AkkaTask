package by.itechart.event

sealed trait Event

case class CounterIncrementEvent() extends Event

case class CounterResetEvent() extends Event


