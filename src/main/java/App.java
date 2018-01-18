import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xpath.internal.operations.Mod;
import dao.Sql2oArtistDao;
import dao.Sql2oTrackDao;
import models.Artist;
import models.Track;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static java.lang.Double.parseDouble;
import static spark.Spark.*;




public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oTrackDao trackDao = new Sql2oTrackDao(sql2o);
        Sql2oArtistDao artistDao = new Sql2oArtistDao(sql2o);

        // homepage
        get("/",(request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            List<Track> allTracks = trackDao.getAll();
            model.put("tracks", allTracks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        // /tracks/deleteAll
        get ("/tracks/deleteAll", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Track> allTracks = trackDao.getAll();
            model.put("track", allTracks);
            trackDao.clearAllTracks();

            return new ModelAndView(model, "delete.hbs");
        }, new HandlebarsTemplateEngine());

//         /artist/deleteAll
        get("/artists/deleteAll", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);
            artistDao.clearAllArtists();
            return new ModelAndView(model, "delete.hbs");
        }, new HandlebarsTemplateEngine());


        // =========tracks======== //

        // /tracks = all tracks
        get("/tracks", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Track> allTracks = trackDao.getAll();
            model.put("tracks", allTracks);
            return new ModelAndView(model, "tracks.hbs");
        }, new HandlebarsTemplateEngine());

        // /tracks/new
        //get form
        get("tracks/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artists", allArtists);

            return new ModelAndView(model, "trackform.hbs");
        }, new HandlebarsTemplateEngine());

        //post form
        post("/tracks/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String title = request.queryParams("title");
            String genre = request.queryParams("genre");
            Double length = parseDouble(request.queryParams("length"));
            int artistId = Integer.parseInt(request.queryParams("artistId"));
            Track newTrack = new Track(title, genre, length, artistId);
            trackDao.add(newTrack);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        // ===========artists========== //

        // /artist = show all artists
        get("/artists", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artist", allArtists);
            return new ModelAndView(model, "artists.hbs");
        }, new HandlebarsTemplateEngine());

        // /artists/new
        //get form
        get("/artists/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Artist> allArtists = artistDao.getAll();
            model.put("artist", allArtists);
            return new ModelAndView(model, "artistform.hbs");
        }, new HandlebarsTemplateEngine());


        //post form
        post("/artists/new", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            Artist newArtist = new Artist (name);
            artistDao.add(newArtist);
            List<Artist> allArtists = artistDao.getAll();
            model.put("artist", allArtists);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //get: show an individual task that is nested in a category
        get("/artists/:artistId/tracks/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTrackToFind = Integer.parseInt(req.params("id"));
            Track foundTrack = trackDao.findTrackById(idOfTrackToFind);
            model.put("track", foundTrack);
            return new ModelAndView(model, "trackdetails.hbs");
        }, new HandlebarsTemplateEngine());

        //delete and individual track
        get("/artists/:artistId/tracks/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTrackToDelete = Integer.parseInt(request.params("id"));
            Track deleteTrack = trackDao.findTrackById(idOfTrackToDelete);
            trackDao.deleteById(idOfTrackToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());










    }





}
