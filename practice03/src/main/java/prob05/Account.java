package prob05;

public class Account {
    private String accountNo;

    private int balance;

    public Account(String accountNo) {
        this.accountNo = accountNo;

        System.out.println(accountNo + " 계좌가 개설 되었습니다.");
    }

    public void save(int amount) {
        balance += amount;

        System.out.println(accountNo + " 계좌에 " + amount + "만원이 입금 되었습니다.");
    }

    public void deposit(int amount) {
        balance -= amount;

        System.out.println(accountNo + " 계좌에 " + amount + "만원이 출금 되었습니다.");
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Integer getBalance() {
        return balance;
    }
}
