<%@page import="utility.LoadOnStartAppConfiguration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" />
    <link rel="stylesheet" href="css/main.css" />	
 	<link rel="stylesheet" type="text/css" href="css/css.css">
 	<link rel="icon" href="favicon.ico type="image/vnd.microsoft.icon">
 	<link rel="icon" href="favicon.ico?v=2 type="image/vnd.microsoft.icon">
  	
  	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  	  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
 	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
 	<script src="main.js"></script>
<title>Focusgeofence - WebSocket</title>
</head>
<%
System.out.println("This is websocket.jsp");
String path_geoserverwfs =LoadOnStartAppConfiguration.urlGeoserverWfsService;
//String timeRepeatInvervalInSeconds = LoadOnStartAppConfiguration.timeRepeatInvervalInSeconds;
String path_websocket = LoadOnStartAppConfiguration.urlWebSocketURI;
Boolean active_urlWithinGeofenceWfsService = LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService;
%>
<body onload="main()">
<br><br><br><br>
<div align="center">
<a href="/focusgeofence/">
<img title="" src="img/focus_logo.png"  alt="Logo" >
</a>
</div>
<form  enctype="application/x-www-form-urlencoded">

<table class="tgneu" align="center">
 	 <col width="380">
 	 <col width="680">
  <tr>
    <td  colspan="2" align="left">
	<div align="left">
		<a class="noUnderLine" href="/focusgeofence/">
		&lt;&lt; back to start
		</a>
	</div>
	</td>
  </tr>
</table>

<table class="tg" align="center">
 	 <col width="380">
 	 <col width="680">

  <tr>
    <th class="tg-8zau" colspan="2">3. Service definition for WebSockets</th>
  </tr>
  <tr>
    <td class="tg-031e" colspan="2"><p>Please start the service, if you wish to send data to the provided WebSocket URL.<br><br>

    </p></td>
  </tr> 
  <tr>
    <td class="tg-f2ue">WebSocket URL </td>
    <td class="tg-z2zr"> 
    <!-- <input readonly="readonly" id="_url" type="text" name="_url" size="65" placeholder="provide a url to a service where to get data from" value="http://193.171.127.88:8032/?Cmd=Query&defart=k&version=0&hauptreihe=true"  />
        -->
        <label for="_url" id="_url" type="text" name="_url" class="configFileClass" value="asdf" ><%=path_websocket %> </label>
    <a class="tooltip">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> If the WebSocket service is activated, then all matching data will be sent to a WebSocket chatroom, that is located at the provided URL. Change value in the configuration (step 1).  
		<br>		
	</span> 
</a>
    </td>
  </tr>

  <!-- executeInsertObservationServicesOnStartUp-->
  <!-- <tr>
    <td class="tg-i7wz">Abstract-Description</td>
    <td class="tg-031e">
    <textarea id="_abstract" name="_abstract" cols="49" rows="3" placeholder="Textual description of the service">${cdc.abstract_text}</textarea> 
  </tr>-->
<!-- 
  <tr>
    <td class="tg-i7wz">Request time interval in seconds for e-mails</td>
    <td class="tg-031e">
            
      <label for="_timeRepeatInvervalInSecondsl" id="_timeRepeatInvervalInSeconds" type="text" name="_timeRepeatInvervalInSeconds" class="configFileClass" value="timeRepeatInvervalInSeconds" >
       
      
      </label>

<a class="tooltip">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> 
		<b></b>
		Displays the repeat time interval for requesting new E-Mails and subsequently sending the extracted data to the Geoserver WFS. 
<br>		
	</span> 
</a>    
    </td>
  </tr>
   -->
  <!-- 
    <tr>
    <td class="tg-f2ue">Service Definition for InsertSensor</td>
    <td class="tg-z2zr"><p>some text more.some text more.some text more.some text more.some text more.some text more.some text more.some text more.some text more.</p></td>
  </tr>
  <tr>
    <td class="tg-i7wz">Service Definition for InsertObservation</td>
    <td class="tg-031e"><p>some text some text</p>      
   
  </td>
  </tr> 
  <tr>
    <td class="tg-f2ue">Active InsertObservation Services</td>
    <td class="tg-z2zr"><p>some text some text</p>
       
  </td>
  </tr>
   -->  
  <tr>
    
    <%if(active_urlWithinGeofenceWfsService){ %>
    <td>There is already a service running!<br/> Do you want to stop it?</td>
    <td align="right">  <input formaction="<%=request.getContextPath()%>/websocketHandler" formmethod="POST" type="submit" value="Stop active Service"/> </td>
                    <%}else{ %>
    <td></td>
    <td align="right">  <input formaction="<%=request.getContextPath()%>/websocketHandler" formmethod="POST" type="submit" value="Start Service"/> </td>                
                    <%} %>
  </tr>  
</table>
</form>
<br>
	<!-- <div id="map"></div>

	<script src="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.js"></script>    
    <script src="main.js"></script>
     -->
    
    <button data-toggle="collapse" data-target="#demo">Collapsible</button>

		<div id="demo2" class="collapse">
			Lorem ipsum dolor text....
		</div>
    


</body>
</html>