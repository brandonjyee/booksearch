<% 
Long numResults = (Long) request.getAttribute("numResults"); 
String[] emotions = (String[]) request.getAttribute("emotions");
String[] definitions = (String[]) request.getAttribute("definitions");
String[] physicalSignals = (String[]) request.getAttribute("physicalSignals");
String[] internalSensations = (String[]) request.getAttribute("internalSensations");
String[] mentalResponses = (String[]) request.getAttribute("mentalResponses");
String[] acuteOrLongTermCues = (String[]) request.getAttribute("acuteOrLongTermCues");
String[] relatedEmotions = (String[]) request.getAttribute("relatedEmotions");
String[] suppressedCues = (String[]) request.getAttribute("suppressedCues");
String[] writerTips = (String[]) request.getAttribute("writerTips");
%>
<html>
  <head>
    <title>Emotion Thesaurus</title>
  </head>

  <body bgcolor=white>

<body bgcolor=white>
  	Number of Results: <%= numResults %> (Display limit: 100)
	<br><br>
	<!--  <div style="width:100%;overflow:scroll;"> -->
	<table border="1">
		<tr>
			<th>Result</th>
			<th>Emotion</th>
			<th>Definition</th>
			<th>Physical Signals</th>
			<th>Internal Sensations</th>
			<th>Mental Responses</th>
			<th>Acute or Long-Term Cues</th>
			<th>Related Emotions</th>
			<th>Suppressed Cues</th>
			<th>Writer's Tip</th>
		</tr>
		<% for (int i = 0; i < numResults; i++) { %>
		<tr>
			<td><%= i+1 %></td>
			<td><%= emotions[i] %></td>
			<td><%= definitions[i] %></td>
			<td><pre><%= physicalSignals[i] %></pre></td>
			<td><pre><%= internalSensations[i] %></pre></td>
			<td><pre><%= mentalResponses[i] %></pre></td>
			<td><pre><%= acuteOrLongTermCues[i] %></pre></td>
			<td><%= relatedEmotions[i] %></td>
			<td><pre><%= suppressedCues[i] %></pre></td>
			<td><%= writerTips[i] %></td>
		</tr>
		<% } %>
	</table>
	<!--  </div> -->
	
  </body>
</html> 