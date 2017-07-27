package com.tarun.saini.baking_app.Network;

import com.google.gson.JsonArray;
import com.tarun.saini.baking_app.Model.Ingredient;
import com.tarun.saini.baking_app.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Tarun on 22-06-2017.
 */

public interface RecipeService {

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getAllRecipes();
}
