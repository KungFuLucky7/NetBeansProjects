package serverSharedClasses;

import java.io.Serializable;
import serverInterfaces.*;
/**
 * ProductSpec contains necessary information to describe a product. 
 * @author David
 */
public class ProductSpec implements Serializable{
    private String UPC;
    private String description;
    private float price;
    
    public ProductSpec(){
    }
    
    public void setUPC(String UPC){ 
        this.UPC = UPC;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public void setPrice(float price){
        this.price = price;
    }

    public String getUPC() {
        return UPC;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }
}
