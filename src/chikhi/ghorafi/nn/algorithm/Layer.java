/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.algorithm;

import java.util.Arrays;


//representer les couches d'un reseaux neuron 
public class Layer {
    //chaque couche contient un tableau des nuron
    private Neuron[] neurons;
    //représenter le nombre des nuron pour la couche
    private final int  LAYER_SIZE;

    public Layer(int size) {
        neurons = new Neuron[size];
        LAYER_SIZE=size;
    }
    //initialiser les neurones de la couche
    Layer(int size, int weightSize, boolean isInput) {
        LAYER_SIZE = size;
        neurons = new Neuron[size];
        for(int i=0;i<size;i++){
            neurons[i] = new Neuron(isInput,weightSize);
        }
    }
    
    

    public Neuron[] getNeurons() {
        return neurons;
    }

    public void setNeurons(Neuron[] neurons) {
        this.neurons = neurons;
    }

    public int getLAYER_SIZE() {
        return LAYER_SIZE;
    }

    @Override
    public String toString() {
        return "Layer{" + "neurons=" + Arrays.toString(neurons) + ", LAYER_SIZE=" + LAYER_SIZE + '}';
    }
    
    
    
    
}
