package post;

/**
 * Item is used by transactionRecord. It holds a product specification, and
 * the quantity that is being sold.
 * 
 * @author Michael Santer
 */
public class Item {
    private ProductSpec productSpec;
    private int quantity;
    
    public Item(){   
    }

    public void setProductSpec(ProductSpec productSpec) {
        this.productSpec = productSpec;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getUPC(){
        return productSpec.getUPC();
    }
    
    public int getQuantity(){
        return quantity;
    }
    
    public float getPrice(){
        return productSpec.getPrice();
    }
    
    public String getDescription(){
        return productSpec.getDescription();
    }
    

    
}
