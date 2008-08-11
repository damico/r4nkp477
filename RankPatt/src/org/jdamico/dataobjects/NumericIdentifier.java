package org.jdamico.dataobjects;

import java.util.ArrayList;

import org.jdamico.components.FindSimPat;

public class NumericIdentifier extends SimSetObj {
	
	//private long idLong = -1;

	public NumericIdentifier(long idLong, ArrayList<RankObj> rankArrLst){
		super(idLong, rankArrLst);

	}
	
	
	
	@Override
	public void findSimPat() {
		
		
		FindSimPat.getInstance().process(this.rankArrLst, this.idLong);
		
		
		
	}

}
