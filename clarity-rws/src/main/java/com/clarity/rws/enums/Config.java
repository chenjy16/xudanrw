package com.clarity.rws.enums;

public enum Config {
	
	Master("master",0),Slave("slave",1),Write("write",3),Read("read",4);
	
	private String desc;
	private Integer code;
	
	private Config(String desc, Integer code) {
		this.desc = desc;
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public Integer getCode() {
		return code;
	}
	
}
