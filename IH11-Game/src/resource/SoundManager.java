package resource;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.Clip;

public class SoundManager {

	private static Map<String, Clip> staticClips;
	private static Map<String, Clip> cacheClips;
	
	public static void init(){
		staticClips = new HashMap<>();
		cacheClips = new HashMap<>();
	}
	
	public static void loadStaticClips(){
		
	}
	
	public static void loadStaticClip(String id, String path){
		
	}
	
	public static void loadClipInCache(String id, String path){
		
	}
	
	public static void reloadStaticClip(String id, String path){
		
	}
	
	public static void clearCache(){
		
	}
	
	public static void play(String id){
		play(id, false);
	}
	
	public static void play(String id, boolean looping){
		Clip clip = null;
		if(staticClips.containsKey(id)){
			clip = staticClips.get(id);
		} else if(cacheClips.containsKey(id)){
			clip = cacheClips.get(id);
		} else {
			System.out.println("[SoundManager] Try playing unloaded clip: " + id);
		}
		if(clip != null){
			clip.stop();
			clip.setFramePosition(0);
			if(looping){
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			clip.start();
			System.out.println("[SoundManager] Playing clip: " + id);
		}
	}
	
	public static void stop(String id){
		if(staticClips.containsKey(id)){
			staticClips.get(id).stop();
		} else if(cacheClips.containsKey(id)){
			cacheClips.get(id).stop();
		} else {
			System.out.println("[SoundManager] Try stoping unloaded clip: " + id);
		}
	}
	
	public static void stopAll(String id){
		
	}
	
}
