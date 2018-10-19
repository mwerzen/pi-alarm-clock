package com.mikewerzen.alarm.webalarm.alarm;

import org.springframework.stereotype.Service;
import org.thymeleaf.exceptions.TemplateEngineException;

import java.io.File;

@Service
public class FFPlayMediaPlayer implements AlarmMediaPlayer
{

	private String fileName;

	private Process ffplayProcess;

	@Override public void setSong(String fileName)
	{
		if(ffplayProcess != null)
		{
			stop();
		}

		File file = new File(fileName);
		this.fileName = file.getAbsolutePath();
	}

	@Override public void play()
	{
		if(ffplayProcess != null)
		{
			stop();
		}

		String[] command = new String[] {"ffplay", "-loop", "9999", "-nodisp", fileName};
		try
		{
			ffplayProcess = new ProcessBuilder().command(command).start();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override public void stop()
	{
		if (ffplayProcess != null)
		{
			ffplayProcess.destroy();
			ffplayProcess = null;
		}
	}

	@Override public void pause()
	{
		stop();
	}
}
