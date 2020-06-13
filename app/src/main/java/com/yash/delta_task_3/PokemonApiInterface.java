package com.yash.delta_task_3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApiInterface {

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") String name);

    @GET("type/{id}")
    Call<pokemon_of_types_name> getTypes(@Path("id") String id);

    @GET("pokedex/{id}")
    Call<Location> get_location(@Path("id") int id);

    @GET("item")
    Call<item> get_item(@Query("limit") int id);

    @GET("location")
    Call<location_not_region> getLocation(@Query("limit") int id);

    @GET(".")
    Call<evolution_chain> get_pokemon_species();

    @GET
    Call<Species> get_evolution_chain();

}
