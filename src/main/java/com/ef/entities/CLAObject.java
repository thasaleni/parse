package com.ef.entities;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

public class CLAObject {
	private static final Logger logger = Logger.getLogger(CLAObject.class.getName());
	private File accessLogs;
	private Date startDate;
	private String duration;
	private Integer threshold;

	public CLAObject(CommandLineArguments args) {
		try {
			this.accessLogs = StringUtils.isNotBlank(args.getAccessLogs())
					? new File(args.getAccessLogs().split("=")[1])
					: null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
			this.startDate = format.parse(args.getStartDate().split("=")[1]);
			this.duration = args.getDuration().split("=")[1];
			this.threshold = Integer.parseInt(args.getTreshold().split("=")[1]);
			logger.info(String.format("Arguments set \n %s", this));
		} catch (ParseException e) {
			logger.warning(String.format("Error setting command line arguments: %s", e.getMessage()));
		}
	}

	public File getAccessLogs() {
		return accessLogs;
	}

	public void setAccessLogs(File accessLogs) {
		this.accessLogs = accessLogs;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	@Override
	public String toString() {
		return "CLAObject [accessLogs=" + accessLogs + ", startDate=" + startDate + ", duration=" + duration
				+ ", threshold=" + threshold + "]";
	}

}
