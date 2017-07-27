package com.tarun.saini.baking_app.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tarun on 22-06-2017.
 */

public class RestManager {


    public RecipeService mRecipeService;
    public static final String BASE_URL="http://d17h27t6h515a5.cloudfront.net/";



    public RecipeService getRecipeService()
    {
        if(mRecipeService ==null)
        {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRecipeService =retrofit.create(RecipeService.class);
        }
        return mRecipeService;
    }
}
