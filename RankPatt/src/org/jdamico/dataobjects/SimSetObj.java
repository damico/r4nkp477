package org.jdamico.dataobjects;

import java.util.ArrayList;

abstract class SimSetObj {
	public String idStr 	= null;
	public long	idLong	= -1;
	public ArrayList<RankObj> rankArrLst = new ArrayList<RankObj>();
	
	public abstract void findSimPat();
	
	public SimSetObj(String idStr, ArrayList<RankObj> rankArrLst){
		this.idStr = idStr;
		this.rankArrLst = rankArrLst;
	}
	public SimSetObj(long idLong, ArrayList<RankObj> rankArrLst) {
		this.idLong = idLong;
		this.rankArrLst = rankArrLst;
	}

	
}
