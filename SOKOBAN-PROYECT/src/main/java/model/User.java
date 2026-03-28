package model;

public class User {

    private String name;
    private String email;
    private String username;
    private String avatar;
    private Difficulty difficulty;

    // Constructor
    public User(String name, String email, String username, String avatar, Difficulty difficulty) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.avatar = avatar;
        this.difficulty = difficulty;
    }


    public void register() {

    }


    public String getUsername() {
        return username;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}