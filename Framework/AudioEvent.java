package Framework;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Event that plays an audio file when invoked.
 */
public class AudioEvent implements Event {

    private AudioInputStream audioStream;
    private Clip audioClip;
    private String eventName;

    /**
     * Loads an audio file and connects it to an event name.
     *
     * @param eventName the name of the event
     * @param audioFilePath the path to the audio file
     */
    public AudioEvent(String eventName, String audioFilePath) {
        this.eventName = eventName;

        try {
            audioStream = AudioSystem.getAudioInputStream(new File(audioFilePath).getAbsoluteFile());
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the audio file from the beginning.
     *
     * @param eventName the invoked event name
     */
    @Override
    public void invoke(String eventName) {
        if (audioClip == null) {
            return;
        }

        audioClip.stop();
        audioClip.setFramePosition(0);
        audioClip.start();
    }

    /**
     * Checks if this AudioEvent matches the given event name.
     *
     * @param other the event name to compare with
     * @return true if the event names match
     */
    @Override
    public boolean match(String other) {
        return eventName != null && eventName.equals(other);
    }
}