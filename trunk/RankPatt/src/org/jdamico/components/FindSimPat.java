package org.jdamico.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jdamico.config.Constants;
import org.jdamico.dataobjects.AxisObj;
import org.jdamico.dataobjects.FormObj;
import org.jdamico.dataobjects.util.FormUtil;
import org.jdamico.exceptions.RankPattException;


public class FindSimPat {
	
	private static final FindSimPat INSTANCE = new FindSimPat();
	public static FindSimPat getInstance(){
		return INSTANCE;
	}
	
	public void process(String idStr, Map<String, FormObj> formMap) throws RankPattException{
	
		if(formMap.containsKey(idStr)){
			
			/* number of lines */
			FormObj form = formMap.get(idStr);
			Map<Integer, Integer> rankMap = form.getRankMap();
			int steps = rankMap.size();
			
			Map<Integer, AxisObj> pos = FormUtil.getInstance().distribute(rankMap);
			
			Collection<AxisObj> colAxis = pos.values();
			
			Iterator<AxisObj> axisIter = colAxis.iterator();
			
			
			
			AxisObj axis = null;
			
			int test = 0;
			
			while(axisIter.hasNext()){
				
				axis = axisIter.next();
				
				System.err.println("??? "+axis.getX()+" : "+axis.getY());
				
				Collection<FormObj> lstValues = formMap.values();
				
				Iterator<FormObj> iter = lstValues.iterator();
				while(iter.hasNext()){
					form = iter.next();
					rankMap = form.getRankMap();
					System.out.println(form.getFormOwner()+": ["+rankMap.get( axis.getX() )+","+rankMap.get( axis.getY() )+"]");
				}
				
				
			}	
			
			
		}else{
			String errMsg = Constants.FALSEID.replaceAll(Constants.SEARCHPATTERN, idStr);
			throw new RankPattException(errMsg);
		}
		
	}
	
	
	
}
