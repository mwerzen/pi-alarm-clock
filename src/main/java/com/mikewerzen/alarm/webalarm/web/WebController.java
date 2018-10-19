package com.mikewerzen.alarm.webalarm.web;


import com.mikewerzen.alarm.webalarm.alarm.AlarmController;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class WebController
{
	Logger logger = org.apache.logging.log4j.LogManager.getLogger(WebController.class);

	@Autowired
	private AlarmController alarmController;

	@Autowired
	private TaskScheduler scheduler;

	@GetMapping("/")
	public String index(Map<String, Object> model)
	{

		if (alarmController.alarmHour != null && AlarmController.alarmMinute != null)
		{
			model.put("time", AlarmController.alarmHour + ":" + AlarmController.alarmMinute);
		}
		else
		{
			model.put("time", "Not Set");
		}
		logger.info("Alarm: " + AlarmController.alarmHour + ":" + AlarmController.alarmMinute);

		model.put("song", AlarmController.fileName);
		model.put("songs", getSongNames());
		model.put("status", AlarmController.enabled);

		return "index";

	}

	@GetMapping("/song/{fileName}")
	public String selectSong(@PathVariable String fileName, Map<String, Object> model)
	{
		alarmController.setSong(fileName);

		return index(model);
	}

	@GetMapping("/hour/{hour}")
	public String setHour(@PathVariable long hour, Map<String, Object> model)
	{
		logger.info("Setting hour to: " + hour);
		alarmController.setAlarmHour(hour);

		return index(model);
	}

	@GetMapping("/minute/{minute}")
	public String setMinute(@PathVariable long minute, Map<String, Object> model)
	{
		alarmController.setAlarmMinute(minute);

		return index(model);
	}

	@GetMapping("/test")
	public String test(Map<String, Object> model)
	{
		alarmController.playSong();

		return index(model);
	}

	@GetMapping("/sleep")
	public String sleep(Map<String, Object> model)
	{
		alarmController.pauseSong();
		scheduler.schedule(new Runnable()
		{
			@Override public void run()
			{
				alarmController.playSong();
			}
		}, Instant.ofEpochMilli(System.currentTimeMillis() + (15 * 60 * 1000)));

		logger.info("Sleeping for 15 minutes");

		return index(model);
	}

	@GetMapping("/stop")
	public String stop(Map<String, Object> model)
	{
		alarmController.stopSong();

		return index(model);
	}

	@GetMapping("/turnOn")
	public String turnon(Map<String, Object> model)
	{
		alarmController.turnOn();

		return index(model);
	}

	@GetMapping("/turnOff")
	public String turnoff(Map<String, Object> model)
	{
		alarmController.turnOff();

		return index(model);
	}

	private List<String> getSongNames()
	{
		File songDir = new File(".");

		List<String> songs = new ArrayList<>();
		for (File file : songDir.listFiles())
		{
			String fileName = file.getName();
			if (!fileName.endsWith(".jar"))
			{
				songs.add(fileName);
			}
		}

		Collections.sort(songs);

		return songs;
	}
}
