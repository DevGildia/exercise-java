package dev.gildia.banking.mg;

import dev.gildia.banking.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

class GoodAccountTest {
  Clock fixed = Clock.fixed(Instant.parse("2018-08-19T16:02:42.00Z"), ZoneId.of("Europe/Warsaw"));
  Account account;

  @BeforeEach
  void setUp() {
    account = new Account(fixed);
  }

  @Test
  void shouldReturnExceptionWhenWithdrawTooMuchAmount() {
    final int amount = 100;
    Assertions.assertThatIllegalStateException()
            .isThrownBy(() -> account.withdraw(amount));
  }

  @Test
  void shouldReturnExceptionWithDescriptionWhenWithdrawNegativeAmount() {
    final int amount = -100;
    final String expectedDescription = "amount must be greater then 0, but was: " + amount;
    Assertions.assertThatIllegalArgumentException()
            .describedAs(expectedDescription)
            .isThrownBy(() -> account.withdraw(amount));
  }

  @Test
  void shouldReturnExceptionWithDescriptionWhenWithdrawZeroAmount() {
    final int amount = 0;
    final String expectedDescription = "amount must be greater then 0, but was: " + amount;
    Assertions.assertThatIllegalArgumentException()
            .describedAs(expectedDescription)
            .isThrownBy(() -> account.withdraw(amount));
  }

  @Test
  void shouldReturnReportWithBalanceEqualsZeroWhenDepositAndWithdrawHaveTheSameAmount() {
    final int amount = 100;
    final int expectedAmount = 0;

    LocalDateTime dateTime = LocalDateTime.now(fixed);
    String formatDateTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    var builder = new StringBuilder();
    builder.append("Date | Amount | Balance\n");
    builder.append(formatDateTime);
    builder.append(" | +");
    builder.append(amount);
    builder.append(" | ");
    builder.append(amount);
    builder.append("\n");
    builder.append(formatDateTime);
    builder.append(" | -");
    builder.append(amount);
    builder.append(" | ");
    builder.append(expectedAmount);
    builder.append("\n");
    String expectedReport = builder.toString();

    account.deposit(amount);
    account.withdraw(amount);

    Assertions.assertThat(account.printStatement())
            .isEqualTo(expectedReport);
  }

  @Test
  void shouldReturnReportWithPositiveBalanceWhenDepositIsGreaterThanWithdrawAmount() {
    final int depositAmount = 100;
    final int withdrawAmount = 30;
    final int expectedAmount = 70;

    LocalDateTime dateTime = LocalDateTime.now(fixed);
    String formatDateTime = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    var builder = new StringBuilder();
    builder.append("Date | Amount | Balance\n");
    builder.append(formatDateTime);
    builder.append(" | +");
    builder.append(depositAmount);
    builder.append(" | ");
    builder.append(depositAmount);
    builder.append("\n");
    builder.append(formatDateTime);
    builder.append(" | -");
    builder.append(withdrawAmount);
    builder.append(" | ");
    builder.append(expectedAmount);
    builder.append("\n");
    String expectedReport = builder.toString();

    account.deposit(depositAmount);
    account.withdraw(withdrawAmount);

    Assertions.assertThat(account.printStatement())
            .isEqualTo(expectedReport);
  }

  @Test
  void shouldNotReturnAnyTransactionsWhenNoOperationsWereDone() {
    String expectedReport = "Date | Amount | Balance\n";

    Assertions.assertThat(account.printStatement())
            .isEqualTo(expectedReport);
  }
}
