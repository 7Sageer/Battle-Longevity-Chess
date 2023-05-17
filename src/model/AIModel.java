package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AIModel{
    public String name;
    public double[] playerWeight = new double[8];
    public double[] oppositeWeight = new double[8];
    public double[][] distanceWeight = new double[8][8];

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
        oos.close();
    }

    public void loadModel() throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("resource\\AImodel\\" + name + ".dat"));
        playerWeight = (double[])ois.readObject();
        oppositeWeight = (double[])ois.readObject();
        distanceWeight = (double[][])ois.readObject();
        ois.close();
    }

    private void initialize(){
        for(int i = 0; i < 8; i++){
            playerWeight[i] = 10;
            oppositeWeight[i] = 100;
            for(int j = 0; j < 8; j++){
                distanceWeight[i][j] = 10;
            }
        }
    }

    public static void main(String[] args) {
        AIModel model = new AIModel("modelB");
        model.initialize();
        try {
            model.saveModel();
            model.loadModel();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}