package com.example.unitconverter;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView resulttv = findViewById(R.id.resulttv);
        final TextView inputtv = findViewById(R.id.input1);

        Spinner firstDropdown = findViewById(R.id.dropdown1);
        Spinner secondDropdown = findViewById(R.id.dropdown2);
        String[] items = new String[]{"feet", "inches", "yards", "meters"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        firstDropdown.setAdapter(adapter);
        secondDropdown.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((view) -> {
            if(firstDropdown.getSelectedItem().toString() == secondDropdown.getSelectedItem().toString())
                resulttv.setText(inputtv.getText().toString());
            else resulttv.setText(convert(firstDropdown.getSelectedItem().toString(), secondDropdown.getSelectedItem().toString(), inputtv.getText().toString()));
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

    public String convert(String inputType, String outputType, String input) {
        if(inputType == "feet") {
            if(outputType == "inches") return "" + (Float.parseFloat(input) * 12);
            if(outputType == "yards") return "" + (Float.parseFloat(input) / 3);
            if(outputType == "meters") return "" + (Float.parseFloat(input) / 3.281);
        }
        if(inputType == "inches") {
            if(outputType == "feet") return "" + (Float.parseFloat(input) / 12);
            if(outputType == "yards") return "" + (Float.parseFloat(input) / 36);
            if(outputType == "meters") return "" + (Float.parseFloat(input) / 39.37);
        }
        if(inputType == "yards") {
            if(outputType == "feet") return "" + (Float.parseFloat(input) * 3);
            if(outputType == "inches") return "" + (Float.parseFloat(input) * 36);
            if(outputType == "meters") return "" + (Float.parseFloat(input) / 1.094);
        }
        if(outputType == "feet") return "" + (Float.parseFloat(input) * 3.281);
        if(outputType == "inches") return "" + (Float.parseFloat(input) * 39.37);
        return "" + (Float.parseFloat(input) * 1.094);
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
