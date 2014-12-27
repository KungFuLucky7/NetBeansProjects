package post;

import serverSharedClasses.ProductSpec;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * ClientProductReader reads a list of products from a file, and constructs the
 * appropriate ProductSpec objects.
 *
 * @author David
 */
public class ClientProductReader {

    private FileReader in;
    private BufferedReader reader;

    private String productsFile;
    private String parsedSpec;

    public ClientProductReader(String productCatalogFile) {
        productsFile = productCatalogFile;
    }

    /**
     * Initialize reader.
     *
     * @throws IOException if file cannot be opened.
     */
    public void init() throws IOException {
        in = new FileReader(productsFile);
        reader = new BufferedReader(in);
    }

    /**
     * Checks if the end of the file has been reached.
     *
     * @return false if eof is reached, true otherwise.
     * @throws IOException
     */
    public boolean hasMoreProducts() throws IOException {
        if ((parsedSpec = reader.readLine()) == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Reads the next line in the file, and builds a single ProductSpec object
     * from the line.
     *
     * @return ProductSpec
     */
    public ProductSpec getNextProduct() {
        //read next line in file
        //then create a ProductSpec instance and return it
        //listOfItems.add(new Item(productSpec));]
        StringTokenizer tok = new StringTokenizer(parsedSpec);
        //String UPC = parsedSpec.substring(0,3);
        String UPC = tok.nextToken();
        //String description = parsedSpec.substring(9, 28);
        String description = tok.nextToken();
        //String sPrice = parsedSpec.substring(34);
        String sPrice = tok.nextToken();
        float price = Float.parseFloat(sPrice);

        ProductSpec nextProduct = new ProductSpec();

        nextProduct.setUPC(UPC);
        nextProduct.setDescription(description);
        nextProduct.setPrice(price);

        return nextProduct;
    }

}
