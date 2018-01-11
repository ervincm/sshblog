package com.ervincm.controller;

import com.ervincm.entity.VipCount;
import com.ervincm.service.Service;
import com.ervincm.util.FindCityByIP;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class Login
 */
@WebServlet("/LoginVip")
public class LoginVip extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static {

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginVip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 接收信息
		String username = request.getParameter("userName");
		// username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String userMac = request.getParameter("userMac");
		String vipType = request.getParameter("vipType");
		String mygetip = new FindCityByIP().getRemortIP(request);
		System.out.println(username + "--" + userMac + "--" + vipType+"--"+mygetip);

		VipCount vipCount = null;

		if (username!=null&&userMac != null&&vipType!=null) {
			// 新建服务对象
			Service serv = new Service();

			// 验证处理
			vipCount = serv.loginVip(username, userMac, vipType);

			if (vipCount != null) {
				System.out.print("Succss");
				
				System.out.print(vipCount.toString());
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
		if (vipCount == null) {
			if(Service.registerState==Service.ALREADY_REGISTER)
			{
				System.out.println("fail:"+"ALREADY_REGISTER");
				out.print("fail:"+"ALREADY_REGISTER");
				Service.registerState=0;
			}
			if(Service.getVipState==Service.IN_WAIT_TABLE){
				System.out.println("fail:"+"IN_WAIT_TABLE");// 进入等待队列
				out.print("fail:"+"IN_WAIT_TABLE");
				Service.getVipState=0;
			}
			if(Service.getVipState==Service.WAIT_TABLE_FULL){
				System.out.println("fail:"+"WAIT_TABLE_FULL");// 次月的等待人数满，不再排队，
				out.print("fail:"+"WAIT_TABLE_FULL");
				Service.getVipState=0;
			}
			if(Service.getVipState==Service.NO_REGISTER){
				System.out.println("fail:"+"NO_REGISTER");// 未注册为会员用户
				out.print("fail:"+"NO_REGISTER");
				Service.getVipState=0;
			}
			if(Service.getVipState==Service.NO_REGISTER_TENCENT_VIP){
				System.out.println("fail:"+"NO_REGISTER_TENCENT_VIP");
				out.print("fail:"+"NO_REGISTER_TENCENT_VIP");
				Service.getVipState=0;
			}
			if(Service.getVipState==Service.NO_REGISTER_IQIYI_VIP){
				System.out.println("fail:"+"NO_REGISTER_IQIYI_VIP");
				out.print("fail:"+"NO_REGISTER_IQIYI_VIP");
				Service.getVipState=0;
			}
			if(Service.getVipState==Service.NO_REGISTER_YOUKU_VIP){
				System.out.println("fail:"+"NO_REGISTER_YOUKU_VIP");
				out.print("fail:"+"NO_REGISTER_YOUKU_VIP");
				Service.getVipState=0;
			}
			if(Service.getVipState==0){
				System.out.println("fail:"+"0");
				out.print("fail:"+"CHANGE_VIP_FAIL");
				Service.getVipState=0;
			}
			if(Service.switchTimes==Service.OUT_OF_SWITCH_TIMES){
				System.out.println("fail:"+"OUT_OF_SWITCH_TIMES");
				out.print("fail:"+"OUT_OF_SWITCH_TIMES");
				//Service.getVipState=0;
			}
			if(Service.changeVipState==Service.CHANGE_VIP_FAIL){
				System.out.println("fail:"+"CHANGE_VIP_FAIL");
				out.print("fail:"+"CHANGE_VIP_FAIL");
				Service.changeVipState=0;
			}
		} else {
			out.print(vipCount.toString());
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
