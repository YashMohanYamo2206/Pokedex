package com.yash.deltatask3;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonApi {

    public static final String BASE_URL ="https://pokeapi.co/api/v2/";
    public static final String BASE_URL_TYPES ="https://pokeapi.co/api/v2/type";
    private static Retrofit retrofit;

    public static Retrofit getApi(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getApi_types(String type){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_TYPES+"/"+type)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
