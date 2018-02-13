<% 
String[] bookNames = (String[]) request.getAttribute("bookNames");
%>
<html>
  <head>
    <title>Book Stats</title>
  </head>

  <body bgcolor=white>

  	<form action="bookstatistics" method="post">
  		<p>
  		<b>Select a Book:</b><br>
  		<% for (String bookName : bookNames) { %>
  		<input type="radio" name="book" value="<%= bookName %>"><%= bookName %><br>
  		<% } %>
  		<input type="submit" value="submit">
  	</form>

  </body>
</html> 