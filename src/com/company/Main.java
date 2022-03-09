package com.company;

import com.company.model.Datasource;

import java.sql.SQLException;
import com.company.model.Artist;
import com.company.model.Song;
import com.company.model.Album;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Datasource datasource = new Datasource();
        if(!datasource.open()){
            System.out.println("Can't open datasource!");
            return;
        }

        /*ArrayList<Artist> artists = datasource.queryArtists(Datasource.ORDER_BY_ASC);
        if(artists == null){
            System.out.println("No artists!");
            return;
        }
        for(Artist artist: artists){
            System.out.println("ID = " + artist.getId() + " , Name = " + artist.getName());
        }*/

        /*ArrayList<Song> songs = datasource.querySongs(Datasource.ORDER_BY_ASC);
        if(songs == null){
            System.out.println("No songs!");
            return;
        }
        for(Song song: songs){
            System.out.println("ID = " + song.getId() + ", Track = " + song.getTrack() + ", Title = " + song.getTitle()
                    + ", Album = " + song.getAlbumId());
        }*/

        /*ArrayList<Album> albums = datasource.queryAlbums(Datasource.ORDER_BY_ASC);
        if(albums == null){
            System.out.println("No albums!");
            return;
        }
        for(Album album: albums){
            System.out.println("ID = " + album.getId() + ", Name = " + album.getName() + ", Arist ID = " + album.getArtistId());
        }*/

        datasource.close();
    }
}
