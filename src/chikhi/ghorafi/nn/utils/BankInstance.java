/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


//cette class pour coder les attribue de corpus de bank
public class BankInstance {

    private double income;
    private double age;
    private String[] attributes;
    private String label;
    private double encodedAttributes[];
    private double encodedLabel;

    public BankInstance(double income, double age, String[] attributes, String label) {
        this.income = income;
        this.age = age;
        this.attributes = attributes;
        this.label = label;
    }

    public void encode(HashMap<String, Double> encoder, ArrayList<double[]> ageInterval, ArrayList<double[]> incomeInterval) {
        encodedAttributes = new double[attributes.length+2];
        // codifing the age;
        for (int i=0; i< ageInterval.size();i++) {
            if (this.getAge() >= ageInterval.get(i)[0] && this.getAge() <= ageInterval.get(i)[1]) {
                encodedAttributes[0] = i;
                break;
            }
        }
        //encodedAttributes[0] = k;
        // codifing the income;
        for (int i=0; i< incomeInterval.size();i++) {
            if (this.getIncome()>= incomeInterval.get(i)[0] && this.getIncome()<= incomeInterval.get(i)[1]) {
                encodedAttributes[3] = i;
                break;
            }
        }
        //encodedAttributes[3] = k;
        int k = 1;
        for (int i = 1; i < attributes.length; i++) {
            if (k == 3) {
                k++;
            }
            encodedAttributes[i] = encoder.get(attributes[i]);
            k++;
        }
        encodedLabel = encoder.get(label);
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double[] getEncodedAttributes() {
        return encodedAttributes;
    }

    public void setEncodedAttributes(double[] encodedAttributes) {
        this.encodedAttributes = encodedAttributes;
    }

    public double getEncodedLabel() {
        return encodedLabel;
    }

    public void setEncodedLabel(double encodedLabel) {
        this.encodedLabel = encodedLabel;
    }

    @Override
    public String toString() {
        return "BankInstance{" + "income=" + income + ", age=" + age + ", attributes=" + Arrays.toString(attributes) + ", label=" + label + ", encodedAttributes=" + Arrays.toString(encodedAttributes) + ", encodedLabel=" + encodedLabel + '}';
    }

}
