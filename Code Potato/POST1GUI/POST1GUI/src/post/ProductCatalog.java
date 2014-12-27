package post;

/**
 * ProductCatalog contains all productSpecs matched with a UPC.
 *
 * @author David
 */
import java.util.HashMap;

public class ProductCatalog {

    //a map of UPC's and the matchem product
    //private HashMap<String,ProductSpec> catalog;
    public static HashMap<String, ProductSpec> catalog;

    public ProductCatalog() {
        if (catalog == null) {
            catalog = new HashMap<>();
        }
    }

    public void addProductToCatalog(ProductSpec product) {
        catalog.put(product.getUPC(), product);
    }

    public boolean hasProduct(String upc) {
        return catalog.containsKey(upc);
    }

    public static ProductSpec getProduct(String upc) {
        return catalog.get(upc);
    }

}
