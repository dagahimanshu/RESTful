package com.adobe.demo.cfg;

public class EmailConfig {
	String address;
	int port;
	
	public EmailConfig(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	public void sendMsg(String msg) {
		System.out.println(msg + " sent to " + address + ":" + port);
	}
}
