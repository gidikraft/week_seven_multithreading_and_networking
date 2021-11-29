package com.example.week_seven

import adapters.PokemonListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import common.Common
import common.ItemOffsetDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit.PokemonListInterface
import retrofit.RetrofitClient
import retrofit2.Retrofit

class PokemonListFragment : Fragment() {
    private lateinit var pokemonRecyclerView: RecyclerView
    internal var compositeDisposable = CompositeDisposable()
    internal var iPokemonList: PokemonListInterface

    init {
        val retrofit: Retrofit = RetrofitClient.instance
        iPokemonList = retrofit.create(PokemonListInterface::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView: View = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        pokemonRecyclerView = itemView.findViewById(R.id.pokemon_recyclerview)
        pokemonRecyclerView.setHasFixedSize(true)
        pokemonRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        val itemDecoration = ItemOffsetDecoration(requireActivity(), R.dimen.spacing)
        pokemonRecyclerView.addItemDecoration(itemDecoration)

        fetchData()

        return itemView
    }

    private fun fetchData() {
        compositeDisposable.add(iPokemonList.listPokemon
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{  pokemondex ->
                Common.pokemonList = pokemondex.pokemon!!
                val adapter = PokemonListAdapter(requireActivity(),Common.pokemonList)

                pokemonRecyclerView.adapter = adapter
            }
        )
    }

}