package serverSharedClasses;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/**
 * TransactionRecord contains the necessary informations to describe and perform
 * a transaction.
 *
 * @author Michael Santer
 */
public class TransactionRecord implements Serializable {

    private Customer customer;
    private Payment payment;
    private ArrayList<Item> items;
    private Date dateAndTime;

    /**
     * A TransactionRecord must be instantiated with a customer. This helps to
     * enforce, along with the privatizing of the customer constructor, that
     * only a customer should start a transaction.
     *
     * @param customer
     */
    public TransactionRecord() {
        //set date and time to current date and time
        dateAndTime = new Date();
        items = new ArrayList<>();
    }

    public String getCustomerFirstName() {
        return customer.getFirstName();
    }

    public String getCustomerLastName() {
        return customer.getLastName();
    }

    // Add a new item to a transaction
    public boolean addItem(Item item) {
        if (!items.contains(item)) {
            items.add(item);
            return true;
        } else {
            return false;
        }
    }

    // Update an existing transaction item
    public void updateItem(Item transItem) {
        for (Item item : items) {
            if (item.getUPC().equals(transItem.getUPC())) {
                item.setQuantity(transItem.getQuantity());
            }
        }
    }

    // Check if item with given UPC already exists
    public boolean hasItem(Item transItem) {
        for (Item item : items) {
            if (item.getUPC().equals(transItem.getUPC())) {
                return true;
            }
        }
        return false;
    }

    // Check if transaction has any items
    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Remove an item from transaction
    public void removeItem(Item transItem) throws RemoteException {
        for (Item item : items) {
            if (item.getUPC().equals(transItem.getUPC())) {
                items.remove(item);
                return;
            }
        }
    }

    // Remove all item from transaction
    public void removeAllItem() throws RemoteException {
        items.clear();
    }

    public void setPayment(Payment pay) {
        payment = pay;
    }

    public Payment getPayment() {
        return payment;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public ArrayList getItems() {
        return items;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
