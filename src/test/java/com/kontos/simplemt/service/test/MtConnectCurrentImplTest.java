package com.kontos.simplemt.service.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

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
import com.kontos.simplemt.service.MtConnectCurrent;
import com.kontos.simplemt.test.common.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class MtConnectCurrentImplTest {
	private static Logger LOG = LogManager.getLogger(MtConnectCurrentImplTest.class.getName());
	@Autowired
	MtConnectCurrent mtConnectCurrent;

@Test
public void testInitialState() throws Exception {
	InputStream is = loadCurrentFileAsStream();
	ProcessState current = mtConnectCurrent.getCurrentState(is);
	assertTrue(current.getHydraulic().getCondition().equals("Normal"));
	assertTrue(current.getHydraulic().getSequenceNo().equals(new Long("312069271")));
	assertTrue(current.getHydraulic().getTimestamp().isBefore(LocalDateTime.now()));
	assertTrue(current.getRotary().getSpeedActual() == 3400.0000000000);
	assertTrue(current.getX().getPositionActual() == (double)1.6640045643);
	assertTrue(current.getY().getPositionActual() == (double)-1.1031000614);
	assertTrue(current.getZ().getPositionActual() == (double)-0.1000000015);
	assertTrue(current.getFirstSequence().equals(new Long("311994744")));
	assertTrue(current.getNextSequence().equals(new Long("312125816")));
	is.close();
	
}

private InputStream loadCurrentFileAsStream() throws FileNotFoundException {
	ClassLoader classLoader = getClass().getClassLoader();
	File file = new File(classLoader.getResource("current_endpoint.xml").getFile());
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
