package com.ef.entities;

import java.util.logging.Logger;

public class CommandLineArguments {

	private static final Logger logger = Logger.getLogger(CommandLineArguments.class.getName());
	private String accessLogs;
	private String startDate;
	private String duration;
	private String threshold;

	public CommandLineArguments(String[] args) {
		if (args != null && args.length == 4) {
			this.accessLogs = args[0];
			this.startDate = args[1];
			this.duration = args[2];
			this.threshold = args[3];
		} else if (args != null && args.length == 3) {
			this.startDate = args[0];
			this.duration = args[1];
			this.threshold = args[2];
		} else {
			throw new IllegalArgumentException("you must have 3 or 4 arguments (accessLogs, startDate, duration, threshold)");
		}
		logger.info(String.format("command line arguments set... \n %s", this));
	}

	public String getAccessLogs() {
		return accessLogs;
	}

	public void setAccessLogs(String accessLogs) {
		this.accessLogs = accessLogs;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getTreshold() {
		return threshold;
	}

	public void setTreshold(String treshold) {
		this.threshold = treshold;
	}

	@Override
	public String toString() {
		return "CommandLineArguments [accessLogs=" + accessLogs + ", startDate=" + startDate + ", duration=" + duration
				+ ", treshold=" + threshold + "]";
	}

}
