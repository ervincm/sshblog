<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>   
<%@page import="javax.sql.*" %>   
<%@page import="javax.naming.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%

  request.setCharacterEncoding("UTF-8");
  response.setCharacterEncoding("UTF-8");
  Connection conn = null;  //数据库连接  
  PreparedStatement pstmt = null;  //数据库预处理  
  ResultSet rs = null;  //查询要处理结果集  
  boolean flag = false;  //保存标记  
  String name= null;    //保存真实姓名  
    try {  Context initContext = new InitialContext();   
        Context envContext  =   
            (Context)initContext.lookup("java:/comp/env");   
        DataSource ds =   (DataSource)envContext.lookup("jdbc/manager_system");   
        conn = ds.getConnection(); 
        }catch (Exception e){}
  %>  
  <%  
    
    String sql = "SELECT * FROM administrator";  
    pstmt  = conn.prepareStatement(sql);  
    
    rs = pstmt.executeQuery();  
    if(rs.next()){ 
        flag =true;  
        name= rs.getString("name");  
    }  
    out.println(sql+"*"+flag);
  %>
</body>
</html>