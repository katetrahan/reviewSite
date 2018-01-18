package dao;


import models.Artist;
import models.Track;

import java.util.List;

public interface ArtistDao {

    //create
    void add(Artist artist);

    //read
    List<Artist> getAll();
    List<Track> getAllTracksByArtists(int artistId);

    Artist findById(int id);

    //update
    void update(int id, String name);

    //delete
    void deleteById(int id);
    void clearAllArtists();


}
