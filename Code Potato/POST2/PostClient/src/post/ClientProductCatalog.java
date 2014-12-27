package post;

/**
 * ProductCatalog contains all productSpecs matched with a UPC.
 *
 * @author David
 */
import java.util.HashMap;
import java.util.Set;
import serverSharedClasses.ProductSpec;

public class ClientProductCatalog {

    //a map of UPC's and the matchem product
    private static HashMap<String, ProductSpec> catalog;

    public ClientProductCatalog() {
        catalog = new HashMap<>();
    }

    public void addProductToCatalog(ProductSpec product) {
        catalog.put(product.getUPC(), product);
    }

    public boolean hasProduct(String upc) {
        return catalog.containsKey(upc);
    }

    public ProductSpec getProduct(String upc) {
        return catalog.get(upc);
    }

    public Set<String> getUPCs() {
        return catalog.keySet();
    }

    public HashMap<String, ProductSpec> getAllProducts() {
        return catalog;
    }

}
