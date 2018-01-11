package com.ervincm.controller;

import com.ervincm.entity.VipCount;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ervincm.service.Service;



/**
 * Servlet implementation class ChangeVipCount
 */
@WebServlet("/ChangeVipCount")
public class ChangeVipCount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeVipCount() {
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
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 新建服务对象
		Service serv = new Service();

		// 接收注册信息
		String username = request.getParameter("userName");

		String userMac = request.getParameter("userMac");
		String vipType = request.getParameter("vipType");
		System.out.println(username + "--" + userMac + "--" + vipType);

		// 验证处理
		VipCount vipCount = serv.changeVipCount(username, userMac, vipType);
		if (vipCount != null) {
			System.out.print("Succss");
			request.getSession().setAttribute("username", username);
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("Failed");
		}

		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("用户名：" + vipCount.getName());
		out.print("密码：" + vipCount.getPassword());
		out.print("infos：" + vipCount.toString());
		out.flush();
		out.close();
	}

}
