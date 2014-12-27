package serverSharedClasses;

/**
 * CreditPayment defines how credit payments are processed.
 *
 * @author Michael Santer
 */
public class CreditPayment extends Payment {

    private int accountNumber;

    public CreditPayment() {
    }

    public CreditPayment(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public double processPayment(double total) {
        return 0.;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

}
