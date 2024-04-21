/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


//lire le contenu de corpus et stocker dans arraylist de chaque class qui represnter sa corpus
public class FichierVersListeInstance {

    public static HashMap<String, Double> attributesMapping;
    public static HashMap<String, String> attributesT;
    public static HashMap<String, Double> bankMapping;

    public static ArrayList<Instance> file2Instance(String filename) {
        attributesMapping = new HashMap<>();
        attributesT = new HashMap<>();
        ArrayList<Instance> instances = new ArrayList<Instance>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String temp;
            String[] att = new String[4];
            boolean reachData = false;
            String label;
            while (scanner.hasNextLine()) {
                temp = scanner.nextLine();
                if (temp.equalsIgnoreCase("@DATA")) {
                    reachData = true;
                    continue;
                }
                if (temp.startsWith("@attribute")) {
                    String attribute = temp.substring(temp.indexOf("{") + 1, temp.lastIndexOf("}")).replaceAll(",\\s+", " ");
                    String attributes[] = attribute.split(" ");
                    for (int i = 0; i < attributes.length; i++) {
                        StringBuffer test = new StringBuffer("");
                        for (int j = 0; j < attributes.length; j++) {
                            if (i == j) {
                                test.append("1");

                            } else {
                                test.append("0");
                            }
                        }

                        attributesT.put(attributes[i], test.toString());
                        attributesMapping.put(attributes[i], i * 1.0);
                    }
                }
                if (reachData && !temp.startsWith("%") && !temp.isEmpty()) {
                    att = getAttributesFromString(temp);
                    label = temp.split(",")[4];
                    instances.add(new Instance(att, label));
                }
            }
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return instances;
    }

    public static ArrayList<BankInstance> file2BankInstance(String filename) {
        bankMapping = new HashMap<>();
        ArrayList<BankInstance> instances = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String temp;
            String[] att;
            boolean reachData = false;
            String label;
            double age, income;
            while (scanner.hasNextLine()) {
                temp = scanner.nextLine();
                if (temp.equalsIgnoreCase("@DATA")) {
                    reachData = true;
                    continue;
                }
                if (temp.startsWith("@attribute") && !temp.contains("numeric")) {
                    
                        String attribute = temp.substring(temp.indexOf("{") + 1, temp.lastIndexOf("}")).replaceAll(",", " ").replaceAll("\\s+", " ");
                        String attributes[] = attribute.split(" ");
                        for (int i = 0; i < attributes.length; i++) {
                            bankMapping.put(attributes[i], 1.0 * (i));
                        }
                    
                }
                if (reachData && !temp.startsWith("%") && !temp.isEmpty()) {
                    age = Double.parseDouble(temp.split(",")[0]);
                    income = Double.parseDouble(temp.split(",")[3]);
                    att = getAttributesFromString(temp);
                    label = temp.split(",")[temp.split(",").length - 1];
                    instances.add(new BankInstance(income, age, att, label));
                }
            }
            scanner.close();

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return instances;
    }

    /*
        cette fonction prend une chaine des attribut separer avec ,
        => retourne un tableau des double contenant ces attribus
     */
    private static String[] getAttributesFromString(String temp) {
        temp = temp.replaceAll("[0-9]+\\.?[0-9]+,", "");
        temp = temp.substring(0, temp.lastIndexOf(","));
        return temp.split(",");
    }

}
