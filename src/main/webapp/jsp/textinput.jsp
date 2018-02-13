<% 
String[] bookNames = (String[]) request.getAttribute("bookNames");
String servletName = (String) request.getAttribute("");
%>
<html>
  <head>
    <title>Blackbird Tools</title>
  </head>

  <body bgcolor=white>

  	<form action="<%= servletName %>" method="post">
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