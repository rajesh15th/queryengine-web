package com.randish.model;

public class DataResponse {
	Message message;
	Object object;
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	public void setMessage(String code, String text) {
		this.message = new Message(code, text);
	}
	public void setMessage(String code) {
		this.message = new Message(code);
	}
	@Override
	public String toString() {
		return "DataResponse [message=" + message + ", object=" + object + "]";
	}
	
}