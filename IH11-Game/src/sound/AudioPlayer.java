package sound;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	private Map<String, Clip> clips;

	public AudioPlayer() {

		clips = new HashMap<String, Clip>();
		clips.put("Example", loadClip("/sounds/Example.wav"));

	}

	public void play(String name) {
		stop(name);
		clips.get(name).setFramePosition(0);
		clips.get(name).start();
	}

	public void stop(String name) {
		clips.get(name).stop();
	}

	private Clip loadClip(String path) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sound = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));

			clip.open(sound);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clip;
	}
}
