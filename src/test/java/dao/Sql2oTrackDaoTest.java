package dao;

import models.Track;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;


public class Sql2oTrackDaoTest {

    private Sql2oTrackDao trackDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        trackDao = new Sql2oTrackDao(sql2o);

        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    public Track setupNewTrack() {
        return new Track("A song", "Music", 3.10);
    }

    @Test
    public void newTrack_generatesUniqueId_1() throws Exception {
        Track track = setupNewTrack();
        int originalTrackId = track.getId();
        trackDao.add(track);
        assertNotEquals(originalTrackId, track.getId());
    }

    @Test
    public void getAll_returnsAllTrackObjectsAddedToDB_true() throws Exception {
        Track track = setupNewTrack();
        Track nextTrack = setupNewTrack();
        Track notAddedTrack = setupNewTrack();
        trackDao.add(track);
        trackDao.add(nextTrack);
        assertEquals(2,trackDao.getAll().size());
    }

}