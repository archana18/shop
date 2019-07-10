<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>About US</title>
<style>
tr:hover {
	background-color: #e6eeef;
}
</style>
</head>
<body bgcolor="#f0f5f5">
	<%@include file="include/header.jsp"%>
	
	<h3>
		ABOUT US
	</h3>
	
	<p style="color: black">Ecommerce shopping website</p>
	
	
	
	<div align="center">
		<form action="feedback" method="post">
			<table align="center">
				<tr align="center">
					<td>Email Id:<input type="text" name="emailId" maxlength="24"
						size="25" required></td>
				</tr>
				<tr align="center">
					<td>Feedback: <br> <textarea rows="2" cols="60"
							name="message" maxlength="100" required></textarea> <input
						id="submit" type="submit" value="Share">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>