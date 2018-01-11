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
 * Servlet implementation class ChangeVipPwd
 */
@WebServlet("/ChangeVipPwd")
public class ChangeVipPwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeVipPwd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 新建服务对象
		Service serv = new Service();

		// 接收注册信息
		String vipName = request.getParameter("vipName");
		String vipPwd = request.getParameter("vipPwd");
		String vipType = request.getParameter("vipType");
	
		System.out.println(vipName + "--" + vipPwd );

		// 验证处理
		Boolean flag = serv.changeVipPwd(vipName, vipPwd,vipType);
		if (flag ) {
			System.out.print("Succss");
			request.getSession().setAttribute("vipName", vipName);
			// response.sendRedirect("welcome.jsp");
		} else {
			System.out.print("Failed");
		}

		// 返回信息
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print("用户名：" + vipName);
		out.print("密码：" + vipPwd);
		out.flush();
		out.close();
	}

}
