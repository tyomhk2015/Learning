package com.mudgame.mhk.userinterface;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.nio.file.FileSystems;

public class MusicInterface extends Application implements Runnable {

    private String path = FileSystems.getDefault().getPath("").toAbsolutePath() + "/src/com/mudgame/mhk/music/matsuri.mp3";
    private String [] args; // The argument from the main method.
    private Thread thread; // The thread of this class.
    private static MediaPlayer mediaPlayer; // The mediaPlayer to play mp3 formatted music.
    private static boolean musicOn = true; // For telling if the music is on or off.
    private boolean isMusicLoaded = false;
    private double volume = 0.04;
    private int silence = 0;

    public MusicInterface() {
        this.thread = new Thread(this);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Get file from the given path by formatting it in to an identifier.
        Media media = new Media(new File(this.path).toURI().toString());
        // Create a mediaPlayer based on the read mp3 file..
        mediaPlayer = new MediaPlayer(media);
        // Play it as soon as the class has been invoked.
        mediaPlayer.setAutoPlay(true);
        // If the mediaPlayer finished playing the material, take the music process back to zero, beginning.
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        // Set the volume of the music.
        mediaPlayer.setVolume(volume);
        // Let the music BEGIN!
        mediaPlayer.play();
    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public void activateMusic(String[] args){
        this.args = args;
        this.thread.start();
        this.isMusicLoaded = true;
    }

    public void raiseVolume(){
        if(volume == silence){
            musicOn = true;
        }
        volume += 0.04;
        if(volume > 1.00){
            volume = 1.00;
        }
        mediaPlayer.setVolume(volume);
    }

    public void decreaseVolume(){
        volume -= 0.04;
        if(volume < 0.00){
            volume = 0.00;
        }
        mediaPlayer.setVolume(volume);
        if(volume == silence){
            musicOn = false;
        }
    }

    public void pauseMusic(){
        musicOn = false;
        mediaPlayer.setVolume(silence);
    }

    public void resumeMusic(){
        musicOn = true;
        mediaPlayer.setVolume(volume);
    }

    public boolean isMusicLoaded() {
        return isMusicLoaded;
    }

    @Override
    public void run() {
        // Initiate the JavaFX's feature, the MediaPlayer for playing music.
        launch(this.args);
    }
}
