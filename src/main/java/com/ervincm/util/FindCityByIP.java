package com.ervincm.util;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindCityByIP {

 String city=null;
 
  public String getAddressByIP(String ip)
     {
         try
         {
             String strIP = ip;
             URL url = new URL( "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=" + strIP);
             URLConnection conn = url.openConnection();
             BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
             String line = null;
             StringBuffer result = new StringBuffer();
             while((line = reader.readLine()) != null)
             {
                 result.append(line);
             }
             reader.close();
             String str=result.toString();
             System.out.println(str);
             JSONObject jsonObject=JSONObject.fromObject(str);
             city=UnicodeToString(jsonObject.getString("city"));
             
             System.out.println(city);
         }
         catch( IOException e)
         {
             return "fail";
         }
         return city;
     }
  
 /*  
     *  普通类型的unicode转string  
     */    
      public  static  String  UnicodeToString(String  input)  {    
           Pattern pattern  =  Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
           Matcher  matcher  =  pattern.matcher(input);    
           char  ch;    
           while  (matcher.find()) 
           {    
               ch  =  (char)  Integer.parseInt(matcher.group(2),  16);    
               input  =  input.replace(matcher.group(1),  ch  +  "");    
           }    
           return  input;    
   }    
    /*  如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
    　　答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。*/
      public String getRemortIP(HttpServletRequest request) {
           if (request.getHeader("x-forwarded-for") == null) {  
               return request.getRemoteAddr();  
           }  
           return request.getHeader("x-forwarded-for");  
       }   
}
