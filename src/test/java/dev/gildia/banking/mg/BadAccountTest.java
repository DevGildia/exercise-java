package dev.gildia.banking.mg;

import dev.gildia.banking.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

class BadAccountTest {
  Clock fixed = Clock.fixed(Instant.parse("2018-08-19T16:02:42.00Z"), ZoneId.of("Europe/Warsaw"));
  Account account = new Account(fixed);

  @Test
  void TestujęWyrzucanieBłędu() {
    Account account = new Account(fixed);
    final int amount = 100;
    Assertions.assertThatIllegalStateException()
            .isThrownBy(() -> {
              account.withdraw(amount);
            });
  }

  @Test
  void shouldReturnExceptionWithDescriptionWhenWithdrawNegativeValueOfAmount() {
    Account a_c_c_o_u_n_t = new Account(fixed);
    final String expected_description2 = "amount must be greater then 0, but was: " + -100;
    try {
      account.withdraw(-100);
    } catch (IllegalArgumentException e) {
      org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> account.withdraw(-100));
    }
  }

  @Test
  void shouldReturnReportWithBalanceEqualsZeroWhenDepositAndWithdrawHaveTheSameAmount() {
    int Amount = 100;
    Account acccount = new Account(fixed);
    final String expectedReport = "Date | Amount | Balance" + "\n" + "2018-08-19T18:02:42 | +100 | 100" + "\n" + "2018-08-19T18:02:42 | -100 | 0" + "\n";

    acccount.deposit(Amount);
    acccount.withdraw(Amount);

    Assertions.assertThat(acccount.printStatement())
            .isEqualTo(expectedReport);
    return;
  }

  @Test
  void shouldReturnReportWithPositiveBalanceWhenDepositMoreThanWithdrawAmount() {
    final int depositAmount = 100;
    final int withdrawAmount = 30;

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Date | Amount | Balance\n");

    stringBuilder = new StringBuilder();
    stringBuilder.append("Date | Amount | Balance\n");

    String a = stringBuilder.toString() +
            "2018-08-19T18:02:42 | +100 | 100\n" +
            "2018-08-19T18:02:42 | -30 | 70\n";

    account.deposit(depositAmount);
    account.withdraw(withdrawAmount);

    Assertions.assertThat(account.printStatement())
            .isEqualTo(a);
  }

  @Test
  void shouldNotReturnAnyTransactionsWhenNoOperationsWereDone() {
    String expected_Report = "Date | Amount | Balance\n";

//    Assertions.assertThat(account.printStatement())
//            .isEqualTo(expected_Report);
//    Assertions.assertThat(account.printStatement())
//            .isEqualTo(expected_Report);
//    Assertions.assertThat(account.printStatement())
//            .isEqualTo(expected_Report);
//    Assertions.assertThat(account.printStatement())
//            .isEqualTo(expected_Report);

    Assertions.assertThat(account.printStatement())
            .isEqualTo(expected_Report);
  }
}
