package com.kontos.simplemt.service;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.kontos.simplemt.model.Axis;
import com.kontos.simplemt.model.Rotary;
import com.kontos.simplemt.model.SysCondition;

public interface MtConnectStreamParse {

	/**
	 * get a list of the hydraulic conditions available in the xml document
	 * @param doc
	 * @return
	 */
	List<SysCondition> getHydraulic(Document doc);

	/**
	 * get a list of the rotary speed information available in the xml document
	 * @param doc
	 * @return
	 */
	List<Rotary> getRotary(Document doc);

	/**
	 * get a list of the x position available in the xml document
	 * @param doc
	 * @return
	 */
	List<Axis> getX(Document doc);

	/**
	 * get a list of the y position available in the xml document
	 * @param doc
	 * @return
	 */
	List<Axis> getY(Document doc);

	/**
	 * get a list of the Z position available in the xml document
	 * @param doc
	 * @return
	 */
	List<Axis> getZ(Document doc);

	/**
	 * return the header node (which includes nextSequence, buffer, firstSequence, etc.
	 * @param doc
	 * @return
	 */
	Element getHeaderNode(Document doc);

	/**
	 * get the header element attribute for next sequence id
	 * @return a Long value or null
	 */
	Long getNextSequenceId(Document doc);

	/**
	 * get the header element attribute for first sequence id
	 * @return a Long value or null
	 */
	Long getFirstSequenceId(Document doc);
}
