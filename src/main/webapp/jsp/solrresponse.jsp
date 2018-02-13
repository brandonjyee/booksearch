<% 
Long numResults = (Long) request.getAttribute("numResults"); 
String[] resultEntries = (String []) request.getAttribute("resultEntries");
%>

<html>
  <head>
    <title>Book Query</title>
  </head>

  <body bgcolor=white>
  	Number of Results: <%= numResults %> (Display limit: 100)
	<br><br>
	<table border="1">
		<% 
		int i = 1;
		for (String result : resultEntries) { %>
		<tr>
			<td><%= i %></td>
			<td><%= result %></td>
		</tr>
		<% 
		i++;
		} %>
	</table>

  </body>
</html> 