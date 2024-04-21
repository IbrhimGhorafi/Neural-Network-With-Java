/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;


//represnter la corpus des weather nominal avec ses attribue
public class Instance {

    private String[] nominalFeatures;
    private String nominalLabel;
    private double[] encodedFeatures;

    private double encodedLabel;

    public Instance(String[] nominalFeatures, String nominalLabel) {
        this.nominalFeatures = nominalFeatures;
        this.nominalLabel = nominalLabel;

    }

    
    public void encode(HashMap<String,Double> encoder){
        this.encodedFeatures = new double[nominalFeatures.length];
        for(int i=0;i<this.encodedFeatures.length;i++){
            this.encodedFeatures[i]=encoder.get(nominalFeatures[i]);
        }
        encodedLabel = encoder.get(nominalLabel);
        
    }
     /*
    public void encode(HashMap<String, String> encoder) {
        this.encodedFeatures = new double[encoder.size() - 2];
        int k = 0;
        for (int i = 0; i < this.nominalFeatures.length; i++) {
            String current = encoder.get(nominalFeatures[i]);
            String[] array = current.split("");
            for (int j = 0; j < array.length; j++) {
                double num = Double.parseDouble(array[j]);
                encodedFeatures[k]=num;
                k++;
            }
            //this.encodedFeatures[i]=encoder.get(nominalFeatures[i]);
        }
       String []lables = encoder.get(nominalLabel).split("");
       this.encodedLabel = new double[lables.length];
       for(int i=0;i<lables.length;i++){
           encodedLabel[i]=Double.parseDouble(lables[i]);
       }

    }
    */
    public String[] getNominalFeatures() {
        return nominalFeatures;
    }

    public void setNominalFeatures(String[] nominalFeatures) {
        this.nominalFeatures = nominalFeatures;
    }

    public String getNominalLabel() {
        return nominalLabel;
    }

    public void setNominalLabel(String nominalLabel) {
        this.nominalLabel = nominalLabel;
    }

    public double[] getEncodedFeatures() {
        return encodedFeatures;
    }

    public double getEncodedLabel() {
        return encodedLabel;
    }

    public void setEncodedLabel(double encodedLabel) {
        this.encodedLabel = encodedLabel;
    }

    

    public void setEncodedFeatures(double[] encodedFeatures) {
        this.encodedFeatures = encodedFeatures;
    }

    @Override
    public String toString() {
        return "Instance{" + "nominalFeatures=" + Arrays.toString(nominalFeatures) + ", nominalLabel=" + nominalLabel + ", encodedFeatures=" + Arrays.toString(encodedFeatures) + ", encodedLabel=" +encodedLabel + '}';
    }

}
