/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chikhi.ghorafi.nn.utils;

import chikhi.ghorafi.nn.algorithm.NeuralNetwork;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Functions {

    public static ArrayList<Double> instancesCorrect = new ArrayList<>();
    public static ArrayList<Instance> testInstances = new ArrayList<>();
    public static ArrayList<BankInstance> testBanking = new ArrayList<>();
    //intialise les poid a la premier fois aleatoire
    public static double[] initWeights(int size) {
        double[] weights = new double[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            weights[i] = random.nextDouble() - 0.5;
        }
        return weights;
    }
    //intialise les bias a la premier fois aleatoire
    public static double initBias() {
        Random random = new Random();
        return random.nextDouble() - 0.5;
    }
    //la function d'activation sigmoid
    public static double sigmoid(double x) {
        return 1d / (Math.exp(-x) + 1);
    }
    //calcuer la diffrence de deux vecetura
    public static double[] differnceVecteur(double[] vect1, double[] vect2) {
        double res[] = new double[vect1.length];
        for (int i = 0; i < vect1.length; i++) {
            res[i] = -1 * (vect1[i] - vect2[i]);
        }
        return res;
    }

   //le derivative de function d'activation
    public static double sigmoidDerivative(double x) {
        return x * (1 - x);
    }
    //represnter le fonction de cout
    public static double[] QuadraticError(double[] excpected, double[] prediced) {
        double[] result = Functions.differnceVecteur(excpected, prediced);
        for (int i = 0; i < result.length; i++) {
            result[i] = 1 / 2 * Math.pow(result[i], 2);
        }
        return result;
    }
    //derivative de function de cout
    public static double[] QuadraticErrorDerrivative(double[] excpected, double[] prediced) {
        return Functions.differnceVecteur(excpected, prediced);
    }
    //diviser le dataset par la methode validation croisée de cprous bank
    public static ArrayList<ArrayList<BankInstance>> validationCroise(ArrayList<BankInstance> data, int d) {
        int NbInstancDivisi = data.size() / d;
        int index = 0;
        Collections.shuffle(data);
        instancesCorrect.clear();
        for (BankInstance instance : data) {
            instancesCorrect.add(instance.getEncodedLabel());
        }
        ArrayList<ArrayList<BankInstance>> ensemblCroise = new ArrayList<>();
        for (int i = 0; i < d; i++) {
            ArrayList<BankInstance> subArray = new ArrayList<>();
            for (int j = 0; j < NbInstancDivisi; j++) {
                subArray.add(data.get(index));
                index++;
            }
            ensemblCroise.add(subArray);
        }
        return ensemblCroise;
    }
    //diviser le dataset par la methode validation croisée de cprous weather nominale
    public static ArrayList<ArrayList<Instance>> validationCroiseI(ArrayList<Instance> data, int d) {
        int NbInstancDivisi = data.size() / d;
        int index = 0;
        Collections.shuffle(data);
        System.out.println("data in functions " + data.get(0).toString());
        instancesCorrect.clear();
        for (Instance instance : data) {
            instancesCorrect.add(instance.getEncodedLabel());
        }
        ArrayList<ArrayList<Instance>> ensemblCroise = new ArrayList<>();
        for (int i = 0; i < d; i++) {
            ArrayList<Instance> subArray = new ArrayList<>();
            for (int j = 0; j < NbInstancDivisi; j++) {
                subArray.add(data.get(index));
                index++;
            }
            ensemblCroise.add(subArray);
        }
        return ensemblCroise;
    }
    //entrinement par validation croisée selon le corpus choisi par l'utilisateur
    public static NeuralNetwork crossTraining(NeuralNetwork network, int epochs, double learningRate, ArrayList<BankInstance> bankInstances, ArrayList<Instance> instances, int k) {
        if (bankInstances != null) {
            // training 
            ArrayList<ArrayList<BankInstance>> croiss = Functions.validationCroise(bankInstances, k);
            for (int j = 0; j < croiss.size(); j++) {
                ArrayList<BankInstance> test = croiss.get(j);
                ArrayList<BankInstance> entrainment = new ArrayList<>();
                for (k = 0; k < croiss.size(); k++) {
                    if (j != k) {
                        entrainment.addAll(croiss.get(k));
                    }
                }
                for (BankInstance instance : entrainment) {
                    for (int i = 0; i < epochs; i++) {
                        double[] predicted = network.feedForward(instance.getEncodedAttributes());
                        double[] target = {instance.getEncodedLabel()};
                        network.backward(target, predicted, learningRate);
                    }
                }
                for (BankInstance ignored : test) {
                    double[] predicted = network.feedForward(ignored.getEncodedAttributes());
                }
            }
        } else {
            ArrayList<ArrayList<Instance>> croiss = Functions.validationCroiseI(instances, k);
            for (int j = 0; j < croiss.size(); j++) {
                ArrayList<Instance> test = croiss.get(j);
                ArrayList<Instance> entrainment = new ArrayList<>();
                for (k = 0; k < croiss.size(); k++) {
                    if (j != k) {
                        entrainment.addAll(croiss.get(k));
                    }
                }
                for (Instance instance : entrainment) {
                    for (int i = 0; i < epochs; i++) {
                        double[] predicted = network.feedForward(instance.getEncodedFeatures());
                        double[] target = {instance.getEncodedLabel()};
                        network.backward(target, predicted, learningRate);
                    }
                }
                for (Instance ignored : test) {
                    double[] predicted = network.feedForward(ignored.getEncodedFeatures());
                }
            }
        }
        return network;
    }

    //splite le corpus des weather nominale
    public static ArrayList<ArrayList<Instance>> diviserArrayList(ArrayList<Instance> instances, double pur) {
        double porcentage = pur / 100;
        ArrayList<Instance> entrainement = new ArrayList<Instance>();
        ArrayList<Instance> test = new ArrayList<Instance>();
        int taille = instances.size();
        int tailleEntrainement = (int) (taille * porcentage);
        Collections.shuffle(instances);
        instancesCorrect.clear();
        for (Instance instance : test) {
            instancesCorrect.add(instance.getEncodedLabel());
        }
        // Ajouter les éléments de l'échantillon pour l'entraînement
        for (int i = 0; i < tailleEntrainement; i++) {
            entrainement.add(instances.get(i));
        }
        // Ajouter les éléments restants pour les tests
        for (int i = tailleEntrainement; i < taille; i++) {
            test.add(instances.get(i));
        }
        ArrayList<ArrayList<Instance>> dev = new ArrayList<>();
        dev.add(entrainement);
        dev.add(test);
        return dev;
    }

    //splitle corpus de bank
    public static ArrayList<ArrayList<BankInstance>> diviserArrayListB(ArrayList<BankInstance> instances, double pur) {
        double porcentage = pur / 100;
        ArrayList<BankInstance> entrainement = new ArrayList<BankInstance>();
        ArrayList<BankInstance> test = new ArrayList<BankInstance>();
        int taille = instances.size();
        int tailleEntrainement = (int) (taille * porcentage);
        Collections.shuffle(instances);
        instancesCorrect.clear();
        for (BankInstance instance : instances) {
            instancesCorrect.add(instance.getEncodedLabel());
        }
        // Ajouter les éléments de l'échantillon pour l'entraînement
        for (int i = 0; i < tailleEntrainement; i++) {
            entrainement.add(instances.get(i));
        }
        // Ajouter les éléments restants pour les tests
        for (int i = tailleEntrainement; i < taille; i++) {
            test.add(instances.get(i));
        }
        ArrayList<ArrayList<BankInstance>> dev = new ArrayList<>();
        dev.add(entrainement);
        dev.add(test);
        return dev;
    }
    
    //entrinement par split, selon le corpus choisi par l'utilisateur
    public static NeuralNetwork splitTraining(NeuralNetwork network, int epochs, double learningRate, ArrayList<BankInstance> bankInstances, ArrayList<Instance> instances, int porcentage) {
        if (bankInstances != null) {
            ArrayList<ArrayList<BankInstance>> splitdata = diviserArrayListB(bankInstances, porcentage);
            ArrayList<BankInstance> trainData = splitdata.get(0);
            ArrayList<BankInstance> testData = splitdata.get(1);
            instancesCorrect.clear();
            for (BankInstance bi : testData) {
                instancesCorrect.add(bi.getEncodedLabel());
            }
            testBanking = testData;
            for (BankInstance bank : trainData) {
                for (int i = 0; i < epochs; i++) {
                    double[] predicted = network.feedForward(bank.getEncodedAttributes());
                    double[] target = {bank.getEncodedLabel()};
                    network.backward(target, predicted, learningRate);
                }
            }
        } else {
            ArrayList<ArrayList<Instance>> splitdata = diviserArrayList(instances, porcentage);
            ArrayList<Instance> trainData = splitdata.get(0);
            ArrayList<Instance> testData = splitdata.get(1);
             for (Instance i : testData) {
                instancesCorrect.add(i.getEncodedLabel());
            }
            testInstances = testData;
            for (Instance instanc : trainData) {
                for (int i = 0; i < epochs; i++) {
                    double[] predicted = network.feedForward(instanc.getEncodedFeatures());
                    double[] target = {instanc.getEncodedLabel()};
                    network.backward(target, predicted, learningRate);
                }
            }
        }
        return network;
    }
    //genere matruce confusion
    public static int[][] matriceConfusion(ArrayList<Double> predicted) {
        int[][] matrice = new int[2][2];
        //int [] labels = new int[]{0,1};
        for (int i = 0; i < instancesCorrect.size(); i++) {
            double predit = predicted.get(i) > 0.5 ? 1 : 0;
            double vrai = instancesCorrect.get(i);
            if (vrai == 0) {
                if (vrai == predit) {
                    matrice[0][0]++;
                } else {
                    matrice[0][1]++;
                }
            } else {
                if (vrai == predit) {
                    matrice[1][1]++;
                } else {
                    matrice[1][0]++;
                }
            }
        }
        return matrice;
    }
    //calculer le precision par class
    public static double precisionClass(int[][] matrice, int inedx) {
        return (double) matrice[inedx][inedx] / (matrice[0][inedx] + matrice[1][inedx]);
    }
    //calculer le rapple par class
    public static double rappelClass(int[][] matrice, int inedx) {
        return (double) matrice[inedx][inedx] / (matrice[inedx][0] + matrice[inedx][1]);
    }
        //calculer le f-meseure par class
    public static double fmeseurClass(double rapell, double precision) {
        return (2 * rapell * precision) / (precision + rapell);
    }
        //calculer le rappel globale
    public static double rappelGlobal(ArrayList<Double> rapel) {
        double som = 0;
        for (double rapl : rapel) {
            som += rapl;
        }
        return som / 2;
    }
    //calculer le precesion globale
    public static double precesionGlobal(ArrayList<Double> precesion) {
        double som = 0;
        for (double precesio : precesion) {
            som += precesio;
        }
        return som / 2;
    }
    //calculer exactitude total ou bien accurcy
    public static double calculerExactitude(int[][] confusionMatrix) {
        double fullSize = 0, correct = 0;
        for (int i = 0; i < confusionMatrix.length; i++) {
            for (int j = 0; j < confusionMatrix[i].length; j++) {
                if (i == j) {
                    correct += confusionMatrix[i][j];
                }
                fullSize += confusionMatrix[i][j];
            }
        }
        return correct / fullSize;
    }
}
