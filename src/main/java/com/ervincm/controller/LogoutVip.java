package com.ervincm.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ervincm.service.Service;

/**
 * Servlet implementation class LogoutVip
 */
@WebServlet("/LogoutVip")
public class LogoutVip extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutVip() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 接收信息
		String username = request.getParameter("userName");
		// username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
		String userMac = request.getParameter("userMac");
		String vipType = request.getParameter("vipType");

		System.out.println(username + "--" + userMac + "--" + vipType);

		Boolean flag = false;
		if (username!=null&&userMac != null&&vipType!=null) {
			// 新建服务对象
			Service serv = new Service();

			// 验证处理
			flag = serv.logoutVip(username, userMac, vipType);

			if (flag) {
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
		if (flag) {
			out.print("success");
		} else {
			out.print("fail");
		}
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

}
