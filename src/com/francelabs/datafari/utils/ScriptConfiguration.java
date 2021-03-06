/*******************************************************************************
 * Copyright 2015 France Labs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.francelabs.datafari.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.francelabs.datafari.statistics.StatsPusher;

/**
 * Configuration reader
 * 
 * @author France Labs
 * 
 */
public class ScriptConfiguration {

	
	//TODO switch to relative path
	public static String configPropertiesFileName = "datafari.properties";

	private static ScriptConfiguration instance;
	private Properties properties;

	private final static Logger LOGGER = Logger.getLogger(ScriptConfiguration.class
			.getName());

	/*
	public static void setProperty(String key, String value) throws IOException {
		getInstance().properties.setProperty(key, value);
		getInstance().properties.store(new FileOutputStream(configPropertiesFileName), null);
	}*/

	public static String getProperty(String key) throws IOException {
		return (String) getInstance().properties.get(key);
	}

	/**
	 * 
	 * Get the instance
	 * 
	 */
	private static ScriptConfiguration getInstance() throws IOException {
		if (null == instance) {
			instance = new ScriptConfiguration();
		}
		return instance;
	}

	/**
	 * 
	 * Read the properties file to get the parameters to create  instance
	 * 
	 */
	private ScriptConfiguration() throws IOException {
		
		File configFile = new File(System.getProperty("catalina.base") + File.separator + "conf" + File.separator + configPropertiesFileName);
		
		InputStream stream = new FileInputStream(configFile);
		properties = new Properties();
		try {
			properties.load(stream);
		} catch (IOException e){
			LOGGER.error("Cannot read file : "+ configFile.getAbsolutePath(),e );
			throw e;
		} finally {
			stream.close();
		}
		
	}

}
