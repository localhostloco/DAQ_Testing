package testing.Impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import datalogger.DataLogger;
import datalogger.drivers.LoggerDriverPressure;

public class DataLoggerImpl implements DataLogger, LoggerDriverPressure {

	private boolean bufferClosed;

	// General parameters
	private Double[] lastData;
	private Double currentValue;
	private boolean activated;

	// Auxiliary parameters
	private BufferedReader br;
	private int lastDataIndex;

	// Constructor
	public DataLoggerImpl() throws FileNotFoundException {

		br = new BufferedReader(new FileReader(new File(DATA_FILE_PATH)));
		lastData = new Double[SIZE_LAST_DATA];
		bufferClosed = false;
		activated = true;
		initializeLastData();
	}

	public void readValue() throws IOException, FileNotFoundException {
		if (!bufferClosed) {
			String line = br.readLine();
			if (line == null) {
				br = new BufferedReader(new FileReader(new File(
						DATA_FILE_PATH.toString())));
				setBr(br);
				readValue();
			} else {
				Double value = Double.parseDouble(line);
				currentValue = value;
				addValueLastData(value);
			}
		}
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public String getDataName() {
		return DATA_NAME;
	}

	public String getDataEngineeringUnit() {
		return ENGINEERING_UNIT;
	}

	public Double[] getLastData() {
		return lastData;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}

	public BufferedReader getbr() {
		return br;
	}

	public void stop() throws IOException {
		if (!bufferClosed) {
			br.close();
			br = null;
			lastData = new Double[SIZE_LAST_DATA];
			initializeLastData();
			bufferClosed = true;
		} else {
			br = new BufferedReader(new FileReader(new File(DATA_FILE_PATH)));
			lastData = new Double[SIZE_LAST_DATA];
			bufferClosed = false;
			initializeLastData();
		}
		activated = false;
	}

	public String getDataUnit() {
		return ENGINEERING_UNIT;
	}

	public String getDriverName() {
		return DRIVER_NAME;
	}

	private void addValueLastData(Double d) {
		lastData[lastDataIndex] = d;
		lastDataIndex++;
		if (lastDataIndex == SIZE_LAST_DATA)
			lastDataIndex = 0;
	}

	private void initializeLastData() {
		lastData = new Double[SIZE_LAST_DATA];
		for (int i = 0; i < SIZE_LAST_DATA; i++)
			lastData[i] = -1.0;
	}

	public boolean isActivated() {
		return activated;
	}

	public void activate() {
		activated = true;
	}
}
