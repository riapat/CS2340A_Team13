package com.example.cs2340a_team13.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.example.cs2340a_team13.R;

import java.util.ArrayList;
import java.util.List;

public class InputMealScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_meal_screen);


        Button btnInputMeal = findViewById(R.id.InputMeal);
        Button btnRecipe = findViewById(R.id.Recipe);
        Button btnIngredient = findViewById(R.id.Ingredients);
        Button btnShoppingList = findViewById(R.id.ShoppingList);
        Button btnHome = findViewById(R.id.Home);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputMealScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
        btnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle recipe button click (navigate to recipe screen)
                Intent intent = new Intent(InputMealScreen.this, RecipeScreen.class);
                startActivity(intent);
            }
        });

        btnIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle ingredients button click (navigate to ingredients screen)
                Intent intent = new Intent(InputMealScreen.this, IngredientScreen.class);
                startActivity(intent);
            }
        });
        btnShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle shopping list button click (navigate to shopping list screen)
                Intent intent = new Intent(InputMealScreen.this, ShoppingListScreen.class);
                startActivity(intent);
            }
        });

        //add a AnyChartView in the XML layout with id any_chart_view
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        Cartesian lineChart = AnyChart.line();
        lineChart.animation(true);
        lineChart.padding(10d, 20d, 5d, 20d);

        //chart settings
        lineChart.crosshair().enabled(true);
        lineChart.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        lineChart.tooltip().positionMode(TooltipPositionMode.POINT);

        lineChart.title("Trend of Daily Calorie Intake");
        lineChart.yAxis(0).title("Number of Calories");
        lineChart.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        //create a loop going through the database, how to restrict to a week?

        List <DataEntry> weeklyData = new ArrayList<>();
        weeklyData.add(new CustomDataEntry("1986", 3.6, 2.3, 2.8));
        weeklyData.add(new CustomDataEntry("1987", 7.1, 4.0, 4.1));
        weeklyData.add(new CustomDataEntry("1988", 8.5, 6.2, 5.1));
        weeklyData.add(new CustomDataEntry("1989", 9.2, 11.8, 6.5));
        weeklyData.add(new CustomDataEntry("1990", 10.1, 13.0, 12.5));
        weeklyData.add(new CustomDataEntry("1991", 11.6, 13.9, 18.0));
        weeklyData.add(new CustomDataEntry("1992", 16.4, 18.0, 21.0));
        weeklyData.add(new CustomDataEntry("1993", 18.0, 23.3, 20.3));
        weeklyData.add(new CustomDataEntry("1994", 13.2, 24.7, 19.2));

        //cartesian.data(data);

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
    private class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }
    }

}