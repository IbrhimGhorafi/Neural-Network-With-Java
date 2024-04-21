/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.algorithm;

import chikhi.ghorafi.nn.utils.Functions;
import java.util.Arrays;

//cette class representer un neuron avec ces caracterstique
public class Neuron {
    //represent output de neuron actaul et input de neuron suivant
    private double output;
    //represente le bias de neuron actual
    private double bias;
    //representer les poides de neuron actual
    private double[] weights;
    //representer les errur de chaue neuron
    private double [] error;

    public Neuron() {
    }
    //intialisation les neuron par ses poides sauf les neuron de couche entre
    Neuron(boolean isInput, int weightSize) {
        this.bias = isInput ? 0 :  Functions.initBias();
        this.weights = Functions.initWeights(weightSize);
        this.error = new double[weightSize];
    }
    //modifie les poides des nueron dans le retropropgation
     void updateWeights(double learningRate) {
         for(int i=0;i<weights.length;i++){
             weights[i] = weights[i]-learningRate*error[i];
         }
    }
    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double[] getError() {
        return error;
    }

    public void setError(double[] error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Neuron{" + "output=" + output + ", bias=" + bias + ", weights=" + Arrays.toString(weights) + ", error=" + Arrays.toString(error) + '}';
    }

   
    
    
    
}
