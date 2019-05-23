package communicator.markham.edu.pe.android_tarea1;

import android.media.Image;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView tvName;
    Button btSearch;
    TextInputEditText etPokemon;
    ImageView ivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        etPokemon = findViewById(R.id.etPokemon);
        btSearch = findViewById(R.id.btSearch);
        ivPhoto = findViewById(R.id.ivPhoto);

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baseUrl = "https://pokeapi.co/api/v2/";
                String name = etPokemon.getText().toString();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                PokemonInterface pokemonInterface = retrofit.create(PokemonInterface.class);
                Call<Pokemon> methodSearch = pokemonInterface.searchPokemon(name);

                methodSearch.enqueue(new Callback<Pokemon>() {
                    @Override
                    public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                        if (response.isSuccessful()) {
                            Pokemon pokemon = response.body();
                            tvName.setText(pokemon.getName());
                            Glide.with(MainActivity.this)
                                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getId() + ".png")
                                    .into(ivPhoto);
                        } else {
                            tvName.setText("Pokemon no encontrado");

                        }
                    }

                    @Override
                    public void onFailure(Call<Pokemon> call, Throwable t) {
                        tvName.setText("Error en la busqueda");
                        Log.e("PokemonAppError", t.toString());
                    }
                });
            }
        });


    }
}
