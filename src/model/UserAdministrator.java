package model;
import java.util.ArrayList;


public class UserAdministrator{
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
        return newUser;
    }

    public static void lose(){
        currentUser.setLose(currentUser.getLose() + 1);
    }

    public static void win(){
        currentUser.setWin(currentUser.getWin() + 1);
    }

    public static void logout(){
        currentUser = null;
    }

    public static User getCurrentUser(){
        return currentUser;
    }
}