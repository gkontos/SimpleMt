package com.kontos.simplemt.service.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

import com.kontos.simplemt.model.Axis;
import com.kontos.simplemt.model.ProcessStateBuffer;
import com.kontos.simplemt.service.MtConnectSample;
import com.kontos.simplemt.test.common.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class MtConnectSampleParseTest {
	private static Logger LOG = LogManager.getLogger(MtConnectSampleParseTest.class.getName());
	@Autowired
	MtConnectSample mtConnectSample;



@Test
public void testgetAxis() throws IOException, SAXException, ParserConfigurationException {
	InputStream is = loadSampleFileAsStream();
	ProcessStateBuffer buffer = mtConnectSample.updateState(is, null);
	List<Axis> axis = buffer.getX();
	assertTrue(axis.size() == 21);
	assertTrue(axis.get(0).getPositionActual() == (double)1.4198908806);
	assertTrue(axis.get(0).getSequenceNo().equals(new Long("312069278")));
	assertTrue(axis.get(axis.size()-1).getPositionActual() == (double)1.1544514894);
	assertTrue(axis.get(axis.size()-1).getSequenceNo().equals(new Long("312069369")));
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
