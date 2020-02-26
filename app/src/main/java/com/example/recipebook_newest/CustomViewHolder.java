package com.example.recipebook_newest;

import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView recipe_image;
    private TextView recipe_title, recipe_portions, recipe_time, recipe_diet, recipe_allergens, recipe_source;
    private String dietArray = "";
    private String allergensArray = "";
    private String recipeId = "";

    public CustomViewHolder(@NonNull View customLayoutView)
    {
        super(customLayoutView);

        recipe_image = customLayoutView.findViewById(R.id.recipe_image);
        recipe_allergens = customLayoutView.findViewById(R.id.recipe_allergens);
        recipe_diet = customLayoutView.findViewById(R.id.recipe_diet);
        recipe_portions = customLayoutView.findViewById(R.id.recipe_portions);
        recipe_source = customLayoutView.findViewById(R.id.recipe_source);
        recipe_time = customLayoutView.findViewById(R.id.recipe_time);
        recipe_title = customLayoutView.findViewById(R.id.recipe_title);
        itemView.setOnClickListener(this);
    }

    public void bindData(final RecipeData data)
    {
        recipe_title.setText(data.label);
        Picasso.get().load(data.urlImage).fit().into(recipe_image);
        recipe_source.setText(data.source);

        // Set portions with condition
        if (data.portions == 0)
            recipe_portions.setText("N/A");
        else
        {
            String portionsString = String.valueOf(Double.valueOf(data.portions).intValue());
            recipe_portions.setText(portionsString);
        }

        // Set time with condition
        if (data.cookTime == 0)
            recipe_time.setText("N/A");
        else
        {
            String timeString = Double.valueOf(data.cookTime).intValue() + "min";
            recipe_time.setText(timeString);
        }

        // Set diet labels - It is a JSON Array.
        if (data.dietLabels.length() == 0)
        {
            recipe_diet.setText("N/A");
            recipe_diet.setGravity(Gravity.CENTER);
        }
        else
        {
            for (int i = 0; i < data.dietLabels.length(); ++i)
            {
                try
                {
                    dietArray += data.dietLabels.getString(i) + " \n";
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            recipe_diet.setText(dietArray);
            dietArray = "";
        }

        // Set allergens - It is a JSON Array.
        if (data.cautions.length() == 0)
        {
            recipe_allergens.setText("No allergens informed!");
            recipe_allergens.setGravity(Gravity.CENTER);
        }
        else
        {
            for (int i = 0; i < data.cautions.length(); ++i)
            {
                try
                {
                    allergensArray += data.cautions.getString(i) + " \n";
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
            recipe_allergens.setText(allergensArray);
            allergensArray = "";
        }

        recipeId = data.recipeId;
    }

    @Override
    public void onClick(View myView)
    {
        Intent myIntent = new Intent(myView.getContext(), SingleRecipe.class);
        myIntent.putExtra("recipeId", recipeId);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) myView.getContext(), recipe_image, "extend_image");
        myView.getContext().startActivity(myIntent, options.toBundle());
    }
}
