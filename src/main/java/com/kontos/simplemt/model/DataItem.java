package com.kontos.simplemt.model;

import java.time.LocalDateTime;

/**
 * common attributes of a process variable
 * 
 * @author greg
 *
 */
public class DataItem {
	
	private LocalDateTime timestamp;
	private Long sequenceNo;
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public Long getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Long sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	
}
