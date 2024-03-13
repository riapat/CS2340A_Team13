package com.example.cs2340a_team13.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.core.cartesian.series.Line;
import com.anychart.core.cartesian.series.Column;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.anychart.enums.HoverMode;
import com.example.cs2340a_team13.R;

import com.anychart.enums.Position;

import java.util.ArrayList;
import java.util.List;
import com.example.cs2340a_team13.model.Meal;
import com.example.cs2340a_team13.viewModels.MealViewModel;
import com.example.cs2340a_team13.viewModels.UserViewModel;


public class InputMealScreen extends AppCompatActivity {

    private MealViewModel mealViewModel;
    private UserViewModel userViewModel;

    @SuppressLint({ "DefaultLocale", "SetTextI18n" })
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal_screen);

        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView genderTextView = findViewById(R.id.genderTextView);
        TextView heightTextView = findViewById(R.id.heightTextView);
        TextView weightTextView = findViewById(R.id.weightTextView);
        TextView recommendedCaloriesTextView = findViewById(R.id.recommendedCaloriesTextView);
        TextView currentCaloriesTextView = findViewById(R.id.currentCaloriesTextView);

        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);
        EditText mealNameEditText = findViewById(R.id.mealNameEditText);
        EditText caloriesEditText = findViewById(R.id.estimatedCaloriesEditText);
        Button submitButton = findViewById(R.id.submitButton);

        mealViewModel = MealViewModel.getInstance();
        Button btnPersonalInfo = findViewById(R.id.PersonalInfo);

        userViewModel = UserViewModel.getInstance();
        String username = getIntent().getStringExtra("username");
        userViewModel.loadUser(username);
        Log.d("InputMealScreen", "User loaded in input meal screen " + userViewModel.getUser().getUsername());

        if (userViewModel.getUser() == null) {
            ageTextView.setText("Age:  yrs");
            genderTextView.setText("Gender: ");
            heightTextView.setText("Height:  cm");
            weightTextView.setText("Weight:  kg");
            recommendedCaloriesTextView.setText("Advised Daily Calories: 0");
            currentCaloriesTextView.setText("Current Day's Calories: 0");
        } else {
            Log.d("InputMealScreen", "User loaded in input meal screen " + userViewModel.getUser().getAge());
            ageTextView.setText("Age: " + userViewModel.getUser().getAge() + " yrs");
            genderTextView.setText("Gender: " +  userViewModel.getUser().getGender());
            heightTextView.setText("Height: " + userViewModel.getUser().getHeight() + " cm");
            weightTextView.setText("Weight: " + userViewModel.getUser().getWeight() + " kg");
            recommendedCaloriesTextView.setText("Advised Daily Calories: " + userViewModel.calculateCalories());
            Log.d("InputMealScreen", "Current calories: " + userViewModel.currentCalories());
            currentCaloriesTextView.setText("Current Day's Calories: " + userViewModel.currentCalories());
        }

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealScreen.this, HomeScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle recipe button click (navigate to recipe screen)
                Intent intent = new Intent(InputMealScreen.this, RecipeScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        btnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle ingredients button click (navigate to ingredients screen)
                Intent intent = new Intent(InputMealScreen.this, IngredientScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle shopping list button click (navigate to shopping list screen)
                Intent intent = new Intent(InputMealScreen.this, ShoppingListScreen.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mealName = mealNameEditText.getText().toString();
                String caloriesText = caloriesEditText.getText().toString();
                if (!caloriesText.isEmpty()) {
                    try {
                        int calories = Integer.parseInt(caloriesText);
                        // Check if meal name contains numbers
                        boolean containsNumber = false;
                        for (char c : mealName.toCharArray()) {
                            if (Character.isDigit(c)) {
                                containsNumber = true;
                                break;
                            }
                        }
                        if (containsNumber) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InputMealScreen.this);
                            builder.setTitle("Invalid Input");
                            builder.setMessage("Meal name cannot contain numbers");
                            builder.setPositiveButton("OK", null);
                            builder.show();
                        } else {
                            // Create meal object
                            Meal meal = mealViewModel.createMeal(mealName, calories);
                            currentCaloriesTextView.setText(String.format("Current Day's Calories: %d",
                                    userViewModel.currentCalories()));
                            // Clear EditText fields
                            mealNameEditText.setText("");
                            caloriesEditText.setText("");
                        }
                    } catch (NumberFormatException e) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(InputMealScreen.this);
                        builder.setTitle("Invalid Input");
                        builder.setMessage("Calories must be a number");
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(InputMealScreen.this);
                    builder.setTitle("Invalid Input");
                    builder.setMessage("Please enter calories");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
            }
        });

        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealScreen.this, PersonalInformation.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        // add a AnyChartView in the XML layout with id any_chart_view
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        // chart settings
        Button lineButton = findViewById(R.id.lineChart);
        lineButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Cartesian lineChart = AnyChart.line();
                lineChart.animation(true);
                lineChart.padding(10d, 20d, 5d, 20d);
                lineChart.crosshair().enabled(true);
                lineChart.crosshair()
                        .yLabel(true)
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                lineChart.tooltip().positionMode(TooltipPositionMode.POINT);

                lineChart.title("Trend of Daily Calorie Intake");
                lineChart.yAxis(0).title("Number of Calories");
                lineChart.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                List<DataEntry> monthlyData = userViewModel.calculateMonthlyCalories();

                Set monthlySet = Set.instantiate();
                monthlySet.data(monthlyData);

                Mapping seriesMapping = monthlySet.mapAs("{ x: 'x', value: 'value' }");

                Line series = lineChart.line(seriesMapping);
                series.name("Monthly Calorie Intake");
                series.data(monthlyData);
                series.hovered().markers().enabled(true);
                series.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                lineChart.legend().enabled(true);
                lineChart.legend().fontSize(13d);
                lineChart.legend().padding(0d, 0d, 10d, 0d);
                lineChart.draw(true);
                anyChartView.setChart(lineChart);
                anyChartView.setVisibility(View.VISIBLE);
            }
        });

        Button barChartButton = findViewById(R.id.barChart);
        barChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("bar chart", "bar chart clicked");

                Cartesian bar = AnyChart.column();
                bar.animation(true);
                bar.padding(10d, 20d, 5d, 20d);

                // get calculated goal value
                // get daily value so far

                List<DataEntry> data = new ArrayList<>();
                data.add(new ValueDataEntry("Goal", userViewModel.calculateCalories()));
                data.add(new ValueDataEntry("Consumed", userViewModel.currentCalories()));

                Column column = bar.column(data);

                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("${%Value}{groupsSeparator: }");

                bar.animation(true);
                bar.title("Goal vs Achieved Daily Calorie Count");

                bar.yScale().minimum(0d);

                bar.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

                bar.tooltip().positionMode(TooltipPositionMode.POINT);
                bar.interactivity().hoverMode(HoverMode.BY_X);

                bar.xAxis(0).title("Categories");
                bar.yAxis(0).title("Calories");
                anyChartView.setChart(bar);
                anyChartView.setVisibility(View.VISIBLE);
            }
        });

    }

}
