package com.example.recipebook_newest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleRecipe extends AppCompatActivity implements CallBackMe {

    ImageView single_image;
    TextView single_title, single_source, single_time, single_portions, single_calories, single_diet, single_health_labels, single_ingredients, single_link;
    String single_url = "";
    String recipeId = "";
    String dietLabel = "";
    String healthLabel = "";
    String ingredients = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        single_image = findViewById(R.id.single_image);
        single_title = findViewById(R.id.single_title);
        single_source = findViewById(R.id.single_source);
        single_time = findViewById(R.id.single_time);
        single_portions = findViewById(R.id.single_portions);
        single_calories = findViewById(R.id.single_calories);
        single_diet = findViewById(R.id.single_diet);
        single_health_labels = findViewById(R.id.single_health_labels);
        single_ingredients = findViewById(R.id.single_ingredients);
        single_link = findViewById(R.id.single_link);

        Intent receivedIntent = getIntent();
        recipeId = receivedIntent.getStringExtra("recipeId");

        single_url = "https://api.edamam.com/search?r=" + recipeId +
                "&app_id=384a2360&app_key=1e3c75bdc9c99293fb4ec4a886011e70";

        JsonRetriever.RetrieveFromURL(this, single_url, this);
    }

    @Override
    public void CallThis(String jsonText)
    {
        try {
            JSONArray rootArray = new JSONArray(jsonText);
            final JSONObject recipeObject = rootArray.getJSONObject(0);

            //Set image
            Picasso.get().load(recipeObject.getString("image")).fit().into(single_image);
            //Set recipe name
            single_title.setText(recipeObject.getString("label"));
            // Set source
            single_source.setText("by " + recipeObject.getString("source"));

            //Set cook time
            if (recipeObject.getDouble("totalTime") == 0)
                single_time.setText("N/A");
            else
            {
                String timeString = "Ready in " + Double.valueOf(recipeObject.getDouble("totalTime")).intValue() + " min";
                single_time.setText(timeString);
            }
            //Set portions
            String portions = Double.valueOf(recipeObject.getDouble("yield")).intValue() + " servings";
            single_portions.setText(portions);

            //Set calories
            String calories = Double.valueOf(recipeObject.getDouble("calories")).intValue() + " cals";
            single_calories.setText(calories);

            //Set Diet
            JSONArray dietArray = recipeObject.getJSONArray("dietLabels");
            if (dietArray.length() == 0)
            {
                single_diet.setText("N/A");
                single_diet.setGravity(Gravity.CENTER);
            }
            else
            {
                for (int i = 0; i < dietArray.length(); ++i)
                {
                    try
                    {
                        dietLabel += dietArray.getString(i) + "\n";
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }single_diet.setText(dietLabel);
            }

            //Set Health Labels
            JSONArray healthArray = recipeObject.getJSONArray("healthLabels");
            if (healthArray.length() == 0)
            {
                single_health_labels.setText("N/A");
                single_health_labels.setGravity(Gravity.CENTER);
            }
            else
            {
                for (int i = 0; i < healthArray.length(); ++i)
                {
                    try
                    {
                        healthLabel += healthArray.getString(i) + "\n";
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }single_health_labels.setText(healthLabel);
            }

            //Set Health Labels
            JSONArray ingredientsArray = recipeObject.getJSONArray("ingredientLines");
            if (ingredientsArray.length() == 0)
            {
                single_ingredients.setText("N/A");
                single_ingredients.setGravity(Gravity.CENTER);
            }
            else
            {
                for (int i = 0; i < ingredientsArray.length(); ++i)
                {
                    try
                    {
                        ingredients += ingredientsArray.getString(i) + "\n";
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }single_ingredients.setText(ingredients);
            }

            //Set external link
            System.out.println(recipeObject.getString("url"));
            single_link.setText("Click here and check how to do!");
            single_link.setMovementMethod(LinkMovementMethod.getInstance());
            single_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    try {
                        browserIntent.setData(Uri.parse(recipeObject.getString("url")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(browserIntent);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
