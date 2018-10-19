package com.mikewerzen.alarm.webalarm.alarm;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlarmController
{
	Logger logger = org.apache.logging.log4j.LogManager.getLogger(AlarmController.class);

	public static String songPath;
	public static boolean enabled = true;
	public static Long alarmHour;
	public static Long alarmMinute;
	public static String fileName;

	@Autowired
	public AlarmMediaPlayer mediaPlayer;

	@Autowired TaskScheduler scheduler;

	public void setAlarmHour(long hour)
	{
		AlarmController.alarmHour = hour;

	}

	public void setAlarmMinute(long minute)
	{
		AlarmController.alarmMinute = minute;

	}

	@Scheduled(cron = "0 0/5 * * * ?")
	public void testWake()
	{
		logger.info("Checking whether should alarm.");
		logger.info("Current Time: " + getCurrTime());
		logger.info("Alarm Time: " + getAlarmTime());
		if (alarmHour != null && alarmMinute != null && Math.abs(getCurrTime() - getAlarmTime()) < 5)
		{
			playSong();
		}
	}

	public long getCurrTime()
	{
		return (LocalDateTime.now().getHour() * 60) + LocalDateTime.now().getMinute();
	}

	public long getAlarmTime()
	{
		if (alarmHour != null && alarmMinute != null)
		{
			return (alarmHour * 60) + alarmMinute;
		}
		else
		{
			return Long.MAX_VALUE;
		}
	}

	public void setSong(String fileName)
	{
		AlarmController.fileName = fileName;
		mediaPlayer.setSong(fileName);
	}


	public void playSong()
	{
		if (enabled)
		{
			mediaPlayer.play();
		}
	}

	public void pauseSong()
	{
		mediaPlayer.pause();
	}


	public void stopSong()
	{
		mediaPlayer.stop();
	}

	public void turnOn()
	{
		enabled = true;
	}


	public void turnOff()
	{
		mediaPlayer.stop();
		enabled = false;
	}

}
