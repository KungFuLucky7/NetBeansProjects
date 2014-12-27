package post;

/**
 * CheckPayment defines how check payments are processed.
 * @author Michael Santer
 */
class CheckPayment extends Payment {

    public CheckPayment() {
    }

    @Override
    public double processPayment(double total) {
        return this.getAmount() - total;
    }
    
}
