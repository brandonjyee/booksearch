<% 
String[] outputArray = (String[]) request.getAttribute("outputArray"); 
%>

<html>
  <head>
    <title></title>
  </head>

  <body bgcolor=white>
  <% for (String entry : outputArray) { %>
  <%= entry %> <br>
  <% } %>

  </body>
</html> 