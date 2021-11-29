package retrofit

import io.reactivex.Observable
import model.Pokedex
import retrofit2.http.GET

interface PokemonListInterface {
    @get: GET("pokedex.json")
    val listPokemon: Observable<Pokedex>
}