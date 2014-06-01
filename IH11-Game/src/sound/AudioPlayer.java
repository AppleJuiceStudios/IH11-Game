package sound;

import java.io.BufferedInputStream;
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

	public final static SoundFile EXAMPLE = new SoundFile("Example", "/sounds/Example.wav");
	public final static SoundFile HIT = new SoundFile("Hit", "/sounds/Hit.wav");
	public final static SoundFile ORB = new SoundFile("Orb", "/sounds/orb.wav");
	public final static SoundFile JUMP = new SoundFile("Jump", "/sounds/Jump.wav");
	public final static SoundFile WIN = new SoundFile("Win", "/sounds/AirHorn.wav");

	public AudioPlayer() {
		clips = new HashMap<String, Clip>();
	}

	public void play(SoundFile sound) {
		if (clips.containsKey(sound.name)) {
			stop(sound);
			clips.get(sound.name).setFramePosition(0);
			clips.get(sound.name).start();
		}
	}

	public void stop(SoundFile sound) {
		clips.get(sound.name).stop();
	}

	public void load(SoundFile sound) {
		clips.put(sound.name, loadClip(sound.path));
	}

	private Clip loadClip(String path) {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream sound = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream(path)));
			clip.open(sound);
			sound.close();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return clip;
	}

	public void close() {
		for (String key : clips.keySet()) {
			clips.get(key).close();
		}
	}
}
