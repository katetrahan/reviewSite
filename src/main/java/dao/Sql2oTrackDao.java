package dao;


import models.Track;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oTrackDao implements TrackDao {

    private final Sql2o sql2o;
    public Sql2oTrackDao(Sql2o sql2o) { this.sql2o = sql2o;}

    @Override
    public void add(Track track) {
        String sql = "INSERT INTO tracks(title, genre, length, artistId) VALUES (:title, :genre, :length, :artistId)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(track)
                    .executeUpdate()
                    .getKey();
            track.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public List<Track> getAll(){
        String sql = "SELECT * FROM tracks";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Track.class);
        }
    }

    @Override
    public Track findTrackById(int id) {
        String sql = "SELECT * FROM tracks WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Track.class);
        }
    }

    @Override
    public void update(int id, String newTitle, int artistId) {
        String sql = "UPDATE tracks SET title = :title WHERE id = :id";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("title", newTitle)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from tracks WHERE id= :id";
        try(Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllTracks() {
        String sql = "DELETE from tracks";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
