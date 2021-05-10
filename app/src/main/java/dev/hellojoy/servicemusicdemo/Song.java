package dev.hellojoy.servicemusicdemo;

import android.net.Uri;

public class Song {

    private String songName;
    private String songPath;
    private Uri uri;


    public Song(){

    }

    public Song(String songName, String songPath) {
        this.songName = songName;
        this.songPath = songPath;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongPath() {
        return songPath;
    }

    public void setSongPath(String songPath) {
        this.songPath = songPath;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
