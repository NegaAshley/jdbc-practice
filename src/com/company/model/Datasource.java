package com.company.model;

import java.sql.*;
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

    public static final String QUERY_ALBUMS_BY_ARIST_START = "SELECT " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME +
            " FROM " + TABLE_ALBUMS + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." +
            COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " WHERE " + TABLE_ARTISTS + "." +
            COLUMN_ARTIST_NAME + " = \"";
    public static final String QUERY_ALBUMS_BY_ARTIST_SORT = " ORDER BY " + TABLE_ALBUMS + "." + COLUMN_ARTIST_NAME +
            " COLLATE NOCASE ";

    public static final String QUERY_ARTIST_FOR_SONG_START = "SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " + TABLE_SONGS + "." + COLUMN_SONG_TRACK + " FROM " +
            TABLE_SONGS + " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS + "." + COLUMN_SONG_ALBUM + " = " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_ID + " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." +
            COLUMN_ALBUM_ARTIST + " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID + " WHERE " + TABLE_SONGS + "." +
            COLUMN_SONG_TITLE + " = \"";

    public static final String QUERY_ARTIST_FOR_SONG_SORT = " ORDER BY " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME +
            ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " COLLATE NOCASE ";

    public static final String TABLE_ARTIST_SONG_VIEW = "artist_list";
    public static final String CREATE_ARTIST_FOR_SONG_VIEW = "CREATE VIEW IF NOT EXISTS " +
            TABLE_ARTIST_SONG_VIEW + " AS SELECT " + TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME +
            ", " + TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + " AS " + COLUMN_SONG_ALBUM + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK + ", " + TABLE_SONGS + "." + COLUMN_SONG_TITLE +
            " FROM " + TABLE_SONGS +
            " INNER JOIN " + TABLE_ALBUMS + " ON " + TABLE_SONGS +
            "." + COLUMN_SONG_ALBUM + " = " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ID +
            " INNER JOIN " + TABLE_ARTISTS + " ON " + TABLE_ALBUMS + "." + COLUMN_ALBUM_ARTIST +
            " = " + TABLE_ARTISTS + "." + COLUMN_ARTIST_ID +
            " ORDER BY " +
            TABLE_ARTISTS + "." + COLUMN_ARTIST_NAME + ", " +
            TABLE_ALBUMS + "." + COLUMN_ALBUM_NAME + ", " +
            TABLE_SONGS + "." + COLUMN_SONG_TRACK;

    public static final String QUERY_VIEW_SONG_INFO = "SELECT " + COLUMN_ARTIST_NAME + ", " + COLUMN_SONG_ALBUM +
            ", " + COLUMN_SONG_TRACK + " FROM " + TABLE_ARTIST_SONG_VIEW + " WHERE " + COLUMN_SONG_TITLE + " = \"";

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

    public ArrayList<String> queryAlbumsForArtist(String artistName, int sortOrder){
        StringBuilder sb = new StringBuilder(QUERY_ALBUMS_BY_ARIST_START);
        sb.append(artistName);
        sb.append("\"");

        if(sortOrder != ORDER_BY_NONE) {
            sb.append(QUERY_ALBUMS_BY_ARTIST_SORT);

            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        System.out.println("SQL statement =" + sb.toString());

        ArrayList<String> albums = new ArrayList<>();

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())){

            while(results.next()){
                albums.add(results.getString(1));
            }

        }catch(SQLException e){
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
        return albums;
    }

    public ArrayList<SongArtist> queryArtistsForSong(String songName, int sortOrder){
        StringBuilder sb = new StringBuilder(QUERY_ARTIST_FOR_SONG_START);
        sb.append(songName);
        sb.append("\"");

        if(sortOrder != ORDER_BY_NONE){
            sb.append(QUERY_ARTIST_FOR_SONG_SORT);
            if(sortOrder == ORDER_BY_DESC){
                sb.append("DESC");
            }else{
                sb.append("ASC");
            }
        }
        System.out.println("SQL Statement: " + sb.toString());

        try(Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(sb.toString())){
            ArrayList<SongArtist> songArtists = new ArrayList<>();

            while(results.next()){
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack((results.getInt(3)));
                songArtists.add(songArtist);
            }
            return songArtists;
        }catch(SQLException e){
            System.out.println("Error in queryArtistsForSong: " + e.getMessage());
            return null;
        }
    }

    public void querySongsMetadata(){
        String sql = "SELECT * FROM " + TABLE_SONGS;

        try(Statement statement = conn.createStatement();
            ResultSet results = statement.executeQuery(sql)
        ){
            ResultSetMetaData meta = results.getMetaData();
            int numColumns = meta.getColumnCount();
            for(int i = 1; i <= numColumns; i++){
                System.out.format("Column %d in the songs table is named %s\n", i, meta.getColumnName(i));
            }
        }catch(SQLException e){
            System.out.println("Error in querySongsMetadata: " + e.getMessage());
        }
    }

    public int getCount(String table){
        String sql = "SELECT COUNT(*) AS count FROM " + table;
        try(Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(sql)){

            int count = results.getInt("count");
            System.out.format("Count = %d\n", count);
            return count;
        }catch(SQLException e){
            System.out.println("Error in getCount: " + e.getMessage());
            return -1;
        }
    }

    public Boolean createViewForSongArtists(){
        try(Statement statement = conn.createStatement()){

            statement.execute(CREATE_ARTIST_FOR_SONG_VIEW);
            return true;

        }catch(SQLException e){
            System.out.println("Error in createViewForSongArtists: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<SongArtist> querySongInfoView(String title){
        StringBuilder sb = new StringBuilder(QUERY_VIEW_SONG_INFO);
        sb.append(title);
        sb.append("\"");

        System.out.println(sb.toString());

        try(Statement statement = conn.createStatement();
        ResultSet results = statement.executeQuery(sb.toString())){
            ArrayList<SongArtist> songArtists = new ArrayList<>();
            while(results.next()){
                SongArtist songArtist = new SongArtist();
                songArtist.setArtistName(results.getString(1));
                songArtist.setAlbumName(results.getString(2));
                songArtist.setTrack(results.getInt(3));
                songArtists.add(songArtist);
            }
            return songArtists;
        }catch(SQLException e){
            System.out.println("Error in querySongInfoView: " + e.getMessage());
            return null;
        }

    }
}
