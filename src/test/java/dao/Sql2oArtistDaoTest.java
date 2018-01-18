package dao;

import models.Artist;
import models.Track;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Sql2o;


import org.sql2o.Connection;

import static org.junit.Assert.*;


public class Sql2oArtistDaoTest {

    private Sql2oArtistDao artistDao;
    private Sql2oTrackDao trackDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        artistDao = new Sql2oArtistDao(sql2o); //ignore me for now
        trackDao = new Sql2oTrackDao(sql2o);

        //keep connection open through entire test so it does not get erased.
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Artist setupNewArtist() {
        return new Artist("The Artist");
    }

    @Test
    public void newArtist_generatesUniqueId_1() throws Exception {
        Artist artist = setupNewArtist();
        int originalArtistId = artist.getId();
        artistDao.add(artist);
        assertNotEquals(originalArtistId, artist.getId());
    }

    @Test
    public void getAll_returnsAllArtists() throws Exception {
        Artist artist = setupNewArtist();
        Artist nextArtist = setupNewArtist();
        Artist notAddedArtist = setupNewArtist();
        artistDao.add(artist);
        artistDao.add(nextArtist);
        assertEquals(2, artistDao.getAll().size());
    }

    @Test
    public void existingArtistsCanBeFoundById() throws Exception {
        Artist artist = setupNewArtist();
        int originalArtistId = artist.getId();
        artistDao.add(artist);
        assertNotEquals(originalArtistId, artist.getId());
    }

    @Test
    public void updateChangesArtistName() throws Exception {
        String initialName = "The Artist";
        Artist artist = new Artist(initialName);
        artistDao.add(artist);

        artistDao.update(artist.getId(), "Bono");
        Artist updatedArtist = artistDao.findById(artist.getId());
        assertNotEquals(initialName, updatedArtist.getName());
    }

    @Test
    public void artistCanBeDeleted() throws Exception {
        Artist artist = setupNewArtist();
        artistDao.add(artist);
        artistDao.deleteById(artist.getId());
        assertEquals(0,artistDao.getAll().size());

    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Artist artist = setupNewArtist();
        Artist otherArtist = setupNewArtist();
        artistDao.add(artist);
        artistDao.add(otherArtist);
        int daoSize = artistDao.getAll().size();
        artistDao.clearAllArtists();
        assertEquals(0, artistDao.getAll().size());


    }

    @Test
    public void getAllTracksByArtistsReturnsCorrectly() {
        Artist artist = setupNewArtist();
        artistDao.add(artist);
        int artistId = artist.getId();
        Track newTrack = new Track("song1", "", 3.1, artistId);
        Track secondTrack = new Track("song2", "", 3.1, artistId);
        Track thirdTrack = new Track("song3", "", 3.1, artistId);
        trackDao.add(newTrack);
        trackDao.add(secondTrack);

        assertTrue(artistDao.getAllTracksByArtist(artistId).size()==2);
        assertTrue(artistDao.getAllTracksByArtist(artistId).contains(newTrack));
        assertTrue(artistDao.getAllTracksByArtist(artistId).contains(secondTrack));






    }









}