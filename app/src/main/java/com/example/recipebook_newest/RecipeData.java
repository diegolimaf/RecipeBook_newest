package com.example.recipebook_newest;

import org.json.JSONArray;

public class RecipeData {

    String recipeId;
    String urlImage;
    String label;
    String source;
    double portions;
    double cookTime;
    JSONArray dietLabels;
    JSONArray cautions;



    public RecipeData(String recipeId, String urlImage, String label, String source, double portions, double cookTime, JSONArray dietLabels, JSONArray healthLabels)
    {
        this.recipeId = recipeId;
        this.urlImage = urlImage;
        this.label = label;
        this.source = source;
        this.portions = portions;
        this.dietLabels = dietLabels;
        this.cautions = healthLabels;
        this.cookTime = cookTime;
    }
}
