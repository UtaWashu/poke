package com.example.pokemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView; import android.widget.TextView; import android.widget.Toast; import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call; import retrofit2.Callback; import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private TextView heightTextView;
    private TextView weightTextView;

    private TextView abilitiesTextView;
    private TextView typeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_details);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        heightTextView = findViewById(R.id.heightTextView);
        weightTextView = findViewById(R.id.weightTextView);
        typeTextView = findViewById(R.id.typeTextView);
        abilitiesTextView = findViewById(R.id.abilitiesTextView);

        // Получение данных о покемоне из Intent
        String pokemonName = getIntent().getStringExtra("pokemonName");
        String pokemonImageUrl = getIntent().getStringExtra("pokemonImageUrl");
        String pokemonUrl = getIntent().getStringExtra("pokemonUrl");

        setTitle(pokemonName);
        textView.setText(pokemonName);

        Glide.with(this)
                .load(pokemonImageUrl)
                .into(imageView);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokeAPI pokeAPI = retrofit.create(PokeAPI.class);
        Call<PokemonDetails> call = pokeAPI.getPokemonDetails(pokemonUrl);
        call.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                if (response.isSuccessful()) {
                    PokemonDetails pokemonDetails = response.body();
                    if (pokemonDetails != null) {
                        heightTextView.setText("Height: " + pokemonDetails.getHeight()/10);
                        weightTextView.setText("Weight: " + pokemonDetails.getWeight()/10);

                        List<Type> types = pokemonDetails.getTypes();
                        String pokemonTypes = "Types: ";
                        for (Type type : types) {
                            pokemonTypes += type.getType().getName() + " ";
                        }
                        typeTextView.setText(pokemonTypes);

                        List<Ability> abilities = pokemonDetails.getAbilities();
                        String pokemonAbilities = "Abilities: ";
                        for (Ability ability : abilities) {
                            pokemonAbilities += ability.getAbility().getName() + " ";
                        }
                        abilitiesTextView.setText(pokemonAbilities);

                    } else {
                        Log.e("PokemonDetailsActivity", "Pokemon details are null");
                    }
                } else {
                    Log.e("PokemonDetailsActivity", response.message());
                }
            }

            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {
                Log.e("PokemonDetailsActivity", t.getMessage());
            }
        });

        Button bookmarkButton = findViewById(R.id.bookmarkButton);
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pokemon pokemon = new Pokemon();
                pokemon.setName(pokemonName);
                pokemon.setUrl(pokemonUrl);
                pokemon.setImageUrl(pokemonImageUrl);



                    PokemonSharedPreferences.addBookmark(PokemonDetailsActivity.this, pokemon);
                    Toast.makeText(PokemonDetailsActivity.this, "Bookmark added", Toast.LENGTH_SHORT).show();
                }
        });

        Button removeBookmarkButton = findViewById(R.id.removeBookmarkButton);
        removeBookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pokemon pokemon = new Pokemon();
                pokemon.setName(pokemonName);
                pokemon.setUrl(pokemonUrl);
                pokemon.setImageUrl(pokemonImageUrl);

                PokemonSharedPreferences.removeBookmark(PokemonDetailsActivity.this, pokemon);
                Toast.makeText(PokemonDetailsActivity.this, "Bookmark removed", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
