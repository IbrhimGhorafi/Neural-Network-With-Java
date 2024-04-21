package chikhi.ghorafi.nn.Interafce;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class NeuralNetworkVisualization extends JFrame {

    private ArrayList<ArrayList<String>> neuronLayers;

    public NeuralNetworkVisualization(int numLayers, int[] numNeuronsPerLayer) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Neural Network Visualization");
        setSize(600, 600);
        getContentPane().setBackground(Color.WHITE);
        neuronLayers = new ArrayList<>();

        

        for (int i = 0; i < numLayers; i++) {
            ArrayList<String> layer = new ArrayList<>();
            int numNeurons = numNeuronsPerLayer[i];

            for (int j = 0; j < numNeurons; j++) {        
                String neuronName = "";
                layer.add(neuronName);
            }

            neuronLayers.add(layer);
        }

        setVisible(true);
    }

    
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int neuronRadius = 20;
        int layerSpacing = 100;
        int neuronSpacing = 50;

        int numLayers = neuronLayers.size();
        int maxNeurons = 0;
        for (ArrayList<String> layer : neuronLayers) {
            int numNeurons = layer.size();
            if (numNeurons > maxNeurons)
                maxNeurons = numNeurons;
        }

        int startX = 50;
        int startY = (getHeight() - maxNeurons * neuronSpacing) / 2;

        for (int layerIndex = 0; layerIndex < numLayers; layerIndex++) {
            ArrayList<String> layer = neuronLayers.get(layerIndex);

            int numNeurons = layer.size();
            int layerHeight = numNeurons * neuronSpacing;

            int layerX = startX + layerIndex * layerSpacing;
            int layerY = startY + (maxNeurons * neuronSpacing - layerHeight) / 2;

            for (int neuronIndex = 0; neuronIndex < numNeurons; neuronIndex++) {
                String neuronName = layer.get(neuronIndex);

                int neuronX = layerX;
                int neuronY = layerY + neuronIndex * neuronSpacing;

                g.setColor(Color.DARK_GRAY);
                g.fillOval(neuronX - neuronRadius, neuronY - neuronRadius, 2 * neuronRadius, 2 * neuronRadius);           
                
            }
        }
    }

}
