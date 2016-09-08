package com.myxlab.pokemonstore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;


import java.util.List;

/**
 * Created by haslina on 8/15/2016.
 */


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    //List of albums
    List<PokeSnipe> alba;

    public CardAdapter(List<PokeSnipe> alba, Context context){
        super();
        //Getting all the superheroes
        this.alba = alba;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_pokemon_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final PokeSnipe album =  alba.get(position);

        imageLoader = CustomVolleyRequestPokemons.getInstance(context).getImageLoader();
        imageLoader.get(album.getPokeImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(album.getPokeImageUrl(), imageLoader);
        holder.textViewName.setText(album.getTitle());
        holder.textViewRank.setText(album.getTime()+"");
        holder.textViewRank.setVisibility(View.GONE);
        holder.textViewCoords.setText(album.getCoord()+"");

        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
       /* AlbumTrackRetrieve.getInstance().setAlbumName(album.getTitle().toString());
        AlbumTrackRetrieve.getInstance().setAlbumArt(album.getAlbumImageUrl().toString());
        AlbumTrackRetrieve.getInstance().setAlbumID(album.getId());*/
        Toast.makeText(context.getApplicationContext(), album.getTitle().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true;
    }
});




    }

    @Override
    public int getItemCount() {
        return alba.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewRank;
        public TextView textViewCoords;
        public TextView textViewCreatedBy;
        public TextView textViewFirstAppearance;
        public TextView textViewPowers;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewRank= (TextView) itemView.findViewById(R.id.textViewRank);
            textViewCoords= (TextView) itemView.findViewById(R.id.textViewCoord);
        }
    }
}