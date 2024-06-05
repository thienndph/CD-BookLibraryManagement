package Model;

public class CD extends Item {
    private String artist;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        if (artist == null || artist.trim().isEmpty()) {
            throw new IllegalArgumentException("Nghệ sĩ không được để trống.");
        }
        this.artist = artist;
    }

    public CD(String id, String title, String publisher, int year, boolean status, String artist) {
        super(id, title, publisher, year, status);
        setArtist(artist);
    }

    @Override
    public String toString() {
        return "CD{" +
                "Nghệ sĩ='" + artist + " " + super.toString();
    }
}
