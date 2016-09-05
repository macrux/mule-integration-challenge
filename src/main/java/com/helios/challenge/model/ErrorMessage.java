package com.helios.challenge.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents an error message with a customized error code, message and link
 * where to find documentation about the error. A JSON/XML representations of
 * this class is sent to clients when there are errors or special situations
 * (e.g. data not found).
 *
 */
@XmlRootElement
public class ErrorMessage {

	private String errorMessage;
	private int errorCode;
	private String documentation;

	public ErrorMessage() {
	}

	/**
	 * Constructor
	 * 
	 * @param errorMessage
	 *            String with the message.
	 * 
	 * @param errorCode
	 *            int with customized error code.
	 * 
	 * @param documentation
	 *            String with documentation link.
	 */
	public ErrorMessage(String errorMessage, int errorCode, String documentation) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
		this.documentation = documentation;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getDocumentation() {
		return documentation;
	}

	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}

}
