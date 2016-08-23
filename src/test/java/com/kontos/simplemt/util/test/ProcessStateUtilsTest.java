package com.kontos.simplemt.util.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.kontos.simplemt.model.ProcessState;
import com.kontos.simplemt.model.ProcessStateBuffer;
import com.kontos.simplemt.service.MtConnectCurrent;
import com.kontos.simplemt.service.MtConnectSample;
import com.kontos.simplemt.test.common.TestConfig;
import com.kontos.simplemt.util.ProcessStateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class ProcessStateUtilsTest {
	private static Logger LOG = LogManager.getLogger(ProcessStateUtilsTest.class.getName());
	@Autowired
	MtConnectSample mtConnectSample;
	@Autowired
	MtConnectCurrent mtConnectCurrent;
	@Autowired
	ProcessStateUtils stateUtils;

@Test 
public void testUpdate() throws IOException, SAXException, ParserConfigurationException {
	ProcessState state = mtConnectCurrent.getCurrentState(loadCurrentFileAsStream());
	ProcessStateBuffer buffer = mtConnectSample.updateState(loadSampleFileAsStream(), state);
	ProcessState current = stateUtils.updateProcessState(buffer, state);		
	assertTrue(current.getNextSequence().equals(new Long("312069371")));
	assertTrue(current.getX().getPositionActual() == (double)1.1544514894);
}
	
private InputStream loadCurrentFileAsStream() throws FileNotFoundException {
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("current_endpoint.xml").getFile());
	InputStream is = new FileInputStream(file);
	return is;
}

private InputStream loadSampleFileAsStream() throws FileNotFoundException {
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("sample_endpoint.xml").getFile());
	InputStream is = new FileInputStream(file);
	return is;
}

public Document getXmlDocument(InputStream is) throws IOException, SAXException, ParserConfigurationException {

	Document xmlDocument = null;
	Node node = null;

	try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		xmlDocument = builder.parse(is);
	} finally {
		
	}
	
	return xmlDocument;
}
}
