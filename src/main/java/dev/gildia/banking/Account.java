package dev.gildia.banking;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {

  private final Clock clock;
  private final List<Transaction> transactions = new ArrayList<>();

  public Account(Clock clock) {
    this.clock = clock;
  }

  public void deposit(int amount) {
    transactions.add(new Transaction(amount, LocalDateTime.now(clock), TxType.DEPOSIT));
  }

  public void withdraw(int amount) {
    if (currentBalance() - amount < 0) {
      throw new IllegalStateException();
    }
    transactions.add(new Transaction(amount, LocalDateTime.now(clock), TxType.WITHDRAW));
  }

  public String printStatement() {
    var builder = new StringBuilder();
    builder.append("Date");
    builder.append(" | ");
    builder.append("Amount");
    builder.append(" | ");
    builder.append("Balance");
    builder.append("\n");
    List<Transaction> txTillNow = new ArrayList<>();
    for (Transaction tx : transactions) {
      txTillNow.add(tx);
      builder.append(tx.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
      builder.append(" | ");

      switch (tx.type) {
        case WITHDRAW: builder.append("-"); break;
        case DEPOSIT: builder.append("+"); break;
        default:
          throw new IllegalStateException("Unexpected value: " + tx.type);
      }

      builder.append(tx.amount);
      builder.append(" | ");
      builder.append(balance(txTillNow));
      builder.append("\n");
    }

    return builder.toString();
  }


  private int currentBalance() {
    return balance(transactions);
  }

  private int balance(List<Transaction> transactions) {
    return transactions.stream().map(tx -> {
      switch (tx.type) {
        case DEPOSIT:
          return tx.amount;
        case WITHDRAW:
          return Math.negateExact(tx.amount);
        default:
          throw new IllegalArgumentException();
      }
    }).reduce(0, Integer::sum);
  }

  private static class Transaction {
    private final int amount;
    private final LocalDateTime dateTime;
    private final TxType type;

    private Transaction(int amount, LocalDateTime dateTime, TxType type) {
      if (amount <= 0) {
        throw new IllegalArgumentException("amount must be grater then 0, but was: " + amount);
      }
      this.amount = amount;
      this.dateTime = dateTime;
      this.type = type;
    }

  }

  private enum TxType {
    DEPOSIT,WITHDRAW
  }
}
