package com.example.recipebook_newest;

import org.json.JSONArray;

public class RecipeData {

    public String urlImage;
    public String label;
    public String source;
    public double portions;
    public double cookTime;
    public JSONArray dietLabels;
    public JSONArray cautions;


    public RecipeData(String urlImage, String label, String source, double portions, double cookTime, JSONArray dietLabels, JSONArray healthLabels)
    {
        this.urlImage = urlImage;
        this.label = label;
        this.source = source;
        this.portions = portions;
        this.dietLabels = dietLabels;
        this.cautions = healthLabels;
        this.cookTime = cookTime;
    }
}
