package com.ervincm.controller;

import com.ervincm.entity.RegisterUsersInfo;
import com.ervincm.service.Service;
import com.ervincm.util.FindCityByIP;
import com.ervincm.util.StringAndDate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Register
 */
@WebServlet("/RegisterVip")
public class RegisterVip extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String city = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterVip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 新建服务对象
		Service serv = new Service();

		// 接收注册信息
		String username = request.getParameter("userName");

		String userMac = request.getParameter("userMac");
		String ip = request.getParameter("ip");
		String vipType = request.getParameter("vipType");
		String registerTime1= request.getParameter("registerTime");
		String deadlineTime1 = request.getParameter("deadlineTime");
		String mygetip = new FindCityByIP().getRemortIP(request);
		System.out.println(username + "--" + userMac + "--" + ip + "--"
				+ mygetip);
		city = new FindCityByIP().getAddressByIP(ip);
		if (city == null) {
			System.out.print("city==null");
		}
		
		Date registerTime= StringAndDate.strToDate(registerTime1);
		Date deadlineTime=StringAndDate.strToDate(deadlineTime1);
		boolean reged = false;
		if (username!=null&&userMac != null&&vipType!=null&&registerTime!=null&&deadlineTime!=null) {
			RegisterUsersInfo registerInfo = new RegisterUsersInfo(username, userMac, ip,
					vipType, registerTime, deadlineTime, city);
			// 验证处理
			reged = serv.registerVip(registerInfo);
			if (reged) {
				System.out.print("Succss");
				request.getSession().setAttribute("username", username);
				// response.sendRedirect("welcome.jsp");
			} else {
				System.out.print("Failed");
			}

		}

		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (reged) {
			out.print("success");
		} else {
			if(Service.registerState==Service.ALREADY_REGISTER)
			{
				out.print("fail:"+"ALREADY_REGISTER");
				Service.registerState=0;
			}
			if(Service.getVipState==Service.IN_WAIT_TABLE){
				out.print("fail:"+"IN_WAIT_TABLE");
				Service.getVipState=0;
			}
			if(Service.getVipState==Service.WAIT_TABLE_FULL){
				out.print("fail:"+"WAIT_TABLE_FULL");
				Service.getVipState=0;
			}
	
		}
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the Server let.
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		// 测试为何手机端中文乱码，电脑正常
		// System.out.println("u1--"+username);
		// System.out.println("u2--"+username);

	}

}
