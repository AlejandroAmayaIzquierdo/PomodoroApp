package pomo.util;

import java.net.URISyntaxException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Notification {
	
	private String soundFile;
	private Media sound;
	private MediaPlayer mediaPlayer;
	
	public Notification(String soundFile) {
		try {
			this.soundFile = soundFile;
			
			sound = new Media(getClass().getResource(this.soundFile).toURI().toString());
			
			mediaPlayer = new MediaPlayer(sound);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public void playSound() {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.play();
	}

	
	

	public String getSounfFile() {
		return soundFile;
	}

	public void setSounfFile(String sounfFile) {
		this.soundFile = sounfFile;
	}

	public Media getSound() {
		return sound;
	}

	public void setSound(Media sound) {
		this.sound = sound;
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	
	

}
