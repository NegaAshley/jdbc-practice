package com.company.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Datasource {
    public static final String DB_NAME = "music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\amx8198\\Documents\\Web Dev\\SQLite\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1; //Adding indexes for performance
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int  INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int  INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;

    private Connection conn;

    public boolean open(){
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            return true;
        }catch(SQLException e){
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }catch(SQLException e){
            System.out.println("Couldn't close connection: "+  e.getMessage());
        }
    }

    public void printTableAlbums() throws SQLException{
        Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_ALBUMS);
        while(results.next()){
            System.out.println(results.getInt(COLUMN_ALBUM_ID) + " " +
                    results.getString(COLUMN_ALBUM_NAME) + " " +
                    results.getInt(COLUMN_ALBUM_ARTIST));
        }

        results.close();
        statement.close();
    }

    public ArrayList<Artist> queryArtists(int sortOrder){
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if(sortOrder != ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if(sortOrder == ORDER_BY_DESC){
                sb.append("DESC");
            }else{
                sb.append("ASC");
            }
        }

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString())){


            ArrayList<Artist> artists = new ArrayList<>();

            while (results.next()) {
                Artist artist = new Artist();
                artist.setId(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }

            return artists;

        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Song> querySongs(int sortOrder){
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_SONGS);
        if(sortOrder != ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(COLUMN_SONG_TITLE);
            sb.append(" COLLATE NOCASE ");
            if(sortOrder == ORDER_BY_DESC){
                sb.append("DESC");
            }else{
                sb.append("ASC");
            }
        }

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString())){


            ArrayList<Song> songs = new ArrayList<>();

            while (results.next()) {
                Song song = new Song();
                song.setId(results.getInt(INDEX_SONG_ID));
                song.setTrack(results.getInt(INDEX_SONG_TRACK));
                song.setTitle(results.getString(INDEX_SONG_TITLE));
                song.setAlbumId(results.getInt(INDEX_SONG_ALBUM));
                songs.add(song);
            }

            return songs;

        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Album> queryAlbums(int sortOrder){
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ALBUMS);
        if(sortOrder != ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ALBUM_NAME);
            sb.append(" COLLATE NOCASE ");
            if(sortOrder == ORDER_BY_DESC){
                sb.append("DESC");
            }else{
                sb.append("ASC");
            }
        }

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sb.toString())){


            ArrayList<Album> albums = new ArrayList<>();

            while (results.next()) {
                Album album = new Album();
                album.setId(results.getInt(INDEX_ALBUM_ID));
                album.setName(results.getString(INDEX_ALBUM_NAME));
                album.setArtistId(results.getInt(INDEX_ALBUM_ARTIST));
                albums.add(album);
            }

            return albums;

        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }
}
