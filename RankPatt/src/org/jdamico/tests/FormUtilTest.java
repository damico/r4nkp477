package org.jdamico.tests;

import org.jdamico.dataobjects.util.FormUtil;

import junit.framework.TestCase;

public class FormUtilTest extends TestCase {

	public void testCreateForm() throws Exception {
		FormUtil.getInstance().createForm(new String[] {"Owner:jose","2:1","2:2","3:3","4:4","5:5"});
		//FormUtil.getInstance().distribute();
		System.out.println(FormUtil.getInstance().swapKey("AB"));
	}

}
