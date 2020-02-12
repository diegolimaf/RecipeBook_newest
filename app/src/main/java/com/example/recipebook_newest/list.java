package com.example.recipebook_newest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity implements CallBackMe{

    RecyclerView recyclerView;
    List<RecipeData> myDataSet = new ArrayList<>();
    String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager LinearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LinearLayout);

        Intent receivedIntent = getIntent();
        url = receivedIntent.getStringExtra("urlSearch");

        JsonRetriever.RetrieveFromURL(this, url, this);
    }

    @Override
    public void CallThis(String jsonText)
    {
        try
        {
            JSONObject rootObject = new JSONObject(jsonText);
            JSONArray rootRecipes = rootObject.getJSONArray("hits");


            for (int i=0;i<rootRecipes.length();i++)
            {
                JSONObject parentRecipe = rootRecipes.getJSONObject(i);
                JSONObject childRecipe = parentRecipe.getJSONObject("recipe");

                RecipeData c1 = new RecipeData(childRecipe.getString("image"), childRecipe.getString("label"),
                        childRecipe.getString("source"), childRecipe.getDouble("yield"), childRecipe.getDouble("totalTime"),
                        childRecipe.getJSONArray("dietLabels"), childRecipe.getJSONArray("cautions"));
                myDataSet.add(c1);
            }

            RecyclerAdapter r1= new RecyclerAdapter(myDataSet);
            recyclerView.setAdapter(r1);
        }

        catch (JSONException e)
        {
            Log.e("not found",url);
        }
    }
}
