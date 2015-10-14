<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${path}/jquery-js/jquery.min.js"></script>
<title>首页</title>
<script type="text/javascript">
    function loginSystem(){
    	
    	 var url="${path}/loginAction!loginSystem";
    	 jQuery.post(url,function(jsonData){
    		 
    		 alert(jsonData.flag);
    		 alert(jsonData.message);
    		 
    		 
    	 },"json");
    	
    	
    }

</script>
</head>
<body>

  <form>
    <table>
       <tr>
          <td>用户名:</td>
          <td><input type="text" style="width:200px;"/></td>
       </tr>
       <tr>
          <td>密码</td>
          <td><input type="password" style="width:200px;"/></td>
       </tr>
       <tr>
          <td><input type="button" value="登录" onclick="loginSystem()"/></td>
          <td>&nbsp;</td>
       </tr>
      </table>
  </form>
</body>
</html>