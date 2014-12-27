package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import serverInterfaces.*;
import serverSharedClasses.*;

/**
 * Store contains a productCatalog, a POST, and a transactionRecord history.
 *
 * @author Michael Santer
 */
public class StoreImpl extends UnicastRemoteObject implements Store {

    private String name;
    //private Post post;
    private ProductCatalog productCatalog;
    private ArrayList<TransactionRecord> transactionHistory;

    public StoreImpl() throws RemoteException {
        this.name = "Default Store";
        productCatalog = new ProductCatalog();
        transactionHistory = new ArrayList<>();
    }

//    public Post getPost(){
//        return post;
//    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Creates a new POST.
     *
     * @throws Exception if POST has already initialized.
     */
//    public void newPost() throws Exception {
//        if (post != null)
//            throw new Exception("Post already exists!");
//        post = new Post(this);
//    }
    /**
     * Adds a single product to the product catalog.
     *
     * @param product
     */
    @Override
    public void addProductToCatalog(ProductSpec product) throws RemoteException {
        productCatalog.addProductToCatalog(product);
    }

    /**
     * Given a upc, checks if the product exists in the productCatalog.
     *
     * @param upc
     * @return true if product exists, false if not.
     */
    @Override
    public boolean hasProduct(String upc) {
        return productCatalog.hasProduct(upc);
    }

    /**
     * Returns the product spec for a given upc.
     *
     * @param upc
     * @return ProductSpec
     */
    @Override
    public ProductSpec getProduct(String upc) {
        return productCatalog.getProduct(upc);
    }

    @Override
    public HashMap<String, ProductSpec> getProductCatalog() {
        return productCatalog.getAllProducts();
    }

    @Override
    public String[] getUPCs() {
        return productCatalog.getUPCs().toArray(new String[0]);
    }

    /**
     * Saves a single transactionRecord to transactionHistory.
     *
     * @param transaction
     */
    @Override
    public void saveTransaction(TransactionRecord transaction) {
        transactionHistory.add(transaction);
    }

    @Override
    public String getName() {
        return name;
    }

}
