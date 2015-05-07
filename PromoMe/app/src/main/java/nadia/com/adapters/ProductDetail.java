package nadia.com.adapters;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * Created by ngarcia on 5/5/15.
 */
public class ProductDetail {
    private String proveedor;
    private String marca;
    private String nombre;
    private Caracteristicas[] caracteristicas;

    public ProductDetail(Object detalles) {
        LinkedTreeMap<String, Object> mapa_detalles = (LinkedTreeMap<String, Object>) detalles;

        LinkedTreeMap<String, Object> characteristics = (LinkedTreeMap<String, Object>) mapa_detalles.get("characteristics");
        ArrayList<LinkedTreeMap<LinkedTreeMap, String>> entry = (ArrayList<LinkedTreeMap<LinkedTreeMap, String>>) characteristics.get("entry");


        caracteristicas = new Caracteristicas[entry.size()];

        for (int i = 0; i < entry.size(); i++) {
            LinkedTreeMap<LinkedTreeMap, String> keyEntry = entry.get(i);
            String key = keyEntry.get("key");
            String value = keyEntry.get("value");
            caracteristicas[i] = new Caracteristicas(key,value);
        }
        marca = (String) mapa_detalles.get("brand");
        nombre = (String) mapa_detalles.get("productName");
        proveedor = (String) mapa_detalles.get("businessName");


    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Caracteristicas[] getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(Caracteristicas[] caracteristicas) {
        this.caracteristicas = caracteristicas;
    }


}
