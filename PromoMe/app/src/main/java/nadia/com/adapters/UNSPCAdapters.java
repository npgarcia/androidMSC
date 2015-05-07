package nadia.com.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * Created by ngarcia on 5/3/15.
 */
public class UNSPCAdapters {

    public static ArrayAdapter<String> getSegmentDescriptions(ArrayList unspcSegments, Context context){
        ArrayList<String> descripciones = new ArrayList<>();
        descripciones.add("[Selecciona un segmento]");

        for(Object segment : unspcSegments){
            LinkedTreeMap<String,String> seg = (LinkedTreeMap<String, String>) segment;
            descripciones.add(seg.get("descripcion"));
        }

        return new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,descripciones);
    }

    public static String getSelectedSegmentId(ArrayList unspcSegments, int posicion){

        Object segment = unspcSegments.get(posicion-1);
        LinkedTreeMap<String, String> segmentMap = (LinkedTreeMap<String, String>)segment;

        return segmentMap.get("idSegmento");


    }

    public static ArrayAdapter<String> getFamiliesDescriptions(ArrayList unspcFamilies, Context context) {
        ArrayList<String> descripciones = new ArrayList<>();
        descripciones.add("[Selecciona una familia]");

        for(Object segment : unspcFamilies){
            LinkedTreeMap<String,String> seg = (LinkedTreeMap<String, String>) segment;
            descripciones.add(seg.get("descripcion"));
        }

        return new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,descripciones);
    }

    public static String getSelectedFamilyId(ArrayList unspcFamilies, int posicion){

        Object family = unspcFamilies.get(posicion-1);
        LinkedTreeMap<String, String> familyMap = (LinkedTreeMap<String, String>)family;

        return familyMap.get("idFamilia");


    }

    public static ArrayAdapter<String> getClassesDescriptions(ArrayList unspcClasses, Context context) {

        ArrayList<String> descripciones = new ArrayList<>();
        descripciones.add("[Selecciona una clase]");

        for(Object segment : unspcClasses){
            LinkedTreeMap<String,String> seg = (LinkedTreeMap<String, String>) segment;
            descripciones.add(seg.get("descripcion"));
        }

        return new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,descripciones);

    }

    public static String getSelectedClassId(ArrayList unspcFamilies, int posicion){

        Object family = unspcFamilies.get(posicion-1);
        LinkedTreeMap<String, String> familyMap = (LinkedTreeMap<String, String>)family;

        return familyMap.get("idClass");


    }

    public static ArrayAdapter<String>  getProductsDescriptions(ArrayList unspcProducts, Context context) {
        ArrayList<String> descripciones = new ArrayList<>();
        descripciones.add("[Selecciona un producto]");

        for(Object segment : unspcProducts){
            LinkedTreeMap<String,String> seg = (LinkedTreeMap<String, String>) segment;
            descripciones.add(seg.get("descripcion"));
        }

        return new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,descripciones);
    }

    public static String getSelectedProductId(ArrayList unspcProductos, int posicion){

        Object family = unspcProductos.get(posicion-1);
        LinkedTreeMap<String, String> familyMap = (LinkedTreeMap<String, String>)family;

        return familyMap.get("idProduct");


    }

    public static ArrayAdapter<String>  getProductsByUnspc(ArrayList unspcProducts, Context context) {
        ArrayList<String> descripciones = new ArrayList<>();
        descripciones.add("[Selecciona un producto]");

        for(Object segment : unspcProducts){
            LinkedTreeMap<String,String> seg = (LinkedTreeMap<String, String>) segment;
            descripciones.add(seg.get("descripcion"));
        }

        return new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,descripciones);
    }
}
