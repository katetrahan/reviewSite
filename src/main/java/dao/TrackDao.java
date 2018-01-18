package dao;


import models.Track;

import java.util.List;

public interface TrackDao {

    //create
    void add (Track track);

    //read
    List<Track> getAll();

    Track findTrackById(int id);

    //update
    void update(int id, String title, int artistId);

    //delete
    void deleteById(int id);
    void clearAllTracks();




}
