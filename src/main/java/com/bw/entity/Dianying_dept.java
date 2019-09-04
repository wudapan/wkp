package com.bw.entity;


public class Dianying_dept implements java.io.Serializable {

	//columns START
	private Integer did;
	private Integer tid;
	//columns END
	public Dianying_dept(){
	}
	public Dianying_dept(Integer did,Integer tid){
		this.did=did;		this.tid=tid;	}

	public void setDid(Integer did){
		this.did=did;
	}
	public Integer getDid(){
		return did;
	}

	public void setTid(Integer tid){
		this.tid=tid;
	}
	public Integer getTid(){
		return tid;
	}
}

