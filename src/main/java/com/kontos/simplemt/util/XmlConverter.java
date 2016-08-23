package com.kontos.simplemt.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * methods for processing xml files and documents
 * @author greg
 *
 */
@Component
public class XmlConverter {

	private static Logger LOG = LogManager.getLogger(XmlConverter.class.getName());
	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Unmarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public Object convertFromXMLToObject(InputStream is) throws IOException {

		
		try {
		
			return getUnmarshaller().unmarshal(new StreamSource(is));
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * get an xml document from the input stream
	 * @param is
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public Document getXmlDocument(InputStream is) throws IOException, SAXException, ParserConfigurationException {

		Document xmlDocument = null;
	
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(is);
		} finally {
			
		}
		
		return xmlDocument;
	}

	/**
	 * get an xmlNode object given the XPATH expression
	 * @param xmlDocument
	 * @param expression (an xpath expression)
	 * @return the Node or Null
	 */
	public Node getXmlNode(Document xmlDocument,String expression) {
		Node node = null;
		XPath xPath =  XPathFactory.newInstance().newXPath();
		try {
			xPath.compile(expression).evaluate(xmlDocument);
			node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
			
		} catch (XPathExpressionException e) {
			LOG.error(e);
		}
		return node;
	}
	
	/**
	 * get the nodeList based on an xpath expression
	 * @param xmlDocument
	 * @param expression (an xpath expression)
	 * @return the nodelist or null
	 */
	public NodeList getXmlNodeList(Document xmlDocument,String expression) {
		NodeList node = null;
		XPath xPath =  XPathFactory.newInstance().newXPath();
		try {
			xPath.compile(expression).evaluate(xmlDocument);
			node = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			
		} catch (XPathExpressionException e) {
			LOG.error(e);
		}
		return node;
	}
}
