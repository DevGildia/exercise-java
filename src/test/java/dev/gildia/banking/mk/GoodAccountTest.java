package dev.gildia.banking.mk;

import static org.assertj.core.api.Assertions.assertThat;

import dev.gildia.banking.Account;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodAccountTest {

  private final Clock fixed = Clock.fixed(Instant.parse("2018-08-19T16:02:42.00Z"), ZoneId.of("Europe/Warsaw"));
  private Account account;

  @BeforeEach
  public void setUp() {
    account = new Account(fixed);
  }

  @Test
  public void should_return_empty_table() {
    assertThat(account.printStatement()).isEqualTo("Date | Amount | Balance\n");
  }

  @Test
  public void should_return_statement_with_date() {
    //when
    account.deposit(100);

    //then
    assertThat(account.printStatement()).containsPattern("2018-08-19T([0-9]{2}:[0-9]{2}:[0-9]{2})");
  }

  @Test
  public void should_return_statement_with_correct_transactions() {
    //when
    account.deposit(100);
    account.withdraw(50);
    account.deposit(75);
    account.withdraw(125);

    //then
    assertThat(account.printStatement())
        .contains("| +100 | 100\n")
        .contains("| -50 | 50\n")
        .contains("| +75 | 125\n")
        .contains("| -125 | 0\n");
  }

  @Test
  public void should_throw_exception_with_negative_deposit_amount() {
    Assertions.assertThatThrownBy(() -> account.deposit(-100))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("amount must be grater then 0, but was: -100");
  }

  @Test
  public void should_throw_exception_with_negative_withdraw_amount() {
    Assertions.assertThatThrownBy(() -> account.withdraw(-100))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("amount must be grater then 0, but was: -100");
  }

  @Test
  public void should_throw_exception_with_withdraw_over_account_current_deposit() {

    account.deposit(50);

    Assertions.assertThatThrownBy(() -> account.withdraw(-100))
        .isInstanceOf(IllegalArgumentException.class);
  }


}
