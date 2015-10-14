package com.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.google.gson.Gson;

@Controller
@Scope(value="prototype")
@ParentPackage(value="struts-default")
@Namespace(value="/")
@Action(value="loginAction",results={
	
})
public class LoginAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244836157202972448L;

	public String loginSystem() throws Exception{
		
		Map<String,Object> dataMap=new HashMap<String,Object>();
		dataMap.put("flag", true);
		dataMap.put("message", "登录成功");
		
		System.out.println(dataMap);
		Gson gson=new Gson();
	    PrintWriter out=response.getWriter();
	    String jsonStr=gson.toJson(dataMap);
	    
	    System.out.println(jsonStr);
	    out.print(jsonStr);
	    out.flush();
	    out.close();
	    
	    return NONE;
	    
	    
	}
	
}
