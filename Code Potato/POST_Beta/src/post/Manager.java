/* 
 * Copyright (C) 2014 Terry Wong, Steven Senatori, Jung Hwan Kim
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package post;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The manager will open the store, set up Post(s), put together the product
 * catalog.
 *
 * @author Terry Wong
 */
public class Manager {

    private Store store;
    private String Invoice = "";
    private ProductCatalog productCatalog;
    private TransactionReader transactionRecord;
    private TransactionLog transactionLog;
    private ArrayList<Transaction> transactions;
    private HashMap<String, Integer> transactionPurchases;

    /**
     * The constructor of a Manager class sets up a store and loads the record
     * of all transactions.
     *
     * @param productFile
     * @param transactionFile
     */
    public Manager(String productFile, String transactionFile) {
        try {
            store = new Store();
            store.init(productFile);
            transactionRecord = new TransactionReader(transactionFile);
            transactionLog = new TransactionLog();
        } catch (IOException e) {
            System.err.println("**** " + e);
        }
    }

    /**
     * Load transactions and print an invoice
     */
    public void manage() {
        try {
            transactionRecord.loadTransactions();
            transactionRecord.close();
            generateInvoice();
            System.out.println("INVOICE PRINTED:");
            System.out.print(Invoice);
            logInvoice();
        } catch (IOException e) {
            System.err.println("**** " + e);
        }

    }

    /**
     * Generate an invoice for printing
     */
    private void generateInvoice() throws IOException {
        productCatalog = store.getProducts();
        transactions = transactionRecord.getTransactions();
        Invoice += "______________________________________________________\n\n";
        Invoice += store.getName() + "\n";
        Invoice += "______________________________________________________\n\n";
        for (Transaction t : transactions) {
            double purchaseTotal = 0.;
            Invoice += String.format("%-25s %-20s\n", "Customer Name:", "Date & Time:");
            Invoice += String.format("%-25s %-20s\n\n", t.getCustomer().getName(), t.getDateTime());
            Invoice += String.format("%-12s %-12s %-12s %-12s\n", "Item:", "QTY:", "Unit Price:", "Subtotal:");
            transactionPurchases = t.getCustomer().getPurchases();
            //each entry is a purchase
            for (Map.Entry entry : transactionPurchases.entrySet()) //entry iterates through each customer object's purchases 
            {
                Product product = productCatalog.getProduct(entry.getKey().toString()); //doing product lookup
                if (product != null) {
                    int quantity = Integer.valueOf(entry.getValue().toString());
                    purchaseTotal += product.getPrice() * quantity;
                    Invoice += String.format("%-12s %-12s %-12s %-12.2f\n", product.getDescription(), entry.getValue(), product.getPrice(), product.getPrice() * quantity);
                } else {
                    throw new IOException("**** UPC doesn't exist! ****");
                }
            }
            Invoice += "------------------------------------------------------\n";
            Invoice += String.format("Total: %.2f\n", purchaseTotal);
            // Use dynamic binding for getting the correct payment statement
            IPayment payment = t.getPayment();
            if (payment instanceof CashPayment) {
                CashPayment cashPayment = (CashPayment) payment;
                cashPayment.setAmountDue(purchaseTotal);
                Invoice += cashPayment.statePayment();
            } else if (payment instanceof CheckPayment) {
                CheckPayment checkPayment = (CheckPayment) payment;
                Invoice += checkPayment.statePayment();
            } else if (payment instanceof CreditPayment) {
                CreditPayment creditPayment = (CreditPayment) payment;
                Invoice += creditPayment.statePayment();
            }
            Invoice += "\n******************************************************\n\n";
        }
        Invoice += "______________________________________________________\n";
    }

    /**
     * Logs an invoice for record
     */
    private synchronized void logInvoice() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("invoice.txt", false));
            writer.write(Invoice);
            writer.close();
        } catch (IOException e) {  // if there is an error in writing the file
            System.err.println(e.getMessage());
        }
    }
}
