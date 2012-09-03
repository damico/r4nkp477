package org.jdamico.rankpatt.components;

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
import org.jdamico.dataobjects.SimData;
import org.jdamico.dataobjects.util.FormUtil;
import org.jdamico.exceptions.RankPattException;

/**
 * FindRankPat is the class responsible by compare a set of profiles
 * and find the most similar 
 * @author (Jose Damico) Jose Ricardo de Oliveira Damico
 */

public class FindRankPat {
	
	/**
	 * Static instance of this class to be used as singleton
	 */
	private static final FindRankPat INSTANCE = new FindRankPat();
	
	/**
	 * Global point of access to FindRankPat
	 * Method implementation of Singleton Object Creational Pattern
	 * @return only one FindRankPat instance based on INSTANCE
	 */
	public static FindRankPat getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Process the main logic for profile comparison
	 * Analyzes each rank value and applies Euclidean distance
	 * between profiles  
	 * @param idStr String identifier of profile for comparison (a.k.a.: form owner)
	 * @param formMap Map that makes a relationship between idStr and its rank values (rankMap)
	 * @return ArrayList filled with Euclidean score (double) of each profile ordered by descendant 
	 * @throws RankPattException if formMap does not contains key idStr
	 */
	public ArrayList<SimData> process(String idStr, Map<String, FormObj> formMap) throws RankPattException{
		ArrayList<SimData> retArray = null;
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

				int xMain = 0, yMain = 0;
					
				System.out.println("Testing: ITEM "+axis.getX()+" vs. ITEM "+axis.getY()+"\n\n");
				
				while(iter.hasNext()){

					
					
					form = iter.next();
					rankMap = form.getRankMap();
					FormCoordinates fc = new FormCoordinates();
					fc.setAxis(axis);
					fc.setFormOwner(form.getFormOwner());
					fc.setXValue(rankMap.get( axis.getX() ));
					fc.setYValue(rankMap.get( axis.getY() ));
					compAxis.put(form.getFormOwner(), fc);
					System.out.println(form.getFormOwner()+": ["+rankMap.get( axis.getX() )+","+rankMap.get( axis.getY() )+"]");

				}
				
				System.out.println();
				
				
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
						System.out.println("["+element.getFormOwner()+"] TotalSum: "+sum+" Distance: "+euclideanDist);
					}
				
					System.out.println("\n========\n");
			}	
			
			retArray =  new ArrayList<SimData>();
			int j = 0;
			
			ArrayList<Integer> usedArray = new ArrayList<Integer>();
			
			int maxPos = formOwners.length +1;
			while(!distSum.isEmpty()){
				double min = .0, max = .0;
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
				retArray.add(j, new SimData(max, formOwners[maxPos], -1l));
				j++;

			}
		
		}else{
			String errMsg = Constants.FALSEID.replaceAll(Constants.SEARCHPATTERN, idStr);
			throw new RankPattException(errMsg);
		}
		return retArray;
	}
	
	
	
}
