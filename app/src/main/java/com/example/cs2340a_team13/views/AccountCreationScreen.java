package com.example.cs2340a_team13.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.cs2340a_team13.R;

public class AccountCreationScreen extends AppCompatActivity {
    private AccountCreationViewModel viewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation_screen);
    }



}