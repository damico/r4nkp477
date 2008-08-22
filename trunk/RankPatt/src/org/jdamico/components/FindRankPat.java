package org.jdamico.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.jdamico.config.Constants;
import org.jdamico.dataobjects.AxisObj;
import org.jdamico.dataobjects.FormCoordinates;
import org.jdamico.dataobjects.FormObj;
import org.jdamico.dataobjects.util.FormUtil;
import org.jdamico.exceptions.RankPattException;


public class FindRankPat {
	
	private static final FindRankPat INSTANCE = new FindRankPat();
	public static FindRankPat getInstance(){
		return INSTANCE;
	}
	
	public ArrayList<Double> process(String idStr, Map<String, FormObj> formMap) throws RankPattException{
		ArrayList<Double> retArray = null;
		if(formMap.containsKey(idStr)){
			
			/* number of lines */
			FormObj form = formMap.get(idStr);
			Map<Integer, Integer> rankMap = form.getRankMap();
			//int steps = rankMap.size();
			
			Map<Integer, AxisObj> pos = FormUtil.getInstance().distribute(rankMap);
			
			Collection<AxisObj> colAxis = pos.values();
			
			Iterator<AxisObj> axisIter = colAxis.iterator();
			
			Map<String, Double> distSum = new HashMap<String, Double>();
			
			AxisObj axis;
			
			String[] formOwners = new String[formMap.size() - 1];
			
			//int test = 0;
			
			while(axisIter.hasNext()){
				
				axis = axisIter.next();
				
				//System.err.println("??? "+axis.getX()+" : "+axis.getY());
				
				Collection<FormObj> lstValues = formMap.values();
				
				Iterator<FormObj> iter = lstValues.iterator();
				
				Map<String, FormCoordinates> compAxis = new Hashtable<String, FormCoordinates>();

				int xMain = 0,yMain = 0;
								
				while(iter.hasNext()){

					form = iter.next();
					rankMap = form.getRankMap();
					FormCoordinates fc = new FormCoordinates();
					fc.setAxis(axis);
					fc.setFormOwner(form.getFormOwner());
					fc.setXValue(rankMap.get( axis.getX() ));
					fc.setYValue(rankMap.get( axis.getY() ));
					compAxis.put(form.getFormOwner(), fc);
					//System.out.println(form.getFormOwner()+": ["+rankMap.get( axis.getX() )+","+rankMap.get( axis.getY() )+"]");
					
				}
					xMain = compAxis.get(idStr).getXValue();
					yMain = compAxis.get(idStr).getYValue();
					compAxis.remove(idStr);
					//System.out.println("compAxis.size() ==== "+compAxis.size());
					Collection<FormCoordinates> colFC = compAxis.values();
					
					
					int count = 0;
					Iterator<FormCoordinates> iFC = colFC.iterator();
					
					while(iFC.hasNext()){
						FormCoordinates element = iFC.next();
						formOwners[count] = element.getFormOwner();
						count++;
						double euclideanDist = Math.sqrt(Math.pow((xMain - element.getXValue()),2) + Math.pow((yMain - element.getYValue()),2));
						
						double preValue = .0;
						
						if(distSum.containsKey(element.getFormOwner())) preValue = distSum.get(element.getFormOwner());
						
						double sum = preValue + euclideanDist;
						distSum.put(element.getFormOwner(),sum);
						//System.out.println("   ["+element.getFormOwner()+"] Sum: "+sum+" Distance: "+euclideanDist);
					}
				
				
			}	
			
			retArray =  new ArrayList<Double>();
			int j = 0;
			
			ArrayList<Integer> usedArray = new ArrayList<Integer>();
			
			int maxPos = formOwners.length +1;
			while(!distSum.isEmpty()){
				double min = 0.0, max = 0.0;
				for(int i =0; i<formOwners.length; i++){
					if(!usedArray.contains(i)){
						if(distSum.get(formOwners[i]) > max){
							max = distSum.get(formOwners[i]);
							maxPos = i;
						}						

						if(distSum.get(formOwners[i]) < min) min = distSum.get(formOwners[i]);
					}

				}
				distSum.remove(formOwners[maxPos]);
				usedArray.add(maxPos);
				retArray.add(j, max);
				j++;

			}
		
		}else{
			String errMsg = Constants.FALSEID.replaceAll(Constants.SEARCHPATTERN, idStr);
			throw new RankPattException(errMsg);
		}
		return retArray;
	}
	
	
	
}
