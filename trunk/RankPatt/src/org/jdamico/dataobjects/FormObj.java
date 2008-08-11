package org.jdamico.dataobjects;

import java.util.HashMap;
import java.util.Map;

public class FormObj {
	private String formOwner = null;
	private Map<Integer, Integer> rankMap = new HashMap<Integer, Integer>();
	
	public String getFormOwner() {
		return formOwner;
	}
	public void setFormOwner(String formOwner) {
		this.formOwner = formOwner;
	}
	
	public Map<Integer, Integer> getRankMap() {
		return rankMap;
	}
	public void setRankMap(Map<Integer, Integer> rankMap) {
		this.rankMap = rankMap;
	}
}
