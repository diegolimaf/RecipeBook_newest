package com.example.recipebook_newest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class result extends AppCompatActivity implements CallBackMe{

    ImageView recipe_image;
    TextView recipe_title, recipe_portions, recipe_time, recipe_diet, recipe_allergens, recipe_source;
    String url = "";
    String dietArray = "";
    String allergensArray = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recipe_image = findViewById(R.id.recipe_image);
        recipe_allergens = findViewById(R.id.recipe_allergens);
        recipe_diet = findViewById(R.id.recipe_diet);
        recipe_portions = findViewById(R.id.recipe_portions);
        recipe_source = findViewById(R.id.recipe_source);
        recipe_time = findViewById(R.id.recipe_time);
        recipe_title = findViewById(R.id.recipe_title);

        Intent receivedIntent = getIntent();
        url = receivedIntent.getStringExtra("urlSearch");

        JsonRetriever.RetrieveFromURL(this, url, this);
    }

    @Override
    public void CallThis(String jsonText)
    {
        //Parse the Json here
        try
        {
            JSONObject json = new JSONObject(jsonText);
            JSONArray hits = json.getJSONArray("hits");
            JSONObject hit1 = hits.getJSONObject(0);
            JSONObject recipe1 = hit1.getJSONObject("recipe");

            recipe_title.setText(recipe1.getString("label"));
            Picasso.get().load(recipe1.getString("image")).fit().into(recipe_image);
            recipe_source.setText(recipe1.getString("source"));

            // Set portions with condition
            if (Double.valueOf(recipe1.getDouble("yield")).intValue() == 0)
                recipe_portions.setText("N/A");
            else
            {
                recipe_portions.setText(String.valueOf(Double.valueOf(recipe1.getDouble("yield")).intValue()));
            }

            // Set time with condition
            if (Double.valueOf(recipe1.getDouble("totalTime")).intValue() == 0)
                recipe_time.setText("N/A");
            else
                recipe_time.setText(Double.valueOf(recipe1.getDouble("totalTime")).intValue() + "min");

            // Set diet with condition
            JSONArray diet = recipe1.getJSONArray("dietLabels");

            if (diet.length() == 0)
            {
                recipe_diet.setText("N/A");
                recipe_diet.setGravity(Gravity.CENTER);
            }
            else
            {
                for (int i = 0; i < diet.length(); i++)
                    dietArray += diet.getString(i) + " \n";
                recipe_diet.setText(dietArray);
            }

            // Set allergens with condition
            JSONArray allergens = recipe1.getJSONArray("cautions");

            if (allergens.length() == 0)
                recipe_allergens.setText("No allergens informed!");
            else{
                for (int i = 0; i < allergens.length(); i++)
                    allergensArray += allergens.getString(i) + " \n";
                recipe_allergens.setText(allergensArray);
            }

        }

        catch (JSONException e)
        {
            Log.e("not found",url);
        }
    }
}
