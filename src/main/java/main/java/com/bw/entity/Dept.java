package main.java.com.bw.entity;


public class Dept implements java.io.Serializable {

	//columns START
	private Integer tid;
	private String tname;
	//columns END
	public Dept(){
	}
	public Dept(Integer tid,String tname){
		this.tid=tid;		this.tname=tname;	}

	public void setTid(Integer tid){
		this.tid=tid;
	}
	public Integer getTid(){
		return tid;
	}

	public void setTname(String tname){
		this.tname=tname;
	}
	public String getTname(){
		return tname;
	}
}

