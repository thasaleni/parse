package com.ef.services;

import java.beans.PropertyEditor;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.ef.entities.CLAObject;
import com.ef.entities.CommandLineArguments;
import com.ef.entities.Log;
import com.ef.repositories.LogRepository;
import com.ef.services.LogService;

@Service
public class LogService {
	private static final Logger logger = Logger.getLogger(LogService.class.getName());
	private LogRepository logRepository;
	private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	@Autowired
	public LogService(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	public List<Log> getLogs(String[] args) {
		List<Log> results = new ArrayList<>();
		CommandLineArguments arguments = new CommandLineArguments(args);
		CLAObject obj = new CLAObject(arguments);
		if (obj.getAccessLogs() != null) {
			loadLogToDb(obj);
		}
		logger.info(String.format("listing logs that meet criteria date: %s, duration: %s, threshold: %s ...",
				obj.getStartDate(), obj.getDuration(), obj.getThreshold()));
		Date endDate = obj.getDuration().equalsIgnoreCase("daily") ? DateUtils.addHours(obj.getStartDate(), 24)
				: DateUtils.addHours(obj.getStartDate(), 1);
		results = logRepository.findLogs(obj.getStartDate(), endDate, obj.getThreshold());
		logger.info(String.format("found results: %s", results));
		return results;
	}

	private void loadLogToDb(CLAObject obj) {
		logger.info(String.format("loading logs from file: %s...", obj.getAccessLogs()));
		List<Log> results = readLogsFromFile(obj.getAccessLogs());
		logger.info(String.format("inserting logs from file: %s to db...", obj.getAccessLogs()));
		logRepository.saveAll(results);
		logger.info(String.format("loading logs from file: %s to db completed.", obj.getAccessLogs()));
	}

	private List<Log> searchLogsFromParams() {
		List<Log> results = new ArrayList<Log>();
		return results;
	}

	private List<Log> readLogsFromFile(File file) {
		List<Log> result = new ArrayList<>();
		if (file != null && file.exists()) {
			FlatFileItemReader<Log> reader = getReader(file);
			reader.open(new ExecutionContext());
			try {
				Log line = null;
				do {
					line = reader.read();
					logger.info(String.format("loading line: %s.", line));
					result.add(line);
				} while (line != null);
			} catch (Exception e) {
				logger.warning(String.format("Error while loading file: %s, Cause: %s", file, e.getCause()));
			} finally {
				reader.close();
			}
		} else {
			logger.warning(String.format("Specified file: %s cannot be accessed or doesn't exist", file));
			throw new IllegalArgumentException(
					String.format("Specified file: %s cannot be accessed or doesn't exist", file));
		}
		return result;
	}

	private FlatFileItemReader<Log> getReader(File file) {
		FlatFileItemReader<Log> reader = new FlatFileItemReader<>();
		reader.setResource(new FileSystemResource(file.getAbsolutePath()));
		final DefaultLineMapper<Log> lineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames(new String[] { "date", "ip", "request", "status", "userAgent" });
		lineTokenizer.setDelimiter("|");
		lineMapper.setLineTokenizer(lineTokenizer);
		final BeanWrapperFieldSetMapper<Log> fieldMapper = new BeanWrapperFieldSetMapper<>();
		fieldMapper.setTargetType(Log.class);
		final Map<Class, PropertyEditor> customEditors = Stream
				.of(new AbstractMap.SimpleEntry<>(Date.class, new CustomDateEditor(DATE_FORMAT, false)))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		fieldMapper.setCustomEditors(customEditors);
		lineMapper.setFieldSetMapper(fieldMapper);
		reader.setLineMapper(lineMapper);
		return reader;
	}
}
