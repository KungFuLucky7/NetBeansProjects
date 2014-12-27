package post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author Michael Santer
 *
 * PostUI handles all user interfacing. Communicates between Manager and Post.
 */
public class PostUI {

    private Manager manager;
    private Post post;

    public PostUI(Manager manager) {
        this.manager = manager;
    }

    /**
     * This method handles the high level flow of the UI.
     *
     * @param manager
     */
    public void run(Manager manager) {
        Scanner in = new Scanner(System.in);

        System.out.println("Welcome to the point of sale terminal (POST)!");

        //Get name of store and ask manager to open store and init post. 
        System.out.print("Please enter the name of the store: ");
        try {
            manager.openStore(in.nextLine());
            manager.initPost();
            post = manager.getPost();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        //Get name of product catalog and ask manager to initialize it. 
        System.out.print("Please enter the name of Product Catalog file: ");
        try {
            manager.setupProductCatalog(in.nextLine());
        } catch (IOException ex) {
            System.err.println("File was not found!");
            return;
        }

        for (int i = 0; i < 1; i++) {
            // (create useful condition eventually)

            //get transaction file
            System.out.print("Please enter the name of Transaction file: ");
            String transactFile = in.nextLine();

            /*Request Customer class to get transactions from file.
             *An available post is sent so that the customer can pass off the
             *transaction to the post for processing. */
            try {
                Customer.getTransactions(transactFile, post);
            } catch (FileNotFoundException ex) {
                System.err.println("File was not found!");
            } catch (IOException ex) {
                System.err.println("Error reading file. Check file format.");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        }
    }
}
