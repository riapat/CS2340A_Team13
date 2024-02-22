package com.example.cs2340a_team13.viewModels;

import com.example.cs2340a_team13.model.User;

public class LoginViewModel {
    private User user;
    private static LoginViewModel instance;

    public LoginViewModel() {
        user = new User();

    }

    public static synchronized LoginViewModel getInstance() {
        if (instance == null) {
            instance = new LoginViewModel();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateUser(String username, String password) {
        this.user.setUsername(username);
        this.user.setPassword(password);
    }

    public void createUser(String username, String password) {
        user = new User(username, password);
    }
}
