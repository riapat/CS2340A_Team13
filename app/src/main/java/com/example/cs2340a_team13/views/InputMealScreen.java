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

        TextView ageTextView = findViewById(R.id.ageTextView);
        TextView genderTextView = findViewById(R.id.genderTextView);
        TextView heightTextView = findViewById(R.id.heightTextView);
        TextView weightTextView = findViewById(R.id.weightTextView);
        TextView recommendedCaloriesTextView = findViewById(R.id.recommendedCaloriesTextView);
        TextView currentCaloriesTextView = findViewById(R.id.currentCaloriesTextView);

        Button btnInputMeal = findViewById(R.id.InputMeal);
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
        Log.d("InputMealScreen", "User loaded in input meal screen "
                + userViewModel.getUser().getUsername());

        if (userViewModel.getUser() == null) {
            ageTextView.setText("Age:  yrs");
            genderTextView.setText("Gender: ");
            heightTextView.setText("Height:  cm");
            weightTextView.setText("Weight:  kg");
            recommendedCaloriesTextView.setText("Advised Daily Calories: 0");
            currentCaloriesTextView.setText("Current Day's Calories: 0");
        } else {
            ageTextView.setText(String.format("Age: %d yrs",
                    userViewModel.getUser().getAge()));
            genderTextView.setText(String.format("Gender: %s",
                    userViewModel.getUser().getGender()));
            heightTextView.setText(String.format("Height: %d cm",
                    userViewModel.getUser().getHeight()));
            weightTextView.setText(String.format("Weight: %d kg",
                    userViewModel.getUser().getWeight()));
            recommendedCaloriesTextView.setText(String.format("Advised Daily Calories: %f",
                    userViewModel.calculateCalories()));
            currentCaloriesTextView.setText(String.format("Current Day's Calories: %d",
                    userViewModel.currentCalories()));
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
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        Cartesian lineChart = AnyChart.line();
        lineChart.animation(true);
        lineChart.padding(10d, 20d, 5d, 20d);

        // chart settings
        Button lineButton = findViewById(R.id.lineChart);
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineChart.crosshair().enabled(true);
                lineChart.crosshair()
                        .yLabel(true)
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                lineChart.tooltip().positionMode(TooltipPositionMode.POINT);

                lineChart.title("Trend of Daily Calorie Intake");
                lineChart.yAxis(0).title("Number of Calories");
                lineChart.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                // create a loop going through the database, using the time stamp

                List<DataEntry> weeklyData = new ArrayList<>();
                weeklyData.add(new CustomDataEntry("1986", 3.6, 2.3, 2.8));
                weeklyData.add(new CustomDataEntry("1987", 7.1, 4.0, 4.1));
                weeklyData.add(new CustomDataEntry("1988", 8.5, 6.2, 5.1));
                weeklyData.add(new CustomDataEntry("1989", 9.2, 11.8, 6.5));
                weeklyData.add(new CustomDataEntry("1990", 10.1, 13.0, 12.5));
                weeklyData.add(new CustomDataEntry("1991", 11.6, 13.9, 18.0));
                weeklyData.add(new CustomDataEntry("1992", 16.4, 18.0, 21.0));
                weeklyData.add(new CustomDataEntry("1993", 18.0, 23.3, 20.3));
                weeklyData.add(new CustomDataEntry("1994", 13.2, 24.7, 19.2));

                Set weeklySet = Set.instantiate();
                weeklySet.data(weeklyData);

                Mapping series1Mapping = weeklySet.mapAs("{ x: 'x', value: 'value' }");
                Mapping series2Mapping = weeklySet.mapAs("{ x: 'x', value: 'value2' }");
                Mapping series3Mapping = weeklySet.mapAs("{ x: 'x', value: 'value3' }");

                Line series1 = lineChart.line(series1Mapping);
                series1.name("Brandy");
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                Line series2 = lineChart.line(series2Mapping);
                series2.name("Whiskey");
                series2.hovered().markers().enabled(true);
                series2.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series2.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                Line series3 = lineChart.line(series3Mapping);
                series3.name("Tequila");
                series3.hovered().markers().enabled(true);
                series3.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series3.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                lineChart.legend().enabled(true);
                lineChart.legend().fontSize(13d);
                lineChart.legend().padding(0d, 0d, 10d, 0d);

                anyChartView.setChart(lineChart);
            }
        });

        Button barChartButton = findViewById(R.id.barChart);
        barChartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cartesian cartesian = AnyChart.column();

                // get calculated goal value
                // get daily value so far

                List<DataEntry> data = new ArrayList<>();
                data.add(new ValueDataEntry("Achieved", 1500)); // tester values
                data.add(new ValueDataEntry("Goal", 2500));

                Column column = cartesian.column(data);

                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("${%Value}{groupsSeparator: }");

                cartesian.animation(true);
                cartesian.title("Goal vs Achieved Daily Calorie Count");

                cartesian.yScale().minimum(0d);

                cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);

                cartesian.xAxis(0).title("Categories");
                cartesian.yAxis(0).title("Calories");

                anyChartView.setChart(cartesian);
            }
        });

    }

    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }
}