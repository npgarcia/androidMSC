package nadia.com.adapters;

import java.util.Map;
import java.util.UUID;

/**
 * Created by ngarcia on 5/6/15.
 */
public class Producto {
    private UUID productId;
    private String unspc;
    private String businessName;
    private String brand;
    private String productName;
    private Map<String,String> characteristics;

    public Producto(){

    }

    public UUID getProductId() {
        return productId;
    }
    public void setProductId(UUID productId) {
        this.productId = productId;
    }
    public String getUnspc() {
        return unspc;
    }
    public void setUnspc(String unspc) {
        this.unspc = unspc;
    }
    public String getBusinessName() {
        return businessName;
    }
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Map<String,String> getCharacteristics() {
        return characteristics;
    }
    public void setCharacteristics(Map<String,String> characteristics) {
        this.characteristics = characteristics;
    }


}