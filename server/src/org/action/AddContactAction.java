package org.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.store.Contact;
import org.store.HBaseUtil;
import org.store.Sms;

import com.opensymphony.xwork2.ActionSupport;

public class AddContactAction  extends ActionSupport implements
ServletRequestAware, ServletResponseAware, ServletContextAware{
	private  ServletContext context;
	private HttpServletRequest request;
	private HttpServletResponse response; 
	public void setServletContext(ServletContext arg0) {
		this.context=arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;

	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;   
	}
	@Override
	public String execute() throws Exception {
		request.setCharacterEncoding("utf-8"); 
		String userName = request.getParameter("username");
		String jsonData = request.getParameter("jsondata");
		if(userName==null||jsonData==null){
			return null;
		}
		Contact contact=new Contact();
		contact.storeToHbase(userName, jsonData);
		String resultJson=contact.getContactJsonData(userName);
		response.setCharacterEncoding("utf-8");
		PrintWriter pw=response.getWriter();
		pw.write(resultJson); 
		return null;
	}
}
