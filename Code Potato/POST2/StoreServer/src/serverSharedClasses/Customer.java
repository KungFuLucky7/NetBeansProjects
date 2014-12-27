package serverSharedClasses;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

/**
 * Customer is responsible for initiating new transactions.
 *
 * @author Michael Santer
 */
public class Customer implements Serializable {

    private String firstName, lastName;

    /**
     * Constructor of customer is private. A new customer can only be
     * instantiated by calling the class method "getTransactions". This enforces
     * the schema that a customer must initiate a transaction.
     */
    public Customer() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * This method instantiates a new customer and a new transaction for every
     * transaction record in the file. Using transaction reader, it builds a
     * transactionRecord, and hands off to post to process transaction.
     *
     * @return
     */
    public static TransactionRecord getTransactions(String transactFile)
            throws FileNotFoundException, IOException, Exception {
        TransactionReader reader = new TransactionReader(transactFile);

        //while(reader.hasMoreTransactions()){
        //Instantiate empty customer
        Customer customer = new Customer();

        //Construct transactionRecord for customer
        TransactionRecord transaction = reader.getNextTransaction(customer);

        //Request POST to process the transaction
        return transaction;
        //}
    }

}
