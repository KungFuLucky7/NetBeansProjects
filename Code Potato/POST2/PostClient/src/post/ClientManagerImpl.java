package post;

import java.io.IOException;
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
public class ClientManagerImpl implements Manager {

    private ClientStoreImpl store;

    public ClientManagerImpl() {
    }

    /**
     * Instantiates store with a store name
     *
     * @param storeName
     * @throws Exception if store has already been instantiated
     */
    @Override
    public void openStore(String storeName) throws Exception {
        if (store != null) {
            throw new Exception("Store is already open!");
        }
        store = new ClientStoreImpl();
    }

    /**
     * Given a filename, this method sets up the product catalog of the store.
     *
     * @param productCatalogFile
     * @throws IOException if productCatalog has already been set up
     */
    @Override
    public void setupProductCatalog(String productCatalogFile) throws IOException {
        ClientProductReader reader = new ClientProductReader(productCatalogFile);
        reader.init();
        while (reader.hasMoreProducts()) {
            store.addProductToCatalog(reader.getNextProduct());
        }
        System.out.println("A client has set up a product catalog.");
    }

    public Store getStore() {
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
    /* public static void main(String args[]) {
     //        if (System.getSecurityManager() == null){
     //            System.setSecurityManager(new SecurityManager());
     //        }
     try {
     Manager manager = new ClientManagerImpl();
     try {
     manager.openStore("Default Name");
     } catch (Exception ex) {
     System.out.println(ex.getMessage());
     }
     Registry registry = LocateRegistry.createRegistry(1099);
     registry.rebind("manager", manager);
     registry.rebind("store", ((ClientManagerImpl) manager).getStore());
     System.out.println("Manager and store instantiated.");

     } catch (RemoteException ex) {
     Logger.getLogger(ClientManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
     }
     }*/
}
