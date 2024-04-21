/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.algorithm;

import chikhi.ghorafi.nn.utils.Functions;
import java.util.Arrays;


//représenter une réseaux neuronaux
public class NeuralNetwork {
    //une reseau neuronal contient plusieur couche
    private Layer[] layers;
    //représenter les nombres des couche pour la réseau
    private int[] sizes;
    //initialisation du réseau neuronal
    public NeuralNetwork(int[] sizes) {
        this.sizes = sizes;
        layers = new Layer[this.sizes.length];
        // Ajouter le premier coche
        // La couche d'entrée ne contiendra pas les poids.
        Layer inputLayer = new Layer(this.sizes[0], 0, true);
        layers[0] = inputLayer;

        for (int i = 1; i < this.sizes.length; i++) {
            int weightLength = this.sizes[i - 1];
            Layer l = new Layer(this.sizes[i], weightLength, false);
            layers[i] = l;
        }

    }
    //calculer la propagation en avant
    public double[] feedForward(double[] input) {
        // Initialiser la couche d'entrée.
        Neuron[] inputNeurons = layers[0].getNeurons();
        for (int i = 0; i < inputNeurons.length; i++) {
            inputNeurons[i].setOutput(input[i]);
        }

        // feeding data over the network
        for (int layer = 1; layer < layers.length; layer++) {
            Neuron[] currentNeurons = layers[layer].getNeurons();
            Neuron[] prevNeurons = layers[layer - 1].getNeurons();
            for (int neuron = 0; neuron < currentNeurons.length; neuron++) {
                double somme = 0;
                for (int previous = 0; previous < prevNeurons.length; previous++) {
                    somme += (prevNeurons[previous].getOutput() * currentNeurons[neuron].getWeights()[previous]);
                }
                somme += currentNeurons[neuron].getBias();
                currentNeurons[neuron].setOutput(Functions.sigmoid(somme));
            }
        }
        double predicted[] = new double[layers[layers.length-1].getLAYER_SIZE()];
        Neuron[] outputNeurons = layers[layers.length-1].getNeurons();
        int index=0;
        for(Neuron n:outputNeurons){
            predicted[index] = n.getOutput();
        }
        return predicted;
    }
    //Calculer la rétropropagation et représenter ses fonctionnalités
    public void backward(double [] expected,double [] predicted , double learningRate){
        // calcule error pour la couche de sortie
        Layer outputLayer = layers[layers.length-1];
        Neuron[] outputNeurons = outputLayer.getNeurons();
        Layer hiddenLayer = layers[layers.length-2];
        Neuron[] hiddenNeurons = hiddenLayer.getNeurons();
        double error[][] = new double[outputNeurons.length][hiddenNeurons.length];
        for(int output=0 ; output<outputNeurons.length;output++){
            double delta = Functions.sigmoidDerivative(outputNeurons[output].getOutput()) * Functions.QuadraticErrorDerrivative(expected, predicted)[output];            
            for(int previous=0;previous < hiddenNeurons.length;previous++){
                error[output][previous] = delta*hiddenNeurons[previous].getOutput();
            }
            //ajouter error de  neuro de sortie pour la couche de sortie
            outputNeurons[output].setError(error[output]);
        }
        
        // calculate error pour les coches caches 
        for(int layer=layers.length-2;layer>=1;layer--){
            Neuron[] currentNeurons = layers[layer].getNeurons();
            Neuron[] nextNeurons = layers[layer+1].getNeurons();
            Neuron[] prevNeurons = layers[layer-1].getNeurons();
            error = new double[currentNeurons.length][prevNeurons.length];
            for(int current=0;current<currentNeurons.length;current++){
                double delta=Functions.sigmoidDerivative(currentNeurons[current].getOutput());
                double somme=0;
                for(int next=0;next<nextNeurons.length;next++){
                   
                    somme+=nextNeurons[next].getWeights()[current]*Functions.QuadraticErrorDerrivative(expected, predicted)[0] * Functions.sigmoidDerivative(nextNeurons[next].getOutput());
                }

                delta = delta * somme;
                for(int previous=0;previous<prevNeurons.length;previous++){
                    error[current][previous] = delta*prevNeurons[previous].getOutput();
                }
                // set the error for te current hidden layer
                currentNeurons[current].setError(error[current]);
            }
            
        }
        for(int layer=1;layer<layers.length;layer++){
            Neuron[] neurons = layers[layer].getNeurons();
            for(Neuron neuron:neurons){
                neuron.updateWeights(learningRate);
            }
        }
     
    }

    public Layer[] getLayers() {
        return layers;
    }

    public void setLayers(Layer[] layers) {
        this.layers = layers;
    }

    public int[] getSizes() {
        return sizes;
    }

    public void setSizes(int[] sizes) {
        this.sizes = sizes;
    }

    @Override
    public String toString() {
        return "NeuralNetwork{" + "layers=" + Arrays.toString(layers) + ", sizes=" + Arrays.toString(sizes) + '}';
    }

}
