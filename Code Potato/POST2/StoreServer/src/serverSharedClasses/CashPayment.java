package serverSharedClasses;

/**
 * CashPayment defines how cash payments are processed. 
 * @author Michael Santer
 */
public class CashPayment extends Payment {

    public CashPayment() {
    }

    @Override
    public double processPayment(double total) {
        return this.getAmount() - total;
    }
    
}
