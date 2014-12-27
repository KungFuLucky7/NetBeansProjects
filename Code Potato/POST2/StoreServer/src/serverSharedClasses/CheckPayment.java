package serverSharedClasses;

/**
 * CheckPayment defines how check payments are processed.
 * @author Michael Santer
 */
public class CheckPayment extends Payment {

    public CheckPayment() {
    }

    @Override
    public double processPayment(double total) {
        return this.getAmount() - total;
    }
    
}
