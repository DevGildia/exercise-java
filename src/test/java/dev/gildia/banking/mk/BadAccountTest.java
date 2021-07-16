package dev.gildia.banking.mk;

import dev.gildia.banking.Account;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class BadAccountTest {

  Clock fixed = Clock.fixed(Instant.parse("2018-08-19T16:02:42.00Z"), ZoneId.of("Europe/Warsaw"));
  Account account = new Account(fixed);

  @Test
  public void test1() {
    Assertions.assertThat(account.printStatement()).isEqualTo("Date | Amount | Balance\n");
  }

  @Test
  public void test2() {
    account.deposit(100);
    account.withdraw(100);
    Assertions.assertThat(account.printStatement()).isEqualTo("Date | Amount | Balance\n"
        + "2018-08-19T18:02:42 | +100 | 100\n"
        + "2018-08-19T18:02:42 | -100 | 0\n");
  }

  @Test
  public void test3() {
    Exception exception = null;
    try {
      account.deposit(-10);
      account.withdraw(-10);
    } catch (Exception e) {
      exception = e;
    }
    Assertions.assertThat(exception).isNotNull();
    Assertions.assertThat(exception.getMessage()).contains("was: -10");
  }

  @Test
  public void test4() {
    Exception exception = null;
    try {
      account.deposit(10);
      account.withdraw(-20);
    } catch (Exception e) {
      exception = e;
    }
    Assertions.assertThat(exception).isNotNull();
  }
}
