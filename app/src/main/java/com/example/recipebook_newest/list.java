package com.example.recipebook_newest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class list extends AppCompatActivity implements CallBackMe{

    EditText listSearch;
    RecyclerView recyclerView;
    List<RecipeData> myDataSet = new ArrayList<>();
    String url = "";
    String hint = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listSearch = findViewById(R.id.list_search_bar);
        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager LinearLayout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LinearLayout);

        Intent receivedIntent = getIntent();
        url = receivedIntent.getStringExtra("urlSearch");
        hint = receivedIntent.getStringExtra("itemSearch");

        listSearch.setHint("Results for " + hint);

        JsonRetriever.RetrieveFromURL(this, url, this);
    }

    @Override
    public void CallThis(String jsonText)
    {
        try
        {
            JSONObject rootObject = new JSONObject(jsonText);
            JSONArray rootRecipes = rootObject.getJSONArray("hits");

            if (rootRecipes.length() == 0)
            {
                Toast.makeText(getApplicationContext(),"There is no result for " + hint,Toast.LENGTH_SHORT).show();
                new Timer().schedule(new TimerTask()
                {
                    public void run()
                    {
                        startActivity(new Intent(list.this, MainActivity.class));
                    }
                }, 2000 );
            }
            else
            {
                for (int i=0;i<rootRecipes.length();i++)
                {
                    JSONObject parentRecipe = rootRecipes.getJSONObject(i);
                    JSONObject childRecipe = parentRecipe.getJSONObject("recipe");

                    String recipeIdEncoded = URLEncoder.encode(childRecipe.getString("uri"), "UTF-8");

                    RecipeData c1 = new RecipeData(recipeIdEncoded, childRecipe.getString("image"), childRecipe.getString("label"),
                            childRecipe.getString("source"), childRecipe.getDouble("yield"), childRecipe.getDouble("totalTime"),
                            childRecipe.getJSONArray("dietLabels"), childRecipe.getJSONArray("cautions"));
                    myDataSet.add(c1);
                }

                RecyclerAdapter r1= new RecyclerAdapter(myDataSet);
                recyclerView.setAdapter(r1);
            }
        }
        catch (JSONException e)
        {
            Log.e("No results...",url);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
    public void newSearch(View myView)
    {
        Intent myIntent = new Intent(this, list.class);

        if (TextUtils.isEmpty(listSearch.getText()))
            Toast.makeText(getApplicationContext(),"Enter a value!",Toast.LENGTH_SHORT).show();
        else
        {
            String urlSearch = "https://api.edamam.com/search?q=" + listSearch.getText().toString()
                    + "&app_id=384a2360&app_key=1e3c75bdc9c99293fb4ec4a886011e70";
            myIntent.putExtra("urlSearch", urlSearch);
            myIntent.putExtra("itemSearch", listSearch.getText().toString());
            startActivity(myIntent);
        }
    }
    public void goToSearch(View logoClicked)
    {
        Intent myInt = new Intent(this, MainActivity.class);
        startActivity(myInt);
    }
}
