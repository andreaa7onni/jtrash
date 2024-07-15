package controller;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;

/**
 * The type Audio manager.
 * This class is used to manage the sounds of the application.
 */
public class AudioManager {

    private static AudioManager instance;

    private AudioManager() {

    }

    /**
     * Gets instance.
     *
     * @return the instance of the AudioManager class
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Play.
     * Method to Play sound.
     *
     * @param filename the filename contening the path of wav sound.
     */
    public void play(String filename) {

        try {
            URL a = getClass().getResource(filename);
            String b = a.getPath().replace("%c3%a0", "à");
            InputStream in = new BufferedInputStream(new FileInputStream(b));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        }
    }
}
