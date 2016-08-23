package com.kontos.simplemt.service;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.kontos.simplemt.model.ProcessState;
import com.kontos.simplemt.model.ProcessStateBuffer;

public interface MtConnectSample {
	/**
	 * FOR TESTING.  
	 * get the updated samples for the process variables using the input stream given.
	 * @param is
	 * @param previous
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public ProcessStateBuffer updateState(InputStream is, ProcessState previous) throws IOException, SAXException, ParserConfigurationException;
	/**
	 * get the updated samples for the process variables using the input stream given.
	 * @param previous - will be used to populate the from variable of the endpoint
	 * @return - a ProcessStateBuffer with a list of data available for each process variable of interest
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public ProcessStateBuffer updateState(ProcessState previous) throws IOException, SAXException, ParserConfigurationException;
	
}
