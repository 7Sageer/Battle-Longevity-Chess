package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AIModel{
    public String name;
    public double[] playerWeight = new double[9];
    public double[] oppositeWeight = new double[9];
    public double[][] distanceWeight = new double[9][9];
    public double[] dendistanceWeight = new double[9];

    public AIModel(String name){
        this.name = name;
        try {
            this.loadModel();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveModel() throws FileNotFoundException, IOException{     
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("resource\\AImodel\\" + name + ".dat"));
        oos.writeObject(playerWeight);
        oos.writeObject(oppositeWeight);
        oos.writeObject(distanceWeight);
        oos.writeObject(dendistanceWeight);
        oos.close();
    }

    public void loadModel() throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resource\\AImodel\\" + name + ".dat"));
        playerWeight = (double[])ois.readObject();
        oppositeWeight = (double[])ois.readObject();
        distanceWeight = (double[][])ois.readObject();
        dendistanceWeight = (double[])ois.readObject();
        ois.close();
    }

    public AIModel setModel(AIModel model){
        model.playerWeight = new double[]{500, 800, 700, 800, 900, 1000, 1100, 1200, 1300};
        model.oppositeWeight = new double[]{500, 800, 700, 800, 900, 1000, 1100, 1200, 1300};
        model.dendistanceWeight = new double[]{5, -2, -3, 1, 3, 5, 11, 16, 1};
        model.distanceWeight = new double[][] 
        {
            {0, 5, 10, 15, 20, 25, 30, 35, 40},
            {0, 0, 5, 10, 15, 20, 25, 30, 8},
            {0, 1.0, 0, 5, 10, 15, 20, 25, 30},
            {0, 2.0, 1.5, 0, 5, 10, 15, 20, 25},
            {0, 1.5, 2.0, 2.5, 0, 5, 10, 15, 20},
            {0, 2.0, 2.5, 3.0, 3.5, 0, 5, 10, 15},
            {0, 1.0, 2.0, 3.0, 4.0, 4.5, 0, 5, 10},
            {0, 2.0, 2.5, 3.0, 4.0, 5.0, 6.0, 0, 5},
            {0, 0.0, 4.0, 4.5, 5.0, 5.6, 6.8, 7.0, 0}
        };
        
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                model.distanceWeight[i][j] /= 4.2;
            }
        }

            
        return model;
    }

    public void copyModel(AIModel model){
        this.name = model.name;
        this.playerWeight = model.playerWeight;
        this.oppositeWeight = model.oppositeWeight;
        this.distanceWeight = model.distanceWeight;
        this.dendistanceWeight = model.dendistanceWeight;
    }

    private void initialize(){
        for(int i = 0; i < 9; i++){
            playerWeight[i] = 500 + 100 * i;
            oppositeWeight[i] = 500 + 100 * i;
            dendistanceWeight[i] = 5;
            for(int j = 0; j < 9; j++){
                if(i > j){
                    distanceWeight[i][j] =  j;
                }else{
                    distanceWeight[i][j] =  i;
                }
            }
        }
    }

    public static AIModel radientDescent(AIModel modelA, AIModel modelB){
        double learningRate = 0.1;
        for(int i = 0; i < 9; i++){
            modelA.playerWeight[i] += learningRate * (modelB.playerWeight[i] - modelA.playerWeight[i]);
            modelA.oppositeWeight[i] += learningRate * (modelB.oppositeWeight[i] - modelA.oppositeWeight[i]);
            modelA.dendistanceWeight[i] += learningRate * (modelB.dendistanceWeight[i] - modelA.dendistanceWeight[i]);
            for(int j = 0; j < 9; j++){
                modelA.distanceWeight[i][j] += learningRate * (modelB.distanceWeight[i][j] - modelA.distanceWeight[i][j]);
            }
        }
        return modelA;
    }

    @Override
    public String toString(){
        String result = "";
        result +="name: " + name + "\n";
        result += "playerWeight: ";
        for(int i = 1; i < 9; i++){
            result += playerWeight[i] + " ";
        }
        result += "\noppositeWeight: ";
        for(int i = 1; i < 9; i++){
            result += oppositeWeight[i] + " ";
        }
        result += "\ndistanceWeight: ";
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                result += distanceWeight[i][j] + " ";
            }
        }
        result += "\ndendistanceWeight: ";
        for(int i = 1; i < 9; i++){
            result += dendistanceWeight[i] + " ";
        }
        return result;
    }

    public static void main(String[] args) {
        AIModel model = new AIModel("modelB");
        // model.initialize();
        // model = model.setModel(model);
        model.copyModel(new AIModel("basic"));
        try {
            model.saveModel();
            model.loadModel();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(model);
    }


}