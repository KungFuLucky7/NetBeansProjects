package post;

import java.io.IOException;

/**
 *
 * @author Michael Santer
 *
 * The Manager class is responsible for opening the store, initializing all
 * POSTs, and loading the Product Catalog.
 *
 */
public class Manager {

    private Store store;

    private Manager() {
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
        store = new Store(storeName);
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
    }

    /**
     * Initialize a new POST in store.
     *
     * @throws Exception if store is open, or if POST is already initialized.
     */
    public void initPost() throws Exception {
        if (store == null) {
            throw new Exception("Store is not open!");
        }
        store.newPost();
    }

    /**
     * Retrieves POST from store.
     *
     * @return
     * @throws Exception if store is not open.
     */
    public Post getPost() throws Exception {
        if (store == null) {
            throw new Exception("Store is not open!");
        }
        return store.getPost();
    }

    /**
     * Instantiate manager, instantiate UI, run UI.
     *
     * @param args
     */
    public static void main(String args[]) {
        Manager manager = new Manager();
        PostUI ui = new PostUI(manager);
        ui.run(manager);
    }
}
