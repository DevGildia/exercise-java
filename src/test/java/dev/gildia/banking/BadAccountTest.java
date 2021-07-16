package dev.gildia.banking;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

class BadAccountTest {
  Clock fixed = Clock.fixed(Instant.parse("2018-08-19T16:02:42.00Z"), ZoneId.of("Europe/Warsaw"));
  
}
