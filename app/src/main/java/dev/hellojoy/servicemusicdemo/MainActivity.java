package dev.hellojoy.servicemusicdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionListener, SongListAdapter.OnSongClickListener{

    private static final String TAG = "MainActivity";

    private SongListAdapter adapter;

    private List<Song> songs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(this)
                .check();

        adapter = new SongListAdapter(songs,this,this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getAudioFiles();
    }



    public void getAudioFiles() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                Song song = new Song();
                song.setSongName(title);
                song.setUri(Uri.parse(url));
//                song.setaudioArtist(artist);
//                modelAudio.setaudioDuration(duration);

                songs.add(song);
                adapter.notifyDataSetChanged();

            } while (cursor.moveToNext());
        }

    }


    @Override
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
        Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

    }

    @Override
    public void onSongClick(Song song) {

        Intent intent = new Intent(this,PlayerService.class);
        intent.putExtra("name", song.getSongName());
        intent.putExtra("uri",song.getUri().toString());

        startService(intent);


        //Toast.makeText(this,song.getSongName() , Toast.LENGTH_SHORT).show();
    }
}