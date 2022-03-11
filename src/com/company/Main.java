package com.company;

import com.company.model.*;

import java.sql.SQLException;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Scanner;

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

        /*ArrayList<String> albumsForArtist = datasource.queryAlbumsForArtist("Pink Floyd", Datasource.ORDER_BY_ASC);

        for(String album: albumsForArtist){
            System.out.println(album);
        }*/

        /*ArrayList<SongArtist> songArtists = datasource.queryArtistsForSong("Heartless", Datasource.ORDER_BY_ASC);
        if(songArtists == null){
            System.out.println("Couldn't find the artist for the song");
            return;
        }

        for(SongArtist artist: songArtists){
            System.out.println("Artist name = " + artist.getArtistName() + " Album name = " + artist.getAlbumName() +
                    " Track = " + artist.getTrack());
        }*/

        //datasource.querySongsMetadata();
        /*int count = datasource.getCount(Datasource.TABLE_SONGS);
        System.out.println("Number of songs: "  + count);*/

        //datasource.createViewForSongArtists();


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a song title: ");
        String title = scanner.nextLine();

        ArrayList<SongArtist> songArtists;

        songArtists = datasource.querySongInfoView(title);
        if(songArtists.isEmpty()){
            System.out.println("Couldn't find the artist for this song.");
            return;
        }

        for(SongArtist artist: songArtists){
            System.out.println("From View - Arist name = " + artist.getArtistName() + " Album name = " +
                    artist.getAlbumName() + " Track number = " + artist.getTrack());
        }

        datasource.close();
    }
}
