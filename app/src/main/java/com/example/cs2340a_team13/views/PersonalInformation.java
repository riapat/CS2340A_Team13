package com.example.cs2340a_team13.views;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs2340a_team13.R;
import com.example.cs2340a_team13.viewModels.LoginViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;

public class PersonalInformation extends AppCompatActivity {
    private EditText createHeightEditText;
    private EditText createWeightEditText;
    private EditText createGenderEditText;

    private EditText createAgeEditText;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info_screen);
        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        createHeightEditText = findViewById(R.id.heightFieldAACS);
        createWeightEditText = findViewById(R.id.weightFieldAACS);
        createGenderEditText = findViewById(R.id.genderFieldAACS);
        createAgeEditText = findViewById(R.id.ageFieldAACS);
        Button createPersonalInfoScreenButton = findViewById(R.id.createPersonalInfoButtonAACS);
        userViewModel = UserViewModel.getInstance();

        btnInputMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle input meal button click (navigate to input meal screen)
                Intent intent = new Intent(PersonalInformation.this, InputMealScreen.class);
                startActivity(intent);
            }
        });
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle recipe button click (navigate to recipe screen)
                Intent intent = new Intent(PersonalInformation.this, RecipeScreen.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PersonalInformation.this, HomeScreen.class);
                startActivity(intent);
            }
        });
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle shopping list button click (navigate to shopping list screen)
                Intent intent = new Intent(PersonalInformation.this, ShoppingListScreen.class);
                startActivity(intent);
            }
        });
        btnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle ingredient button click (navigate to ingredient screen)
                Intent intent = new Intent(PersonalInformation.this, IngredientScreen.class);
                startActivity(intent);
            }
        });
    }

    public void createPersonalInfoScreenButtonClicked(View v) {
        //Verify no null fields, remove whitespace from all fields
        boolean emptyCheck = true;
        int createHeight = 0;
        int createWeight = 0;
        int createAge = 0;
        String createGender = "";

        /* Binds fields to variables, checking for null and whitespace */
        if (createHeightEditText != null) {
            try {
                createHeight = Integer.parseInt(createHeightEditText.getText().toString().trim());
            } catch (NumberFormatException e) {
                makeText(PersonalInformation.this, "Please enter a valid height", Toast.LENGTH_SHORT).show();
            }
            if (createHeight == 0) {
                emptyCheck = false;
                createHeightEditText.setError(("Please enter a height."));
                createHeightEditText.requestFocus();
            } else {
                userViewModel.updateUserHeight(createHeight);
            }
        } else {
            makeText(PersonalInformation.this, "Please enter a valid height", Toast.LENGTH_SHORT).show();
            createHeightEditText.requestFocus();
        }

        if (createWeightEditText != null) {
            try {
                createWeight = Integer.parseInt(createWeightEditText.getText().toString().trim());
            } catch (NumberFormatException e) {
                makeText(PersonalInformation.this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
            }
            if (createWeight == 0) {
                emptyCheck = false;
                createWeightEditText.setError(("Please enter a weight."));
                createWeightEditText.requestFocus();
            } else {
                userViewModel.updateUserWeight(createWeight);
            }
        } else {
            makeText(PersonalInformation.this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
            createWeightEditText.requestFocus();
        }

        if (createGenderEditText != null) {
            createGender = createGenderEditText.getText().toString().trim();
            if (createGender.isEmpty()) {
                emptyCheck = false;
                createGenderEditText.setError(("Please enter a gender"));
                createGenderEditText.requestFocus();
            } else {
                userViewModel.updateUserGender(createGender);
            }
        }
        if (createAgeEditText != null) {
            try {
                createAge = Integer.parseInt(createAgeEditText.getText().toString().trim());
            } catch (NumberFormatException e) {
                makeText(PersonalInformation.this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
            }
            if (createAge == 0) {
                emptyCheck = false;
                createAgeEditText.setError(("Please enter an age."));
                createAgeEditText.requestFocus();
            } else {
                userViewModel.updateUserAge(createAge);
            }
        } else {
            makeText(PersonalInformation.this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
            createAgeEditText.requestFocus();
        }
    }
}
