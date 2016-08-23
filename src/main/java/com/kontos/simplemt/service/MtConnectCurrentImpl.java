package com.kontos.simplemt.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.kontos.simplemt.model.Axis;
import com.kontos.simplemt.model.ProcessState;
import com.kontos.simplemt.model.Rotary;
import com.kontos.simplemt.model.SysCondition;
import com.kontos.simplemt.util.XmlConverter;

@Service
public class MtConnectCurrentImpl implements MtConnectCurrent {
	private static Logger LOG = LogManager.getLogger(MtConnectCurrentImpl.class.getName());
	@Autowired
	XmlConverter xmlConverter;
	@Autowired
	MtConnectStreamParse mtConnectStreamParse;

	private InputStream getInputStream() throws IOException {
		URL url;
		try {
		// connect to agent.mtconnect.org/current
			url = new URL("http://agent.mtconnect.org/current");
			URLConnection conn = url.openConnection();
			return conn.getInputStream();
		} catch (Exception e){
			LOG.error(e);
			throw new IOException(e);
		}
	}
	public ProcessState getCurrentState() throws IOException {
		InputStream is = null;
		try {
			is = getInputStream();
			
		} catch (IOException e) {
			LOG.error("Unable to process current endpoint", e);
			throw new IOException(e);
		}
		if (is != null) {
			ProcessState state = getCurrentState(is);
			is.close();
			return state;
		} else {
			return null;
		}
	}

	public ProcessState getCurrentState(InputStream is) {
		ProcessState current = new ProcessState();
		Document doc = null;
		try {
			doc = xmlConverter.getXmlDocument(is);
		} catch (IOException e) {
			LOG.error(e);
		} catch (SAXException e) {
			LOG.error(e);
		} catch (ParserConfigurationException e) {
			LOG.error(e);
		} 
		if (doc != null) {
			current.setFirstSequence(mtConnectStreamParse.getFirstSequenceId(doc));
			current.setNextSequence(mtConnectStreamParse.getNextSequenceId(doc));
			List<SysCondition> hyd = mtConnectStreamParse.getHydraulic(doc);
			if (hyd != null && hyd.size() > 0) {
				current.setHydraulic(hyd.get(0));
			}
			List<Rotary> rot = mtConnectStreamParse.getRotary(doc);
			if (rot != null && rot.size() > 0) {
				current.setRotary(rot.get(0));
			}
			List<Axis> x = mtConnectStreamParse.getX(doc);
			if (x != null && x.size() > 0) {
				current.setX(x.get(0));
			}
			List<Axis> y = mtConnectStreamParse.getY(doc);
			if (y != null && y.size() > 0) {
				current.setY(y.get(0));
			}
			List<Axis> z = mtConnectStreamParse.getZ(doc);
			if (z != null && z.size() > 0) {
				current.setZ(z.get(0));
			}
		}
		return current;
	}


	
}
