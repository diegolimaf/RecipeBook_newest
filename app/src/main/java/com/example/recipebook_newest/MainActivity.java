package com.example.recipebook_newest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText toSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toSearch = findViewById(R.id.toSearch);
    }

    public void showResults(View myView)
    {
        Intent myIntent = new Intent(this, list.class);

        if (TextUtils.isEmpty(toSearch.getText()))
            Toast.makeText(getApplicationContext(),"Enter a value!",Toast.LENGTH_SHORT).show();
        else
        {
            String urlSearch = "https://api.edamam.com/search?q=" + toSearch.getText().toString()
                    + "&app_id=384a2360&app_key=1e3c75bdc9c99293fb4ec4a886011e70&from=0&to=10";
            myIntent.putExtra("urlSearch", urlSearch);
            startActivity(myIntent);
        }
    }
}
