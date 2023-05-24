package model;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;


public class UserAdministrator implements Serializable {
    private static ArrayList<User> users = new ArrayList<User>();
    private static User currentUser = null;

    public static User login(String username, String password){
        for (User user : users) {
            if(user.getUsername().equals(username)){
                if(user.getPassword().equals(password)){
                    System.out.println("Login success");
                    currentUser = user;
                    return user;
                }
                else{
                    System.out.println("Wrong password");
                    return null;
                }
            }
        }
        System.out.println("User not found, creating new user");
        User newUser = new User(username, password, 0, 0, 0);
        users.add(newUser);
        currentUser = newUser;
        saveData();
        return newUser;
    }

    public static void logout(){
        currentUser = null;
    }

    public static ArrayList<User> rank(){
        ArrayList<User> rank = new ArrayList<User>();
        for (User user : users) {
            rank.add(user);
        }
        rank.sort((a, b) -> (b.getWin() - b.getLose()) - (a.getWin() - a.getLose()));
        return rank;
    }

    public static void saveData(){
        //save in a txt file          
        Path path = Paths.get("users.txt");
        File file = path.toFile();
        try{
            if(!file.exists()){
                file.createNewFile();
            }
        FileWriter writer = new FileWriter(file);
        for (User user : users) {
            writer.write(user.toString() + "\n");
        }
        writer.close();

        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }
                
    public static void loadData(){
        //load from a txt file
        Path path = Paths.get("users.txt");
        File file = path.toFile();
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                int win = Integer.parseInt(data[3]);
                int lose = Integer.parseInt(data[4]);
                int score = Integer.parseInt(data[2]);
                User user = new User(username, password, score, win, lose);
                for(int i = 0; i < users.size(); i++){
                    if(users.get(i).getUsername().equals(username)){
                        users.remove(i);
                    }
                }
                users.add(user);
            }
            scanner.close();

        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void lose(){
        currentUser.setLose(currentUser.getLose() + 1);
        saveData();
    }

    public static void win(){
        currentUser.setWin(currentUser.getWin() + 1);
        saveData();
    }

    public static User getCurrentUser(){
        if(currentUser == null){
            System.out.println("No user logged in");
            return null;
        }
        return currentUser;
    }
}