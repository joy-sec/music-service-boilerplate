package dev.hellojoy.servicemusicdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.MyViewHolder> {


    private List<Song> songList;
    private Context context;
    OnSongClickListener onSongClickListener;

    public SongListAdapter(List<Song> songList, Context context, OnSongClickListener onSongClickListener) {
        this.songList = songList;
        this.context = context;
        this.onSongClickListener = onSongClickListener;
    }

    interface OnSongClickListener{
        void onSongClick(Song song);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.tvSongName.setText(song.getSongName());

        holder.tvSongName.setOnClickListener(view -> onSongClickListener.onSongClick(song));

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvSongName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongName = itemView.findViewById(R.id.tv_song_name);
        }
    }


}
