package communicator.markham.edu.pe.android_tarea1;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonInterface {

    @GET("pokemon/{name}")
    Call<Pokemon> searchPokemon(@Path("name") String name);

}
