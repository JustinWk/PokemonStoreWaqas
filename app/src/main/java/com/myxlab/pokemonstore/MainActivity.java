package com.myxlab.pokemonstore;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView tvDetails;
    EditText etPNAME, etPPlace;
    Button btADD, btFIND, bttransfer;
    ListView lv;
    List<Pokemon> pokemonList;
    ArrayAdapter pokemonListAdapter;
    PokemonDBHandler pokemonDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tvDetails = (TextView) findViewById(R.id.tvShowRecords);
        etPNAME = (EditText) findViewById(R.id.editTextPName);
        etPPlace = (EditText) findViewById(R.id.editTextPPlace);
        btADD = (Button) findViewById(R.id.buttonADD);
        btFIND = (Button) findViewById(R.id.buttonFind);
        bttransfer = (Button) findViewById(R.id.buttontransfer);
        lv = (ListView) findViewById(R.id.lvShowRecords);

        pokemonDBHandler = new PokemonDBHandler(this, PokemonDBHandler.DATABASE_NAME, null, PokemonDBHandler.DATABASE_VERSION);

        pokemonDBHandler.addPokemon(new Pokemon("Pikachu", "Ampang Point"));
        pokemonDBHandler.addPokemon(new Pokemon("Dragonite", "Kampung Pandan"));
        pokemonDBHandler.addPokemon(new Pokemon("Snorlax", "Ampang Point"));
        pokemonDBHandler.addPokemon(new Pokemon("Pinsir", "IOI"));
        pokemonDBHandler.addPokemon(new Pokemon("Ratata", "Ampang Point"));
        pokemonDBHandler.addPokemon(new Pokemon("Pidgey", "Desa Pandan"));

        bttransfer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                pokemonDBHandler.transferAllPokemon();
                Toast.makeText(MainActivity.this,"transferd all pokemons", Toast.LENGTH_SHORT).show();
                refreshList();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String valueClicked = (String) lv.getItemAtPosition(i);

                Pokemon pokemon =
                        pokemonDBHandler.findPokemonByName(valueClicked);
                if (pokemon != null) {
                    Snackbar.make(findViewById(android.R.id.content), "Pokemon Found: ID-" + String.valueOf(pokemon.getId()
                            +" At -"+ pokemon.getPokemonPlaceName() ),Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "No Match Found",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        refreshList();
    }

    private void refreshList() {
        PokemonDBHandler pokemonDBHandler = new PokemonDBHandler(this, PokemonDBHandler.DATABASE_NAME, null, PokemonDBHandler.DATABASE_VERSION);

        //fetch all pokemons from the database
        pokemonList = pokemonDBHandler.findAllPokemons();

        List listOfNames = new ArrayList();
        for (int i = 0; i < pokemonList.size(); i++){
            listOfNames.add(pokemonList.get(i).getPokemonName());
        }

        pokemonListAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfNames);
        lv.setAdapter(pokemonListAdapter);

    }

    public void addPokemon(View view){
        PokemonDBHandler pokemonDBHandler = new PokemonDBHandler(this, PokemonDBHandler.DATABASE_NAME, null, PokemonDBHandler.DATABASE_VERSION);
        Pokemon pokemon = new Pokemon(etPNAME.getText().toString(), etPPlace.getText().toString());
        pokemonDBHandler.addPokemon(pokemon);
        etPPlace.setText("");
        etPNAME.setText("");
        refreshList();
    }

    public void transferPokemon(View v) {
        PokemonDBHandler dbHandler = new PokemonDBHandler(this, null,
                null, PokemonDBHandler.DATABASE_VERSION);
        boolean result = dbHandler.transferPokemon(
                etPNAME.getText().toString());
        if (result) {
            Toast.makeText(MainActivity.this,etPNAME.getText().toString() +  "  Transferring ", Toast.LENGTH_SHORT).show();
            etPNAME.setText("");
            etPPlace.setText("");
        } else
            Toast.makeText(MainActivity.this,etPNAME.getText().toString() +  "  Transferring", Toast.LENGTH_SHORT).show();
        refreshList();
    }

    public void findPokemon(View v) {
        /*PokemonDBHandler dbHandler = new PokemonDBHandler(this, null, null, PokemonDBHandler.DATABASE_VERSION);
    Pokemon pokemon =
            dbHandler.findPokemonByName(etPNAME.getText().toString());
    if (pokemon != null) {
        Snackbar.make(findViewById(android.R.id.content), "Pokemon Found: ID-" + String.valueOf(pokemon.getId()
                +" At -"+ pokemon.getPokemonPlaceName() ),Snackbar.LENGTH_LONG).show();
    } else {
        Snackbar.make(findViewById(android.R.id.content), "No Match Found",Snackbar.LENGTH_SHORT).show();
    }
    refreshList();*/
        Intent i = new Intent(MainActivity.this, PokeSnipersActivity.class);
        startActivity(i);
}






}
