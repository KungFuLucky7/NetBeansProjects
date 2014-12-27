package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverInterfaces.*;

/**
 *
 * @author Michael Santer
 *
 * The Manager class is responsible for opening the store, initializing all
 * POSTs, and loading the Product Catalog.
 *
 */
public class ManagerImpl extends UnicastRemoteObject implements Manager {

    private StoreImpl store;

    private ManagerImpl() throws RemoteException {
    }

    /**
     * Instantiates store with a store name
     *
     * @param storeName
     * @throws Exception if store has already been instantiated
     */
    public void openStore(String storeName) throws Exception {
        if (store != null) {
            throw new Exception("Store is already open!");
        }
        store = new StoreImpl();
    }

    /**
     * Given a filename, this method sets up the product catalog of the store.
     *
     * @param productCatalogFile
     * @throws IOException if productCatalog has already been set up
     */
    public void setupProductCatalog(String productCatalogFile) throws IOException {
        ProductReader reader = new ProductReader(productCatalogFile);
        reader.init();
        while (reader.hasMoreProducts()) {
            store.addProductToCatalog(reader.getNextProduct());
        }
        System.out.println("A client has set up a product catalog.");
    }

    private Store getStore() {
        return this.store;
    }

    /**
     * Initialize a new POST in store.
     *
     * @throws Exception if store is open, or if POST is already initialized.
     */
//    public void initPost() throws Exception {
//        if (store == null) {
//            throw new Exception("Store is not open!");
//        }
//        store.newPost();
//    }
    /**
     * Retrieves POST from store.
     *
     * @return
     * @throws Exception if store is not open.
     */
//    public Post getPost() throws Exception {
//        if (store == null) {
//            throw new Exception("Store is not open!");
//        }
//        return store.getPost();
//    }
    /**
     * Instantiate manager, instantiate UI, run UI.
     *
     * @param args
     */
    public static void main(String args[]) {
//        if (System.getSecurityManager() == null){
//            System.setSecurityManager(new SecurityManager());
//        }
        try {
            Manager manager = new ManagerImpl();
            try {
                manager.openStore("Default Name");
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("manager", manager);
            registry.rebind("store", ((ManagerImpl) manager).getStore());
            System.out.println("Manager and store instantiated.");

        } catch (RemoteException ex) {
            Logger.getLogger(ManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
