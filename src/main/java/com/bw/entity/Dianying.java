package com.bw.entity;


public class Dianying implements java.io.Serializable {

	//columns START
	private Integer did;
	private String dname;
	private String desc;
	private String daoyan;
	private String startdate;
	private String tid;
	private String tname;
	
	@Override
	public String toString() {
		return "Dianying [did=" + did + ", dname=" + dname + ", desc=" + desc
				+ ", daoyan=" + daoyan + ", startdate=" + startdate + ", tid="
				+ tid + ", tname=" + tname + "]";
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	//columns END
	public Dianying(){
	}
	public Dianying(Integer did,String dname,String desc,String daoyan,String startdate){
		this.did=did;		this.dname=dname;		this.desc=desc;		this.daoyan=daoyan;		this.startdate=startdate;	}

	public void setDid(Integer did){
		this.did=did;
	}
	public Integer getDid(){
		return did;
	}

	public void setDname(String dname){
		this.dname=dname;
	}
	public String getDname(){
		return dname;
	}

	public void setDesc(String desc){
		this.desc=desc;
	}
	public String getDesc(){
		return desc;
	}

	public void setDaoyan(String daoyan){
		this.daoyan=daoyan;
	}
	public String getDaoyan(){
		return daoyan;
	}

	public void setStartdate(String startdate){
		this.startdate=startdate;
	}
	public String getStartdate(){
		return startdate;
	}
}

