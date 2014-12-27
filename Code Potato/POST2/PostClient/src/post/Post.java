package post;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverInterfaces.*;
import serverSharedClasses.*;

/**
 * Post's main responsibility is to perform the transaction. This includes 1)
 * making sure an item exists, 2)calculating the total, 3)processing payment,
 * 4)saving transactionRecord, and 5)printing the invoice.
 *
 * @author Michael Santer
 */
public class Post {

    private Store store;
    private Invoice invoice;

    public Post(Store store) {
        this.store = store;
    }

    /* ********** Methods for Interaction with Store ******** */
    /**
     * Gets a hashmap of all products from the server
     *
     * @return HashMap<String, ProductSpec>. Returns null, if server is offline.
     */
    public HashMap<String, ProductSpec> getProductCatalog() {
        try {
            return store.getProductCatalog();
        } catch (RemoteException ex) {
            System.err.println("Server is disocnnected." + ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param name
     * @return true if successful. false if server is disconnected
     */
    public boolean setStoreName(String name) {
        try {
            store.setName(name);
            return true;
        } catch (RemoteException ex) {
            System.err.println("Server is disocnnected." + ex.getMessage());
            return false;
        }
    }

    /**
     *
     * @return store name. Returns null if server is offline
     */
    public String getStoreName() {
        try {
            return store.getName();
        } catch (RemoteException ex) {
            System.err.println("Server is disocnnected." + ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @param transact
     * @return true if successful. False if server is disconnected.
     */
    public boolean saveTransactionToStore(TransactionRecord transact) throws RemoteException {
        store.saveTransaction(transact);
        return true;
    }

    /* ********** Methods for Interaction with TransactionRecords ******** */
    /**
     * Creates a new transactionRecord that is empty
     *
     * @param customerName
     * @return
     */
    public TransactionRecord createTransaction() {
        TransactionRecord transaction = new TransactionRecord();
        return transaction;
    }

    /**
     * Adds customer name to transactionRecord
     *
     * @param transact
     * @param customerName
     */
    public void setCustomer(TransactionRecord transact, String customerName) {
        Customer customer = new Customer();
        StringTokenizer tok = new StringTokenizer(customerName);

        //parse customer name into first and last
        customer.setFirstName(tok.nextToken());
        if (tok.hasMoreTokens()) {
            customer.setLastName(tok.nextToken());
        }
        transact.setCustomer(customer);
    }

    /**
     * Adds item to transactionRecord. If Item already exists, it updates it.
     *
     * @param transaction
     * @param item
     */
    public void addItemtoTransaction(TransactionRecord transaction, Item item) {
        if (!transaction.hasItem(item)) {
            transaction.addItem(item);
        } else {
            transaction.updateItem(item);
        }
    }

    public TransactionRecord transact(TransactionRecord transaction) throws Exception {
        double total = 0.;
        ArrayList<Item> items = transaction.getItems();

        //1. make sure items exist in catalog
        for (Item item : items) {
            if (!store.hasProduct(item.getUPC())) {
                throw new Exception("UPC not found!");
            }

            //2. calculate total
            total += (store.getProduct(item.getUPC()).getPrice()) * item.getQuantity();
        }

        //3. process payment
        double amount = transaction.getPayment().processPayment(total);

        //4. print invoice
        invoice = new Invoice(transaction, total, store);
        invoice.setAmountReturned(amount);
        invoice.print();

        //5. save transactionRecord to store's transactionHistory
        return transaction;

    }

    /**
     * Get the total of current transaction.
     */
    public double getTotal(TransactionRecord transaction) throws Exception {
        double total = 0.;
        ArrayList<Item> items = transaction.getItems();

        //1. make sure items exist in catalog
        for (Item item : items) {
            if (!store.hasProduct(item.getUPC())) {
                throw new Exception("UPC not found!");
            }

            //2. calculate total
            total += (store.getProduct(item.getUPC()).getPrice()) * item.getQuantity();
        }

        return total;
    }

    @Override
    public String toString() {
        return invoice.printToScreen();
    }
}
