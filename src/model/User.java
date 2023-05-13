package model;

public class User {
    private String username;
    private String password;
    private int score;
    private int win;
    private int lose;

    public User(String username, String password, int score, int win, int lose) {
        this.username = username;
        this.password = password;
        this.score = score;
        this.win = win;
        this.lose = lose;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d,%d", username, password, score, win, lose);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }
    
}
