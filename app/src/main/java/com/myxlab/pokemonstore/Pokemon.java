package com.myxlab.pokemonstore;

/**
 * Created by haslina on 9/7/2016.
 */
public class Pokemon {

    private int id;
    private String pokemonName;
    private String pokemonPlaceName;

    public Pokemon(int id, String pokemonPlaceName, String pokemonName) {
        this.id = id;
        this.pokemonPlaceName = pokemonPlaceName;
        this.pokemonName = pokemonName;
    }


    public Pokemon(String pokemonName, String pokemonPlaceName) {
        this.pokemonName = pokemonName;
        this.pokemonPlaceName = pokemonPlaceName;
    }

    public Pokemon(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public String getPokemonPlaceName() {
        return pokemonPlaceName;
    }

    public void setPokemonPlaceName(String pokemonPlaceName) {
        this.pokemonPlaceName = pokemonPlaceName;
    }

    @Override
    public String toString() {
        return "Pokemon id = " + id + ", Name = "+ pokemonName + ", Place =  "+
                pokemonPlaceName +"\n";
    }

}
