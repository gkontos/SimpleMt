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
import com.kontos.simplemt.model.Rotary;
import com.kontos.simplemt.model.SysCondition;
import com.kontos.simplemt.service.MtConnectStreamParse;
import com.kontos.simplemt.test.common.TestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestConfig.class})
public class MtConnectStreamParseTest {
	private static Logger LOG = LogManager.getLogger(MtConnectStreamParseTest.class.getName());
	@Autowired
	MtConnectStreamParse mtConnectStreamParse;

	@Test
public void testNextId() throws IOException, SAXException, ParserConfigurationException{
	InputStream is = loadCurrentFileAsStream();
	Long bint = mtConnectStreamParse.getNextSequenceId(getXmlDocument(is));
	assertTrue(bint != null);
	assertTrue(bint.compareTo(new Long("0")) > 0);
	LOG.debug("next sequence=" + bint.toString());
	is.close();
}

@Test
public void testFirstId() throws IOException, SAXException, ParserConfigurationException {
	InputStream is = loadCurrentFileAsStream();
	Long bint = mtConnectStreamParse.getFirstSequenceId(getXmlDocument(is));
	assertTrue(bint != null);
	assertTrue(bint.compareTo(new Long("0")) > 0);
	LOG.debug("first sequence=" + bint.toString());
	is.close();
}

@Test
public void testgetAxis() throws IOException, SAXException, ParserConfigurationException {
	InputStream is = loadSampleFileAsStream();
	List<Axis> axis = mtConnectStreamParse.getX(getXmlDocument(is));
	assertTrue(axis.size() == 21);
	assertTrue(axis.get(0).getPositionActual() == (double)1.4198908806);
	assertTrue(axis.get(0).getSequenceNo().equals(new Long("312069278")));
	assertTrue(axis.get(axis.size()-1).getPositionActual() == (double)1.1544514894);
	assertTrue(axis.get(axis.size()-1).getSequenceNo().equals(new Long("312069369")));
}


@Test
public void testGetRotarySample() throws IOException, SAXException, ParserConfigurationException {
	InputStream is = loadSampleFileAsStream();
	List<Rotary> spindle = mtConnectStreamParse.getRotary(getXmlDocument(is));
	assertTrue(spindle.size() == 0);
}

@Test
public void testGetHydraulicSample() throws IOException, SAXException, ParserConfigurationException {
	InputStream is = loadSampleFileAsStream();
	List<SysCondition> hyd = mtConnectStreamParse.getHydraulic(getXmlDocument(is));
	assertTrue(hyd.size() == 1);
	LOG.debug(hyd.get(0).getSequenceNo());
	assertTrue(hyd.get(0).getCondition().equals("Normal"));
	assertTrue(hyd.get(0).getSequenceNo().equals(new Long("312069271")));

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
