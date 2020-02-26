package com.example.recipebook_newest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText toSearch;
    Spinner spinner_quantity, spinner_diet;
    String strQuantity = "";
    String strDiet = "";
    String strHealth = "";
    String strVegan = "";
    String strVegetarian = "";
    String strPeanut = "";
    String strAlcohol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toSearch = findViewById(R.id.toSearch_title);

        spinner_quantity = findViewById(R.id.spinner_quantity);
        spinner_diet = findViewById(R.id.spinner_diet);

        ArrayAdapter<CharSequence> adapterQuantity = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);

        adapterQuantity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_quantity.setAdapter(adapterQuantity);
        spinner_quantity.setSelection(adapterQuantity.NO_SELECTION, false);

        ArrayAdapter<CharSequence> adapterDiet = ArrayAdapter.createFromResource(this,
                R.array.diet_array, android.R.layout.simple_spinner_item);

        adapterDiet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_diet.setAdapter(adapterDiet);
        spinner_diet.setSelection(adapterDiet.NO_SELECTION, false);
    }

    public void GoToRecyclerView(View myView)
    {
        Intent myIntent = new Intent(MainActivity.this, list.class);

        if (TextUtils.isEmpty(toSearch.getText()))
            Toast.makeText(getApplicationContext(),"Enter a value!",Toast.LENGTH_SHORT).show();
        else
        {
            String urlSearch = "https://api.edamam.com/search?q=" + toSearch.getText().toString()
                    + "&app_id=384a2360&app_key=1e3c75bdc9c99293fb4ec4a886011e70" + getQuantity(spinner_quantity) + getDiet(spinner_diet) + strHealth;
            myIntent.putExtra("urlSearch", urlSearch);
            myIntent.putExtra("itemSearch", toSearch.getText().toString());
            startActivity(myIntent);
        }
    }

    String getQuantity(Spinner quantity)
    {
        switch (quantity.getSelectedItemPosition())
        {
            case 0:
                strQuantity = "&from=0&to=10";
                break;
            case 1:
                strQuantity = "&from=0&to=30";
                break;
            case 2:
                strQuantity = "&from=0&to=50";
                break;
            case 3:
                strQuantity = "&from=0&to=100";
                break;
        }
        return strQuantity;
    }
    String getDiet(Spinner diet)
    {
        switch (diet.getSelectedItemPosition())
        {
            case 0:
                break;
            case 1:
                strDiet = "&diet=balanced";
                break;
            case 2:
                strDiet = "&diet=high-protein";
                break;
            case 3:
                strDiet = "&diet=low-fat";
                break;
            case 4:
                strDiet = "&diet=low-carb";
                break;
        }

        return strDiet;
    }
    public void onCheckedBox(View myView)
    {
        boolean checked = ((CheckBox)myView).isChecked();

        switch (myView.getId())
        {
            case R.id.check_vegan:
                if (checked)
                    strVegan = "&health=vegan";
                else
                    strVegan = "";
                break;
            case R.id.check_vegetarian:
                if (checked)
                    strVegetarian = "&health=vegetarian";
                else
                    strVegetarian = "";
                break;
            case R.id.check_peanut_free:
                if (checked)
                    strPeanut = "&health=peanut-free";
                else
                    strPeanut = "";
                break;
            case R.id.check_alcohol_free:
                if (checked)
                    strAlcohol = "&health=alcohol-free";
                else
                    strAlcohol = "";
                break;
        }
        strHealth = strVegan.concat(strVegetarian).concat(strPeanut).concat(strAlcohol);
    }
}
