package com.armanhossain.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Creating Class variables or Fields so that the variables can be accessed from any where in the class
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;

    EditText ageEditText;
    EditText feetEditText;
    EditText inchesEditText;
    EditText weightEditText;

    Button calculateButton;

    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        setupViews();
        setupButtonClickListener();
    }

    private void setupViews() {
        maleRadioButton = findViewById(R.id.rb_male);
        femaleRadioButton = findViewById(R.id.rb_female);

        ageEditText = findViewById(R.id.et_age);
        feetEditText = findViewById(R.id.et_feet);
        inchesEditText = findViewById(R.id.et_inches);
        weightEditText = findViewById(R.id.et_weight);

        calculateButton = findViewById(R.id.btn_calculate);

        resultTextView = findViewById(R.id.tv_result);
    }

    private void setupButtonClickListener() {
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double bmi = calculateBMI();

                String ageText = ageEditText.getText().toString();
                int age = Integer.parseInt(ageText);

                if(age >= 18){
                    checkConditionOnBMIAndDisplayResult(bmi);
                } else {
                    checkConditionOnBMIAndDisplayGuidance(bmi);
                }
            }
        });
    }

    private double calculateBMI() {
        String feetText = feetEditText.getText().toString();
        String inchesText = inchesEditText.getText().toString();
        String weightText = weightEditText.getText().toString();

        // Converting "String"s into "int" variables
        int feet = Integer.parseInt(feetText);
        int inches = Integer.parseInt(inchesText);
        int weight = Integer.parseInt(weightText);

        // Get Total Inches by multiplying feet * 12 and adding the inches
        int totalInches = getTotalInches(feet, inches);
        // convert Inches into Meters by multiplying 0.0254
        double heightInMeters = convertInchesToMeters(totalInches);

        // Calculating BMI by multiplying weight and height using formula : weight / (height * height)
        return calculateBMIByWeightAndHeight(weight, heightInMeters);
    }

    private int getTotalInches(int feet, int inches) {
        return feet * 12 + inches;
    }

    private double convertInchesToMeters(int totalInches) {
        return totalInches * 0.0254;
    }

    private double calculateBMIByWeightAndHeight(int weight, double heightInMeters) {
        return weight/(heightInMeters * heightInMeters);
    }

    private void checkConditionOnBMIAndDisplayResult(double bmi) {
        DecimalFormat decimalFormatter = new DecimalFormat("##.##");
        String bmiInString = decimalFormatter.format(bmi);

        String resultText;
        if(bmi < 18.5){
            // Display underweight
            resultText = bmiInString + " - You are underweight";
        } else if (bmi > 25){
            // Display underweight
            resultText = bmiInString + " - You are overweight";
        } else {
            // Display healthy
            resultText = bmiInString + " - You are a healthy person";
        }

        resultTextView.setText(resultText);
    }

    private void checkConditionOnBMIAndDisplayGuidance(double bmi) {
        DecimalFormat decimalFormatter = new DecimalFormat("##.##");
        String bmiInString = decimalFormatter.format(bmi);

        String resultText;
        if(maleRadioButton.isChecked()){
            // Display guidance for male
            resultText = bmiInString + " - As you are under 18, please consult with your doctor for healthy range for boys";
        } else if (femaleRadioButton.isChecked()){
            // Display guidance for female
            resultText = bmiInString + " - As you are under 18, please consult with your doctor for healthy range for girls";
        } else {
            // Display general guidance
            resultText = bmiInString + " - As you are under 18, please consult with your doctor for healthy range.";
        }

        resultTextView.setText(resultText);
    }
}