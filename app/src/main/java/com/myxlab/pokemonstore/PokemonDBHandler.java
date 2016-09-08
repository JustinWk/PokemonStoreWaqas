package com.myxlab.pokemonstore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by haslina on 9/7/2016.
 */
public class PokemonDBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "POKEMONSDB.db";
    public static final String DATABASE_TABLE_POKEMONS = "pokemons";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "pokemon_name";
    public static final String COLUMN_PLACE = "pokemon_place";
    public static final String CREATE_TABLE_POKEMONS = "CREATE TABLE " + DATABASE_TABLE_POKEMONS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT, " + COLUMN_PLACE + " TEXT" + ")";
    public static final String[] COLUMNS = {COLUMN_ID, COLUMN_NAME, COLUMN_PLACE};
    public static final String TAG = "PokemonDBHandler";


    public PokemonDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        Log.e(TAG,"PokemonDBHandler");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_POKEMONS);
        Log.e(TAG,"Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_POKEMONS);
        onCreate(db);
        Log.e(TAG,"onUpdate");
    }

    // ADD C-R-U-D METHODS BELOW TO PERFORM FUNCTIONALITY OVER DATABASE

    //method to add data to our table
    public void addPokemon(Pokemon pokemonGUY) {
        Log.e(TAG,"addPokemon");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, pokemonGUY.getPokemonName());
        contentValues.put(COLUMN_PLACE, pokemonGUY.getPokemonPlaceName());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(DATABASE_TABLE_POKEMONS, null, contentValues);
        db.close();

    }

    //method to transfer a pokemon by its name
    public boolean transferPokemon(String name) {
        Log.e(TAG,"transferPokemon");
        boolean result = false;
        String querytransferBook = "Select * From " + DATABASE_TABLE_POKEMONS + " WHERE " + COLUMN_NAME + " = \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(querytransferBook, null);
        Pokemon pokemon = new Pokemon();
        if (cursor.moveToFirst()) {
            pokemon.setId(Integer.parseInt(cursor.getString(0)));
            db.delete(DATABASE_TABLE_POKEMONS, COLUMN_ID + " = ?", new String[]{String.valueOf(pokemon.getId())});
            cursor.close();
            result = true;

        }
        return result;
    }

    //method to transfer all pokemons
    public void transferAllPokemon() {
        Log.e(TAG,"onUpdate");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE_POKEMONS, null, null);
    }


    //method to find a pokemon by its name
    public Pokemon findPokemonByName(String pokemonName) {
        Log.e(TAG,"findPokemonByName");
        String queryFindBook = "Select * From " + DATABASE_TABLE_POKEMONS + " WHERE " + COLUMN_NAME + " = \"" + pokemonName + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryFindBook, null);
        Pokemon pokemon = new Pokemon();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            pokemon.setId(Integer.parseInt(cursor.getString(0)));
            pokemon.setPokemonName(cursor.getString(1));
            pokemon.setPokemonPlaceName(cursor.getString(2));
            cursor.close();
        } else {
            pokemon = null;

        }
        db.close();
        return pokemon;

    }

    //method to find all the pokemons
    public List<Pokemon> findAllPokemons() {
        Log.e(TAG,"findAllPokemons");
        //create a list of pokemons
        List<Pokemon> pokemons = new LinkedList<Pokemon>();
        //select the book query
        String query = "SELECT * FROM " + DATABASE_TABLE_POKEMONS;
        //get reference to the pokemons database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //fetch all the data
        Pokemon pokemon = null;
        if (cursor.moveToFirst()) {
            do {
                pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(cursor.getString(0)));
                pokemon.setPokemonName(cursor.getString(1));
                pokemon.setPokemonPlaceName(cursor.getString(2));
                // Add pokemon to the list of POKEMONS
                pokemons.add(pokemon);
            } while (cursor.moveToNext());
        }

        return pokemons;
    }

    public int updatePokemonInfo(Pokemon pokemon) {
        Log.e(TAG,"updatePokemonInfo");

    //get reference to  the Pokemon Database
        SQLiteDatabase db = this.getWritableDatabase();

        //create values to be inserted or edited
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, pokemon.getPokemonName());
        contentValues.put(COLUMN_PLACE, pokemon.getPokemonPlaceName());

        //update our database
        int updatedID = db.update(DATABASE_TABLE_POKEMONS, contentValues, COLUMN_ID + " = ? ", new String[]{String.valueOf(pokemon.getId())} );


        return updatedID;

    }












}
