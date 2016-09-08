package com.myxlab.pokemonstore;

/**
 * Created by haslina on 8/15/2016.
 */
public class PokeSnipe {

    public PokeSnipe() {

    }

    //Data Variables
    private String pokeImageUrl;
    private String title;
    private String time;
    private String coord;


    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public String getPokeImageUrl() {
        return pokeImageUrl;
    }

    public void setPokeImageUrl(String pokeImageUrl) {
        this.pokeImageUrl = pokeImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
