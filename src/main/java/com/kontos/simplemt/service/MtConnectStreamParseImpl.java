package com.kontos.simplemt.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kontos.simplemt.model.Axis;
import com.kontos.simplemt.model.Rotary;
import com.kontos.simplemt.model.SysCondition;
import com.kontos.simplemt.util.XmlConverter;

@Service
public class MtConnectStreamParseImpl implements MtConnectStreamParse {
	private static Logger LOG = LogManager.getLogger(MtConnectStreamParseImpl.class.getName());
	@Autowired
	XmlConverter xmlConverter;

	private List<Axis> getAxis(Document doc, String axisName) {
		Node node = null;
		Element nodeElement = null;
		NodeList nodeList = null;
		List<Axis> axisList = new ArrayList<Axis>();
		Axis axis = null;
		try {
			// parse response for nextSequenceId
			nodeList = (NodeList) xmlConverter.getXmlNodeList(doc, "//ComponentStream[@component='Linear' and @name='"
					+ axisName + "']/Samples/Position[@subType='ACTUAL']");
		} catch (Exception e) {
			LOG.error(e);
		}
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				node = nodeList.item(i);
				if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {

					nodeElement = (Element) node;
					axis = new Axis();
					axis.setPositionActual(new Double(nodeElement.getTextContent()).doubleValue());
					axis.setSequenceNo(new Long(nodeElement.getAttribute("sequence")));
					axis.setTimestamp(parseDateTime(nodeElement.getAttribute("timestamp")));
					axisList.add(axis);

				}
			}
		}
		return axisList;
	}

	public List<Axis> getZ(Document doc) {
		return getAxis(doc, "Z");
	}

	public List<Axis> getY(Document doc) {
		return getAxis(doc, "Y");
	}

	public List<Axis> getX(Document doc) {
		return getAxis(doc, "X");
	}

	public List<Rotary> getRotary(Document doc) {
		Node node = null;
		Element nodeElement = null;
		NodeList nodeList = null;
		ArrayList<Rotary> rotList = new ArrayList<Rotary>();
		Rotary rot = null;
		try {
			// parse response for nextSequenceId
			nodeList = (NodeList) xmlConverter.getXmlNodeList(doc,
					"//ComponentStream[@component='Rotary']/Samples/SpindleSpeed[@subType='ACTUAL']");
		} catch (Exception e) {
			LOG.error(e);
		}
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				node = nodeList.item(i);
				if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {

					nodeElement = (Element) node;
					rot = new Rotary();

					LOG.debug("Spindle node value " + nodeElement.getTextContent());
					rot.setSpeedActual(new Double(nodeElement.getTextContent()).doubleValue());
					rot.setSequenceNo(new Long(nodeElement.getAttribute("sequence")));
					rot.setTimestamp(parseDateTime(nodeElement.getAttribute("timestamp")));
					rotList.add(rot);
				}
			}
		}

		return rotList;
	}

	public List<SysCondition> getHydraulic(Document doc) {
		Node node = null;
		List<SysCondition> condList = new ArrayList<SysCondition>();
		SysCondition cond = null;
		try {
			// parse response for nextSequenceId
			node = xmlConverter.getXmlNode(doc, "//ComponentStream[@component='Hydraulic']/Condition");
		} catch (Exception e) {
			LOG.error(e);
		}
		if (node != null) {

			for (int i = 0; i < node.getChildNodes().getLength(); i++) {
				Node childNode = node.getChildNodes().item(i);
				if (childNode instanceof Element) {
					LOG.debug(childNode.getNodeName());
					Element child = (Element) childNode;
					if (child.getAttribute("type").equals("PRESSURE")) {
						cond = new SysCondition();
						cond.setCondition(child.getNodeName());
						cond.setSequenceNo(new Long(child.getAttribute("sequence")));
						cond.setTimestamp(parseDateTime(child.getAttribute("timestamp")));
						condList.add(cond);
					}
				}
			}

			return condList;
		} else {
			return null;
		}
	}

	public Element getHeaderNode(Document doc) {

		Node headerNode = null;
		try {
			// parse response for nextSequenceId
			headerNode = xmlConverter.getXmlNode(doc, "//Header");
		} catch (Exception e) {
			LOG.error(e);
		}
		if (headerNode != null) {
			return (Element) headerNode;
		} else {
			return null;
		}

	}

	public Long getNextSequenceId(Document doc) {
		Element header = getHeaderNode(doc);
		if (header != null) {
			// parse response for nextSequenceId
			Long nextSequence = new Long(header.getAttribute("nextSequence"));
			return nextSequence;
		} else {
			return null;
		}
		// return nextSequenceId
	}

	public Long getFirstSequenceId(Document doc) {
		Element header = getHeaderNode(doc);
		if (header != null) {
			// parse response for nextSequenceId
			Long nextSequence = new Long(header.getAttribute("firstSequence"));
			return nextSequence;
		} else {
			return null;
		}
		// return nextSequenceId
	}

	public LocalDateTime parseDateTime(String timestamp) {
		LocalDateTime time = LocalDateTime.parse(timestamp);
		return time;
	}
}
