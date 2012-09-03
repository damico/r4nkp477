package org.jdamico.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.jdamico.dataobjects.FormObj;
import org.jdamico.dataobjects.SimData;
import org.jdamico.dataobjects.util.FormUtil;
import org.jdamico.rankpatt.components.FindRankPat;

public class FindRankPattTest extends TestCase {

	public void testFindSimPat() throws Exception{
		
		String baseEntity = "jose";
		
		FindRankPat findSp = new FindRankPat();
		ArrayList<SimData> resultArray = findSp.process(baseEntity, plotSampleData());
		
		System.out.println("Results for: Which entity has more similarity with entity '" + baseEntity + "' ?");
		System.out.println("The lowest value represents the biggest similarity.\n");
		
		for(int i = 0; i < resultArray.size(); i++){
			System.out.println("SimData: "+resultArray.get(i).getEntityName() + " = "+resultArray.get(i).getDistTotalSum());
		}
		
	}
	
	
	private Map<String, FormObj> plotSampleData() throws Exception{
		
		Map<String, FormObj> formMap = new HashMap<String, FormObj>();
		formMap = FormUtil.getInstance().add2Map(formMap, FormUtil.getInstance().createForm(new String[] {"Owner:jose",		"1:1","2:2","3:3","4:4","5:5"}));
		formMap = FormUtil.getInstance().add2Map(formMap, FormUtil.getInstance().createForm(new String[] {"Owner:jorge",	"1:4","2:5","3:3","4:3","5:5"}));
		formMap = FormUtil.getInstance().add2Map(formMap, FormUtil.getInstance().createForm(new String[] {"Owner:paulo",	"1:3","2:2","3:5","4:4","5:5"}));
		formMap = FormUtil.getInstance().add2Map(formMap, FormUtil.getInstance().createForm(new String[] {"Owner:adalberto","1:4","2:4","3:5","4:4","5:5"}));
		formMap = FormUtil.getInstance().add2Map(formMap, FormUtil.getInstance().createForm(new String[] {"Owner:dulce",	"1:2","2:2","3:3","4:4","5:5"}));

		
		return formMap;
	}
	
	
		
		
		
}
