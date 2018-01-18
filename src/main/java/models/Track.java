package models;

public class Track {
    private String title;
    private String genre;
    private Double length;
    private int id;

    public Track(String title, String genre, Double length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (id != track.id) return false;
        if (!title.equals(track.title)) return false;
        if (genre != null ? !genre.equals(track.genre) : track.genre != null) return false;
        return length != null ? length.equals(track.length) : track.length == null;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }
}