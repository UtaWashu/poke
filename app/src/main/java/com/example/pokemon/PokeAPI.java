package com.example.pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PokeAPI {
    @GET("pokemon")
    Call<PokemonResponse> getPokemons(@Query("limit") int limit, @Query("offset") int offset);

    @GET
    Call<PokemonDetails> getPokemonDetails(@Url String url);

}
