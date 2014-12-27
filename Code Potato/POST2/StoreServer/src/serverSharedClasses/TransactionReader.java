package serverSharedClasses;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * TransactionReader reads a list of transactions records from a file, and
 * constructs the appropriate transactionRecord objects.
 *
 * @author Michael Santer
 */
class TransactionReader {

    private BufferedReader reader;
    private StringTokenizer tok;

    public TransactionReader(String transactFile) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(transactFile));
    }

    /**
     * Checks if there is more to read in the file.
     *
     * @return true if there are more lines to read.
     */
    public boolean hasMoreTransactions() {
        try {
            if (reader.ready()) {
                return true;
            }
        } catch (IOException ex) {
            System.err.print(ex);
        }
        return false;
    }

    /**
     * Reads the next transactionRecord from the file, and builds a single
     * transactionRecord object.
     *
     * @param customer
     * @return transactionRecord
     * @throws IOException if there is an error reading file.
     */
    public TransactionRecord getNextTransaction(Customer customer) throws IOException {
        TransactionRecord transaction;

        //read first line to get customer name
        String name = reader.readLine();
        tok = new StringTokenizer(name);

        //parse customer name into first and last
        try {
            customer.setFirstName(tok.nextToken());
            customer.setLastName(tok.nextToken());
        } catch (NoSuchElementException ex) {
            //do nothing. name is not necessary
        }

        //create new transaction with constructed customer
        transaction = new TransactionRecord();
        transaction.setCustomer(customer);

        //read subsequent lines
        String next = reader.readLine();
        while (next != null && !next.isEmpty()) {
            tok = new StringTokenizer(next);
            Item item = new Item();
            ProductSpec product = new ProductSpec();

            //if next line doesn't specify a payment, it must specify and item
            if (!next.contains("CASH") && !next.contains("CHECK") && !next.contains("CREDIT")) {
                //set upc of item
                product.setUPC(tok.nextToken());
                item.setProductSpec(product);

                //try to get quantity
                if (tok.hasMoreTokens()) {
                    item.setQuantity(Integer.parseInt(tok.nextToken()));
                } else {
                    item.setQuantity(1);
                }

                //add item to transaction
                transaction.addItem(item);
            } //if next line specifies a payment, record payment
            else {
                Payment pay;
                String temp = tok.nextToken();
                if (temp.equals("CASH")) {
                    pay = new CashPayment();
                    pay.setAmount(Double.parseDouble(tok.nextToken().replace("$", "")));
                } else if (temp.equals("CHECK")) {
                    pay = new CheckPayment();
                    pay.setAmount(Double.parseDouble(tok.nextToken().replace("$", "")));
                } else { //it's credit
                    int accountNumber = Integer.parseInt(tok.nextToken());
                    pay = (new CreditPayment(accountNumber));
                }

                transaction.setPayment(pay);
            }
            next = reader.readLine();
        }

        return transaction;
    }

}
