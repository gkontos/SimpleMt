package com.kontos.simplemt.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.kontos.simplemt.model.ProcessState;
import com.kontos.simplemt.model.ProcessStateBuffer;
import com.kontos.simplemt.util.XmlConverter;

@Service
public class MtConnectSampleImpl implements MtConnectSample {
	private static Logger LOG = LogManager.getLogger(MtConnectSampleImpl.class.getName());
	@Autowired
	XmlConverter xmlConverter;
	@Autowired
	MtConnectStreamParse mtConnectStreamParse;
	
	public ProcessStateBuffer updateState(InputStream is, ProcessState previous) throws IOException, SAXException, ParserConfigurationException {
		ProcessStateBuffer currentState = new ProcessStateBuffer();
		Document doc = xmlConverter.getXmlDocument(is);
		currentState.setFirstSequence(mtConnectStreamParse.getFirstSequenceId(doc));
		currentState.setNextSequence(mtConnectStreamParse.getNextSequenceId(doc));
		currentState.setHydraulic(mtConnectStreamParse.getHydraulic(doc));
		currentState.setRotary(mtConnectStreamParse.getRotary(doc));
		currentState.setX(mtConnectStreamParse.getX(doc));
		currentState.setY(mtConnectStreamParse.getY(doc));
		currentState.setZ(mtConnectStreamParse.getZ(doc));
		return currentState;
	}

	public ProcessStateBuffer updateState(ProcessState previous) throws IOException, SAXException, ParserConfigurationException {
		return updateState(getInputStream(previous.getNextSequence()), previous);
	}

	private InputStream getInputStream(Long nextSequence) throws IOException {
		URL url;
		try {
		// connect to agent.mtconnect.org/current
			url = new URL("http://agent.mtconnect.org/sample?from="+nextSequence.toString());
			URLConnection conn = url.openConnection();
			return conn.getInputStream();
		} catch (SocketException e){
			LOG.error("Connection Lost. Retrying in 2 sec", e);
			try {
				Thread.sleep(2000);
				return getInputStream(nextSequence);
			} catch (InterruptedException e1) {
				LOG.error(e1);
				return null;
			}
		} catch (UnknownHostException e){
			LOG.error("Connection Error. Retrying in 2 sec", e);
			try {
				Thread.sleep(2000);
				return getInputStream(nextSequence);
			} catch (InterruptedException e1) {
				LOG.error(e1);
				return null;
			}
		} catch (Exception e){
			LOG.error(e);
			throw new IOException(e);
		}
	}
	
}
