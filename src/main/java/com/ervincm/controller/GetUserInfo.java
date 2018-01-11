package com.ervincm.controller;

import com.ervincm.entity.VipUsersInfo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ervincm.service.Service;
/**
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 接收信息
		String username = request.getParameter("userName");
		// username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String userMac = request.getParameter("userMac");
		
		System.out.println(username + "--" + userMac );

		VipUsersInfo vipUsersInfo = null;

		if (username!=null&&userMac != null) {
			// 新建服务对象
			Service serv = new Service();

			// 验证处理
			vipUsersInfo = serv.getVipUsersInfo(userMac);

			if (vipUsersInfo != null) {
				System.out.print("Succss");
				
				System.out.print(vipUsersInfo.toString());
				// response.sendRedirect("welcome.jsp");
			} else {
				System.out.print("Failed");
			}
		}

		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		if (vipUsersInfo == null) {
			
			
			
		} else {
			out.print(vipUsersInfo.toString());
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
