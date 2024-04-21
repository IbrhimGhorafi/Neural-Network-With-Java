/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.utils;

import chikhi.ghorafi.nn.algorithm.Layer;
import chikhi.ghorafi.nn.algorithm.NeuralNetwork;
import chikhi.ghorafi.nn.algorithm.Neuron;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Utility {

    public static ArrayList<double[]> getIntervalls(ArrayList<BankInstance> data, int numIntervals, String name) {
        double min;
        double max;
        min = getMinimum(data, name);
        max = getMaximum(data, name);
        double increment = (max - min) / numIntervals;
        ArrayList<double[]> intervals = new ArrayList<>();
        double previous = min;
        for (int i = 0; i < numIntervals; i++) {
            double bound[] = new double[2];
            bound[0] = previous;
            bound[1] = previous + increment;
            previous = bound[1];
            intervals.add(bound);
        }

        return intervals;
    }

    private static double getMinimum(ArrayList<BankInstance> data, String name) {
        double min = 0;
        if (name.equals("age")) {
            min = data.get(0).getAge();
            for (BankInstance bankInstance : data) {
                if (bankInstance.getAge() < min) {
                    min = bankInstance.getAge();
                }
            }

        } else if (name.equals("income")) {
            min = data.get(0).getIncome();
            for (BankInstance bankInstance : data) {
                if (bankInstance.getIncome() < min) {
                    min = bankInstance.getIncome();
                }
            }
        }
        return min;
    }

    private static double getMaximum(ArrayList<BankInstance> data, String name) {
        double max = 0;
        if (name.equals("age")) {
            max = data.get(0).getAge();
            for (BankInstance bankInstance : data) {
                if (bankInstance.getAge() > max) {
                    max = bankInstance.getAge();
                }
            }

        } else if (name.equals("income")) {
            max = data.get(0).getIncome();
            for (BankInstance bankInstance : data) {
                if (bankInstance.getIncome() > max) {
                    max = bankInstance.getIncome();
                }
            }
        }
        return max;
    }
    public static ArrayList<String> getAttributes(String fileName){
        ArrayList<String> attributes = new ArrayList<>();
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file) ;
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                if(line.startsWith("@attribute")){
                    attributes.add(line.split(" ")[1]);
                }else if(line.startsWith("@data")){
                    break;
                }
                
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
        
        return attributes;
    }
     public static String ShowNeuralStats(NeuralNetwork network){
        StringBuilder result = new StringBuilder("");
        Layer [] layers = network.getLayers();
        for(int i=1;i<layers.length;i++){
            result.append("couche: ").append(i).append("\n");
            Neuron [] neurons  = layers[i].getNeurons();
            for(int j=0;j<neurons.length;j++){
                result.append(" neurone: ").append(j).append("\n");
                double[] weights = neurons[j].getWeights();
                for(int k=0; k<weights.length;k++){
                    result.append("  W[").append(j).append("]").append("[").append(k).append("] = ").append(weights[k])
                            .append("\n");
                    
                }
                result.append("  bias: ").append(neurons[j].getBias()).append("\n");
                
            }
        }
        
        return result.toString();
    }





}
