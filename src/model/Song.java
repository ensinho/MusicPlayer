package model;

/**
 * Classe que representa uma música.
 */
public class Song {
    private String title;
    private String artist;
    private String album;
    private String genre;

    /**
     * Construtor padrão da classe Song.
     */
    public Song() {

    }

    /**
     * Construtor da classe Song.
     *
     * @param title  título da música
     * @param artist artista da música
     * @param album  álbum da música
     * @param genre  gênero da música
     */
    public Song(String title, String artist, String album, String genre) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

    /**
     * Obtém o título da música.
     *
     * @return título da música
     */
    public String getTitle() {
        return title;
    }

    /**
     * Define o título da música.
     *
     * @param title título da música
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Obtém o artista da música.
     *
     * @return artista da música
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Define o artista da música.
     *
     * @param artist artista da música
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Obtém o álbum da música.
     *
     * @return álbum da música
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Define o álbum da música.
     *
     * @param album álbum da música
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Obtém o gênero da música.
     *
     * @return gênero da música
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Define o gênero da música.
     *
     * @param genre gênero da música
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Retorna uma representação em formato de string da música.
     *
     * @return string representando a música
     */
    @Override
    public String toString() {
        return "Song [title=" + title + ", artist=" + artist + ", album=" + album + ", genre=" + genre + "]";
    }
}
