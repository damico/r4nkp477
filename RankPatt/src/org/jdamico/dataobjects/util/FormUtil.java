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

public class FormUtil {
	private static final FormUtil INSTANCE = new FormUtil();
	public static FormUtil getInstance(){
		return INSTANCE;
	}
	
	public FormObj createForm(String[] formData) throws RankPattException{
		
		
		Map<Integer, Integer> rankMap = new HashMap<Integer, Integer>();
		String owner = null;
		/* 
		 * Check for at least 2 items into array
		 * Check for only one valid Owner
		 */ 
		int count = 0;
		count = formData.length;
		if(count == 0 || count == 1) throw new RankPattException(Constants.INVALIDLST);
		for(int i=0; i<count; i++){
			String tmpItem = formData[i].trim();
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
	
	public Map<String, FormObj> add2Map(Map<String, FormObj> formMap, FormObj form) throws RankPattException{
		
		if(formMap.containsKey(form.getFormOwner())) throw new RankPattException(Constants.CONTAINSKEYFORM);
		
		formMap.put(form.getFormOwner(), form);
		
		return formMap;
	}
	
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
	
	public boolean isDoublePair(String key) throws RankPattException{
		if(key.length()>2) throw new RankPattException(Constants.KEYTOOLONG);
		boolean boolRet = false;
		if(key.substring(1).equals(key.substring(0,1))) boolRet = true;
		return boolRet;
	}
}
