package org.jdamico.dataobjects;

public class RankObj {
	
	private String	idStr 	= null;
	private long 	idLong 	= -1;
	private long 	value	= 0;
	
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public long getIdLong() {
		return idLong;
	}
	public void setIdLong(long idLong) {
		this.idLong = idLong;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}

}
