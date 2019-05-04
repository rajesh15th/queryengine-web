package com.randish.model;

public class Message {
	String code;
	String text;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Message(String code, String text) {
		this.code = code;
		this.text = text;
	}
	public Message(String code) {
		this.code = code;
	}
	public Message() {
	}
	@Override
	public String toString() {
		return "Message [code=" + code + ", text=" + text + "]";
	}
	
	
	
	
}
