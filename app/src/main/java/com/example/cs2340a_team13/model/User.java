package com.example.cs2340a_team13.model;

public class User {
    private String username;
    private String password;

    private int height;

    private int weight;

    private int age;

    private String gender;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.height = 0;
        this.weight = 0;
        this.age = 0;
        this.gender = "";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.age = user.getAge();
        this.gender = user.getGender();
    }
}
