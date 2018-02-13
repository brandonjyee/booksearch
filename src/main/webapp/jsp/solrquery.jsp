<%// page import="java.util.List" %>
<% 
//List<String> bookNames = (List<String>) request.getAttribute("bookNames");
String[] bookNames = (String[]) request.getAttribute("bookNames");
%>
<html>
  <head>
    <title>Book Query</title>
  </head>

  <body bgcolor=white>

  	<form action="bookquery" method="post">
  		<b>Enter search words</b><br>
  		<input type="text" name="query" size="50px">
  		<input type="submit" value="submit">
  		<p>
  		<b>Select Books To Search On:</b><br>
  		<% for (String bookName : bookNames) { %>
  		<input type="checkbox" name="books" value="<%= bookName %>"><%= bookName %><br>
  		<% } %>
  	</form>

  </body>
</html> 