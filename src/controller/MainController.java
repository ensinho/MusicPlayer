package controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import model.Song;

/**
 * Controlador da interface principal do player de música.
 */
public class MainController implements Initializable {

    @FXML
    private Label albumLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private ImageView capa;
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton;
    @FXML
    private ComboBox<String> speedBox;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;

    private Media media;
    private MediaPlayer mediaPlayer;

    private File directory;
    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;
    private int[] speeds = { 25, 50, 75, 100, 125, 150, 175, 200 };

    private Timer timer;
    private TimerTask task;
    private Boolean running;

    private Song song;

    /**
     * Método de inicialização do controlador.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        songs = new ArrayList<>();
        directory = new File("music");
        files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                songs.add(file);
            }
        }

        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        songLabel.setText(songs.get(songNumber).getName());
        artistLabel.setText(songs.get(songNumber).getName());

        for (int i = 0; i < speeds.length; i++) {
            speedBox.getItems().add(Integer.toString(speeds[i]) + "%");
        }

        speedBox.setOnAction(this::changeSpeed);

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }

        });
    }

    /**
     * Reproduz a música atual.
     */
    public void playSong() {
        beginTimer();
        changeSpeed(null);
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();

        mediaPlayer.setOnReady(new Runnable() {

            @Override
            public void run() {
                ObservableMap<String, Object> metadata = media.getMetadata();
                capa.setImage((Image) metadata.get("image"));
                songLabel.setText((String) metadata.get("title"));
                artistLabel.setText((String) metadata.get("artist"));
                albumLabel.setText((String) metadata.get("album"));
                // song.setTitle((String) metadata.get("title"));
                // song.setArtist((String) metadata.get("artist"));
                // song.setAlbum((String) metadata.get("album"));
                System.out.println(metadata);
                System.out.println(song);
            }

        });

        mediaPlayer.setOnEndOfMedia(new Runnable() {

            @Override
            public void run() {
                nextSong();
            }

        });
    }

    /**
     * Pausa a reprodução da música atual.
     */
    public void pauseSong() {
        cancelTimer();
        mediaPlayer.pause();
    }

    /**
     * Reinicia a música atual.
     */
    public void resetSong() {
        songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }

    /**
     * Reproduz a música anterior.
     */
    public void previousSong() {
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();

            if (running) {
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playSong();
        } else {
            songNumber = songs.size() - 1;
            mediaPlayer.stop();

            if (running) {
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playSong();
        }
    }

    /**
     * Reproduz a próxima música.
     */
    public void nextSong() {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();

            if (running) {
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playSong();
        } else {
            songNumber = 0;
            mediaPlayer.stop();

            if (running) {
                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playSong();
        }
    }

    /**
     * Altera a velocidade de reprodução da música.
     */
    public void changeSpeed(ActionEvent event) {
        if (speedBox.getValue() == null) {
            mediaPlayer.setRate(1);
        } else {
            mediaPlayer.setRate(
                    Integer.parseInt(speedBox.getValue().substring(0, speedBox.getValue().length() - 1)) * 0.01);
        }
    }

    /**
     * Inicia o temporizador para atualizar a barra de progresso da música.
     */
    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = mediaPlayer.getTotalDuration().toSeconds();
                songProgressBar.setProgress(current / end);

                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * Cancela o temporizador.
     */
    public void cancelTimer() {
        running = false;
        timer.cancel();
    }
}
