<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ResourceBundle"  %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.francelabs.datafari.servlets.*" %>
<!DOCTYPE html>


<html lang="en">
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title>Capsule Configuration</title>
		<link rel="icon" type="image/png" href="images/bullet.png">
		<link rel="stylesheet" type="text/css" href="../css/main.css" media="screen" />
		<link rel="stylesheet" type="text/css" href="../css/admin.css" media="screen" />
	    <script type="text/javascript" src="../js/jquery-1.8.1.min.js"></script>
		<script type="text/javascript" src="../js/menu.js"></script>
	</head>
	<body class="gecko win">
	<jsp:include page="../header.jsp" />
	<jsp:include page="menu.jsp" />
	

    
<br /> <br />
<div class="adminConfig">
<% ResourceBundle resourceBundle = ResourceBundle.getBundle("com.francelabs.i18n.text", request.getLocale()); %>
	<% if(request.getAttribute("errorCapsule") != null) {%>	
	 <form method = post Action="Admin">
	 <fieldset>
 		<legend><%out.print(request.getAttribute("errorCapsule").toString());%></legend>
  		answer(yes to override): <input type="text"name="answer"id="answer" required/><br>
		 <input type="submit" value="Answer" />
	 </fieldset>
	 </form>
	 <% }else{ %>
<FORM method = post Action = "Admin">
 <fieldset>
  <legend>Capsule perso:</legend>
  keyword: <input type="text"name="keyword"id="keyword" required/><br>
  title: <input type="text"name="title"id="title" required/><br>
  content: <input type="text"name="value"id="value" required/><br>
  date beginning : <input type="date" name="dateB" id="dateB" /><br>
  date end : <input type="date" name="dateE" id="dateE" /><br>
  <input type="submit" value="Post" />

 </fieldset>
</FORM>
<%} %>
 
</div>


	<jsp:include page="../footer.jsp" />
</body>
</html>



