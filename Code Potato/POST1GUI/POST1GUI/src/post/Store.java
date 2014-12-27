package post;

import java.util.ArrayList;

/**
 * Store contains a productCatalog, a POST, and a transactionRecord history.
 * @author Michael Santer
 */
public class Store {
    private String name;
    private Post post;
    private ProductCatalog productCatalog;
    private ArrayList<TransactionRecord> transactionHistory;
    
    public Store(String name){
        this.name = name;
        productCatalog = new ProductCatalog(); 
        transactionHistory = new ArrayList<TransactionRecord>();
    }
    
    public Post getPost(){
        return post;
    }

    /**
     * Creates a new POST. 
     * @throws Exception if POST has already initialized. 
     */
    public void newPost() throws Exception {
        if (post != null)
            throw new Exception("Post already exists!");
        post = new Post(this);
    }
    
    /**
     * Adds a single product to the product catalog.
     * @param product 
     */
    public void addProductToCatalog(ProductSpec product){
        productCatalog.addProductToCatalog(product);
    }           

    /**
     * Given a upc, checks if the product exists in the productCatalog. 
     * @param upc
     * @return true if product exists, false if not. 
     */
    public boolean hasProduct(String upc) {
        return productCatalog.hasProduct(upc);
    }
    
    /**
     * Returns the product spec for a given upc. 
     * @param upc
     * @return ProductSpec
     */
    public ProductSpec getProduct(String upc){
        return productCatalog.getProduct(upc);
    }

    /**
     * Saves a single transactionRecord to transactionHistory. 
     * @param transaction 
     */
    public void saveTransaction(TransactionRecord transaction) {
        transactionHistory.add(transaction);
    }

    public String getName() {
        return name;
    }
    
}
