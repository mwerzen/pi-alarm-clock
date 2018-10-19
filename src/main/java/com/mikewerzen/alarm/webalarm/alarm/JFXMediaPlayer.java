package com.mikewerzen.alarm.webalarm.alarm;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.springframework.stereotype.Service;

import java.io.File;

public class JFXMediaPlayer implements AlarmMediaPlayer
{
//	static{
//		JFXPanel fxPanel = new JFXPanel();
//	}


	private static MediaPlayer mediaPlayer;

	@Override public void setSong(String fileName)
	{
		if(mediaPlayer != null)
		{
			mediaPlayer.stop();
			mediaPlayer.dispose();
		}

		Media song = new Media(new File(fileName).toURI().toString());
		mediaPlayer = new MediaPlayer(song);
		mediaPlayer.setOnEndOfMedia(new Runnable()
		{
			@Override public void run()
			{
				mediaPlayer.seek(Duration.ZERO);
			}
		});
	}

	@Override public void play()
	{
		mediaPlayer.play();
	}

	@Override public void stop()
	{
		mediaPlayer.stop();
	}

	@Override public void pause()
	{
		mediaPlayer.pause();
	}
}
