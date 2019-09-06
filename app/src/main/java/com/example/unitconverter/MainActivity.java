package com.example.unitconverter;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //resulttv is the textbox for the converted result. inputtv is the textbox for the input the user wants converted.
        final TextView resulttv = findViewById(R.id.resulttv);
        final TextView inputtv = findViewById(R.id.input1);

        final ImageView backgroundImage = findViewById(R.id.backgroundImage);

        //firstDropdown is input type spinner, secondDropdown is result type spinner, typeDropdown is the conversion type spinner at the bottom
        Spinner firstDropdown = findViewById(R.id.dropdown1);
        Spinner secondDropdown = findViewById(R.id.dropdown2);
        Spinner typeDropdown = findViewById(R.id.typedropdown);
        //the different types of conversion possible- length, temperature, and mass. These are listed under conversionTypes, and the options for each are under their respective arrays.
        String[] itemsLength = new String[]{"feet", "inches", "yards", "meters", "centimeters", "kilometers", "miles"};
        String[] itemsTemp = new String[]{"celsius", "fahrenheit", "kelvin"};
        String[] itemsMass = new String[]{"kilograms", "grams", "ounces", "pounds", "stones", "tonnes"};
        String[] conversionTypes = new String[]{"length", "temp", "mass"};
        //Making arrayAdapters from the arrays that spinners can use, then setting each dropdown to the defaults (Length).
        ArrayAdapter<String> adapterLength = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsLength);
        ArrayAdapter<String> adapterTemp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsTemp);
        ArrayAdapter<String> adapterMass = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsMass);
        ArrayAdapter<String> adapterTypes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, conversionTypes);
        firstDropdown.setAdapter(adapterLength);
        secondDropdown.setAdapter(adapterLength);
        typeDropdown.setAdapter(adapterTypes);

        //On click of the typeDropdown
        typeDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //On selection of the new type
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                //Reset the text of the input textfield and result to default, and change the other spinners based on the new type.
                resulttv.setText("Result");
                inputtv.setText("");
                if(pos == 0) {
                    firstDropdown.setAdapter(adapterLength);
                    secondDropdown.setAdapter(adapterLength);
                    backgroundImage.setImageResource(R.drawable.ruler);
                }
                else if(pos == 1) {
                    firstDropdown.setAdapter(adapterTemp);
                    secondDropdown.setAdapter(adapterTemp);
                    backgroundImage.setImageResource(R.drawable.thermometer);
                }
                else {
                    firstDropdown.setAdapter(adapterMass);
                    secondDropdown.setAdapter(adapterMass);
                    backgroundImage.setImageResource(R.drawable.scale);
                }
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        //Conversion button
        FloatingActionButton fab = findViewById(R.id.fab);
        //On conversion button clicked
        fab.setOnClickListener((view) -> {
            //If input type and result type are the same, simply copy the input to the result
            if(firstDropdown.getSelectedItem().toString() == secondDropdown.getSelectedItem().toString())
            {
                resulttv.setText(inputtv.getText().toString());
                return;
            }
            //Convert using the convert(String inputType, String outputType, String input, String conversionType) method
            else resulttv.setText(convert(firstDropdown.getSelectedItem().toString(), secondDropdown.getSelectedItem().toString(), inputtv.getText().toString(), typeDropdown.getSelectedItem().toString()));
            //Check how many decimal places are used, and if too large, cut it down to max 3)
            int index = resulttv.getText().toString().indexOf(".");
            if(Float.parseFloat(resulttv.getText().toString()) == Math.ceil(Float.parseFloat(resulttv.getText().toString()))) {
                resulttv.setText(resulttv.getText().toString().substring(0, index));
                return;
            }
            if(index >= 0 && resulttv.getText().toString().substring(index).length() > 4) {
                resulttv.setText(resulttv.getText().toString().substring(0, index + 4));
            }
        });
    }

    public String convert(String inputType, String outputType, String input, String conversionType) {
        //Call one of the more specific convert functions based on which type is selected
        //returns the string returned by the more specific convert function
        if(conversionType == "length") return convertLength(inputType, outputType, input);
        else if(conversionType == "temp") return convertTemp(inputType, outputType, input);
        return convertMass(inputType, outputType, input);
    }

    public String convertTemp(String inputType, String outputType, String input) {
        //Converts temperatures based on which are selected, then returns them. Takes input, then returns the output as a string using an existing formula.
        if(inputType == "celsius") {
            if(outputType == "fahrenheit") return "" + ((Float.parseFloat(input) * 9/5) + 32);
            else return "" + (Float.parseFloat(input) + 273.15);
        } else if(inputType == "fahrenheit") {
            if(outputType == "celsius") return "" + ((Float.parseFloat(input) - 32) * 5/9);
            else return "" + ((Float.parseFloat(input) - 32) * 5/9 + 273.15);
        } else {
            if(outputType == "celsius") {
                return "" + (Float.parseFloat(input) - 273.15);
            } else {
                return "" + ((Float.parseFloat(input) - 273.15) * 9/5 + 32);
            }
        }
    }

    public String convertMass(String inputType, String outputType, String input) {
        //Converts mass based on which are selected, then returns them. Takes input, then returns the output as a string using an existing formula.
        if(inputType == "kilograms") {
            if(outputType == "grams") return "" + (Float.parseFloat(input) * 1000);
            else if(outputType == "ounces") return "" + (Float.parseFloat(input) * 35.274);
            else if(outputType == "pounds") return "" + (Float.parseFloat(input) * 2.205);
            else if(outputType == "stones") return "" + (Float.parseFloat(input) / 6.35);
            else if(outputType == "tonnes") return "" + (Float.parseFloat(input) / 1000);
        }
        else if(inputType == "grams") {
            if(outputType == "kilograms") return "" + (Float.parseFloat(input) / 1000);
            else if(outputType == "ounces") return "" + (Float.parseFloat(input) / 28.35);
            else if(outputType == "pounds") return "" + (Float.parseFloat(input) / 453.592);
            else if(outputType == "stones") return "" + (Float.parseFloat(input) / 6350.293);
            else if(outputType == "tonnes") return "" + (Float.parseFloat(input) / 1e+6);
        }
        else if(inputType == "ounces") {
            if(outputType == "kilograms") return "" + (Float.parseFloat(input) / 35.274);
            else if(outputType == "grams") return "" + (Float.parseFloat(input) * 28.35);
            else if(outputType == "pounds") return "" + (Float.parseFloat(input) / 16);
            else if(outputType == "stones") return "" + (Float.parseFloat(input) / 224);
            else if(outputType == "tonnes") return "" + (Float.parseFloat(input) / 35273.962);
        }
        else if(inputType == "pounds") {
            if(outputType == "kilograms") return "" + (Float.parseFloat(input) / 2.205);
            else if(outputType == "grams") return "" + (Float.parseFloat(input) * 453.592);
            else if(outputType == "ounces") return "" + (Float.parseFloat(input) * 16);
            else if(outputType == "stones") return "" + (Float.parseFloat(input) / 14);
            else if(outputType == "tonnes") return "" + (Float.parseFloat(input) / 2204.623);
        }
        else if(inputType == "stones") {
            if(outputType == "kilograms") return "" + (Float.parseFloat(input) * 6.35);
            else if(outputType == "grams") return "" + (Float.parseFloat(input) * 6350.293);
            else if(outputType == "ounces") return "" + (Float.parseFloat(input)  * 224);
            else if(outputType == "pounds") return "" + (Float.parseFloat(input) * 14);
            else if(outputType == "tonnes") return "" + (Float.parseFloat(input) / 157.473);
        }
        //Tonnes conversion
        else {
            if(outputType == "kilograms") return "" + (Float.parseFloat(input) * 1000);
            else if(outputType == "grams") return "" + (Float.parseFloat(input) * 1e+6);
            else if(outputType == "ounces") return "" + (Float.parseFloat(input) * 35273.962);
            else if(outputType == "pounds") return "" + (Float.parseFloat(input) * 2204.623);
            //Tonnes to stones conversion
            else return "" + (Float.parseFloat(input) * 157.473);
        }
        return "0";
    }

    public String convertLength(String inputType, String outputType, String input) {
        //Converts length based on which are selected, then returns them. Takes input, then returns the output as a string using an existing formula.
        if(inputType == "feet") {
            if(outputType == "inches") return "" + (Float.parseFloat(input) * 12);
            else if(outputType == "yards") return "" + (Float.parseFloat(input) / 3);
            else if(outputType == "meters") return "" + (Float.parseFloat(input) / 3.281);
            else if(outputType == "centimeters") return "" + (Float.parseFloat(input) * 30.48);
            else if(outputType == "kilometers") return "" + (Float.parseFloat(input) / 3280.84);
            else if(outputType == "miles") return "" + (Float.parseFloat(input) / 5280);
        }
        else if(inputType == "inches") {
            if(outputType == "feet") return "" + (Float.parseFloat(input) / 12);
            else if(outputType == "yards") return "" + (Float.parseFloat(input) / 36);
            else if(outputType == "meters") return "" + (Float.parseFloat(input) / 39.37);
            else if(outputType == "centimeters") return "" + (Float.parseFloat(input) * 2.54);
            else if(outputType == "kilometers") return "" + (Float.parseFloat(input) / 39370.079);
            else if(outputType == "miles") return "" + (Float.parseFloat(input) / 63360);
        }
        else if(inputType == "yards") {
            if(outputType == "feet") return "" + (Float.parseFloat(input) * 3);
            else if(outputType == "inches") return "" + (Float.parseFloat(input) * 36);
            else if(outputType == "meters") return "" + (Float.parseFloat(input) / 1.094);
            else if(outputType == "centimeters") return "" + (Float.parseFloat(input) * 91.44);
            else if(outputType == "kilometers") return "" + (Float.parseFloat(input) / 1093.613);
            else if(outputType == "miles") return "" + (Float.parseFloat(input) / 1760);
        }
        else if(inputType == "meters") {
            if(outputType == "feet") return "" + (Float.parseFloat(input) * 3.281);
            else if(outputType == "inches") return "" + (Float.parseFloat(input) * 39.37);
            else if(outputType == "yards") return "" + (Float.parseFloat(input) * 1.094);
            else if(outputType == "centimeters") return "" + (Float.parseFloat(input) * 100);
            else if(outputType == "kilometers") return "" + (Float.parseFloat(input) / 1000);
            else if(outputType == "miles") return "" + (Float.parseFloat(input) / 1609.344);
        }
        else if(inputType == "centimeters") {
            if(outputType == "feet") return "" + (Float.parseFloat(input) * 3.281);
            else if(outputType == "inches") return "" + (Float.parseFloat(input) * 39.37);
            else if(outputType == "yards") return "" + (Float.parseFloat(input) / 91.44);
            else if(outputType == "meters") return "" + (Float.parseFloat(input) / 100);
            else if(outputType == "kilometers") return "" + (Float.parseFloat(input) / 100000);
            else if(outputType == "miles") return "" + (Float.parseFloat(input) / 160934.4);
        }
        else if(inputType == "kilometers") {
            if(outputType == "feet") return "" + (Float.parseFloat(input) * 3280.84);
            else if(outputType == "inches") return "" + (Float.parseFloat(input) * 39370.079);
            else if(outputType == "yards") return "" + (Float.parseFloat(input) * 1093.613);
            else if(outputType == "centimeters") return "" + (Float.parseFloat(input) * 100000);
            else if(outputType == "meters") return "" + (Float.parseFloat(input) * 1000);
            else if(outputType == "miles") return "" + (Float.parseFloat(input) / 1.609);
        }
        //Miles conversion
        else {
            if(outputType == "feet") return "" + (Float.parseFloat(input) * 5280);
            else if(outputType == "inches") return "" + (Float.parseFloat(input) * 63360);
            else if(outputType == "yards") return "" + (Float.parseFloat(input) * 1760);
            else if(outputType == "meters") return "" + (Float.parseFloat(input) * 1609.344);
            else if(outputType == "centimeters") return "" + (Float.parseFloat(input) * 160934.4);
            else return "" + (Float.parseFloat(input) * 1.609);
        }
        return "0";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
