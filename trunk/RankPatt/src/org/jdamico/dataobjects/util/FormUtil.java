package org.jdamico.dataobjects.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.jdamico.config.Constants;
import org.jdamico.dataobjects.AxisObj;
import org.jdamico.dataobjects.FormObj;
import org.jdamico.exceptions.RankPattException;
import org.jdamico.tests.FindRankPattTest;

/**
 * Set of utilities to handle {@link FormObj}
 * @author (Jose Damico) Jose Ricardo de Oliveira Damico
 */

public class FormUtil {
	
	/**
	 * Static instance of this class to be used as singleton
	 */
	private static final FormUtil INSTANCE = new FormUtil();
	
	/**
	 * Global point of access to FormUtil
	 * Method implementation of Singleton Object Creational Pattern
	 * @return only one FormUtil instance based on INSTANCE
	 */
	public static FormUtil getInstance(){
		return INSTANCE;
	}
	
	/**
	 * Utility to create a {@link FormObj} given a String[] Dictionary.
	 * @see {@link FindRankPattTest#plotSampleData()}
	 * @param formDictionary String[] Dictionary
	 * @return populated {@link FormObj}
	 * @throws RankPattException in case of malformed formDictionary
	 */
	public FormObj createForm(String[] formDictionary) throws RankPattException{
		
		
		Map<Integer, Integer> rankMap = new HashMap<Integer, Integer>();
		String owner = null;
		/* 
		 * Check for at least 2 items into array
		 * Check for only one valid Owner
		 */ 
		int count = 0;
		count = formDictionary.length;
		if(count < 2) throw new RankPattException(Constants.INVALIDLST);
		for(int i=0; i<count; i++){
			String tmpItem = formDictionary[i].trim();
			if(tmpItem.length()>2 && tmpItem.contains(Constants.SEPARATOR_TAG)){
				if(tmpItem.contains(Constants.OWNER_TAG) 
						&& tmpItem.length() > Constants.OWNER_TAG.length() 
						&& owner == null) {
					owner = tmpItem.replaceAll(Constants.OWNER_TAG, Constants.BLANKSPACE);
				}else{
					StringTokenizer sToken = new StringTokenizer(tmpItem, Constants.SEPARATOR_TAG);
					
					int key, value; 
										
					try{
						key = Integer.parseInt(sToken.nextToken());
						value = Integer.parseInt(sToken.nextToken());
					} catch(NumberFormatException nfe){
						throw new RankPattException(Constants.INVALIDDATAPAIRVALUES);
					}
					
					if(rankMap.containsKey(key)) throw new RankPattException(Constants.CONTAINSKEYDATAPAIR);
					
					rankMap.put(key, value);
				}				
			}else{
				throw new RankPattException(Constants.INVALIDDATAPAIR);
			}
			if(i == count-1 && owner == null) throw new RankPattException(Constants.NOOWNER);
		}
		FormObj form = new FormObj();
		form.setFormOwner(owner);
		form.setRankMap(rankMap);
		
		return form;
		
	}
	
	/**
	 * Utility for {@link FormObj} Map creation. Where FormObject is the value and String formOwner is the key 
	 * @param formMap a base map used to add a new pair of key & value
	 * @param form New {@link FormObj} to be added at Map (as value)
	 * @return the same Map passed as parameter, but with more one key/value pair
	 * @throws RankPattException in case of passed Map already contains a key
	 */
	public Map<String, FormObj> add2Map(Map<String, FormObj> formMap, FormObj form) throws RankPattException{
		
		if(formMap.containsKey(form.getFormOwner())) throw new RankPattException(Constants.CONTAINSKEYFORM);
		formMap.put(form.getFormOwner(), form);
		
		return formMap;
	}
	
	/**
	 * It calculates the number of ranks (items, options or questions) then it maps all possible combinations. 
	 * Each combination is a pair, that will populate a {@link AxisObj}
	 * @param rankMap is the Map that contains ranks (items, options or questions) ant it values. It is a map 
	 * that comes from FormObj
	 * @return
	 * @throws RankPattException
	 */
	public Map<Integer, AxisObj> distribute(Map<Integer, Integer> rankMap) throws RankPattException{
		
		//System.err.println("rankMap.size() => "+rankMap.size());
		
		AxisObj axis = null;
		
		String[] items = new String[rankMap.size()];
		int count=0;
		Collection<Integer> col = rankMap.values();
		Iterator<Integer> iter = col.iterator();
		while(iter.hasNext()){
			items[count] = iter.next().toString();
			count++;
		}
		
		Map<Integer, AxisObj> pos = new HashMap<Integer, AxisObj>();
		Map<String, Integer> posTmp = new HashMap<String, Integer>();
		
		
		count=0;
		
		for(int i=0; i<items.length; i++){
			for(int j=0; j<items.length; j++){
				
				String tmpKey = items[i] + items[j];
				
				if(!posTmp.containsKey(swapKey(tmpKey)) && !isDoublePair(tmpKey)){
					axis = new AxisObj();
					axis.setX(Integer.parseInt(tmpKey.substring(0,1)));
					axis.setY(Integer.parseInt(tmpKey.substring(1)));
					pos.put(count, axis);
					posTmp.put(tmpKey,count);
					//System.out.println("tmpKey => "+tmpKey.substring(0,1)+" : "+tmpKey.substring(1));
					count++;
				}
				
			}
			
		}
		
		//System.err.println("dis size => "+count);
		
		return pos;
	}
	
	public String swapKey(String key) throws RankPattException{
		if(key.length()>2) throw new RankPattException(Constants.KEYTOOLONG);
		key = key.substring(1) + key.substring(0,1);
		return key;
	}
	
	private boolean isDoublePair(String key) throws RankPattException{
		if(key.length()>2) throw new RankPattException(Constants.KEYTOOLONG);
		boolean boolRet = false;
		if(key.substring(1).equals(key.substring(0,1))) boolRet = true;
		return boolRet;
	}
}
