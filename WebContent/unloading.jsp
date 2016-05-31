<%@page import="utility.LoadOnStartAppConfiguration"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="http://cdn.leafletjs.com/leaflet/v0.7.7/leaflet.css" />
 <link rel="stylesheet" type="text/css" href="css/css.css">
 <link rel="icon" href="favicon.ico type="image/vnd.microsoft.icon">
 <link rel="icon" href="favicon.ico?v=2 type="image/vnd.microsoft.icon">
 <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<title>Focusgeofence - unloading WFS</title>
</head>
<%
System.out.println("This is geoserverwfs.jsp");
String path_geoserverwfs = LoadOnStartAppConfiguration.urlWfsPolygons;
String timeRepeatInvervalInSeconds = "LoadOnStartAppConfiguration.timeRepeatInvervalInSeconds";
Boolean active_urlUnloadingWfsService = LoadOnStartAppConfiguration.active_urlUnloadingWfsService;
%>
<body>
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
    <th class="tg-8zau" colspan="2">1. Service configuration for 'unloading areas' WFS</th>
  </tr>
  <tr>
    <td class="tg-031e" colspan="2"><p>This service checks whether the truck is located within a predefined geofence (geographic polygon) and updates the <i>loading</i> status, depending upon the the predefined geofence. 
   <br> Please provide the necessary URLs, if you wish to persist your data in the SOS. <br><br>


    </p></td>
  </tr> 
  
  <tr>
    <td class="tg-f2ue"><abbr title="Sensor Observation Service">SOS</abbr> server URL    
    </td>
    <td class="tg-z2zr" style="font-size: 14px;">
    <input id="urlSosService" type="text" name="urlSosService" size="65" placeholder="provide a url for the SOS" 
    value="${urlSosService}"  />
    <a  class="tooltip_custom">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> 
		<b></b>	
			Provide the SOS server URL of your system. Your changes will be stored on the focusgeofence web service server. 
			The next time you start the web service, the edited URL will be automatically provided to you. 
			<br>		
	</span> 
</a></td>
  </tr>
  
    <tr>
    <td class="tg-i7wz">URL to get only BoundingBox of Polygons</td>
    <td class="tg-031e" style="font-size: 14px;"><input id="urlWfsBoundingBox" type="text" name="urlWfsBoundingBox" size="65" placeholder="provide a url to a WFS to get only bounding box" 
    value="${urlWfsBoundingBox}"  />    
        
<a class="tooltip_custom">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> 
		<b></b>
		Provide the Web Feature Service URL where from to get only the coordinates of the bounding box of the existing polygons. 
<br>		
	</span> 
</a> 

    </td>
  </tr>
  <!-- executeInsertObservationServicesOnStartUp-->
  <!-- <tr>
    <td class="tg-i7wz">Abstract-Description</
    <td class="tg-031e">
    <textarea id="_abstract" name="_abstract" cols="49" rows="3" placeholder="Textual description of the service">${cdc.abstract_text}</textarea> 
  </tr>-->
  <tr>
    <td class="tg-f2ue">URL to get Polygons</td>
    <td class="tg-z2zr" style="font-size: 14px;">
    <input id="urlWfsPolygons" name="urlWfsPolygons" size="65" placeholder="provide a url to a WFS to get polygons" 
    value= "${urlWfsPolygons}"/> 
           
<a  class="tooltip_custom">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> 
		<b></b>
		Provide the Web Feature Service URL where from to get the existing polygons.
		<br>		
	</span> 
</a>
    </td>
  </tr>  
  
  <!-- 
  <tr>
    <td class="tg-i7wz">WebSocket URL</td>
    <td class="tg-031e"><input id="urlWebSocketURI" type="text" name="urlWebSocketURI" size="65" placeholder="provide a url to a WebSocket" 
    value="${urlWebSocketURI}"  />    
        
		<a class="tooltip">
			<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
			<span> 
				<b></b>
				Provide the WebSocket URL. The data will be sent to this WS URL. 
		<br>		
			</span> 
		</a>    
    </td>
  </tr>  
   -->
  
    <tr>
    <td class="tg-i7wz" colspan="1">Change value from </td>
     <td class="tg-031e" style="font-size: 14px;">
     
     <input type="text" id="value_from" name="value_from" value="${value_from}"> </input> to <input type="text" id="value_to" name="value_to" value="${value_to}"></<input>
         
        
	 	<a class="tooltip_custom">
			<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
			<span> 
				<b></b>
				Provide the value which act as an indicator for unloading and will trigger a value change to the second provided value. [0: not loading, 1: loading, 2: unloading]
		<br>		
			</span> 
		</a>    
    </td>
   
  </tr>  
  
  
  <!-- 
  <tr>
    <td class="tg-f2ue">Geoserver WFS service URL </td>
    <td class="tg-z2zr"> 
    <!-- <input readonly="readonly" id="_url" type="text" name="_url" size="65" placeholder="provide a url to a service where to get data from" value="http://193.171.127.88:8032/?Cmd=Query&defart=k&version=0&hauptreihe=true"  />
        --><!-- 
        <label for="_url" id="_url" type="text" name="_url" class="configFileClass" value="asdf" ><%=path_geoserverwfs %> </label>
    <a class="tooltip">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> The TSTP server URL where from to request all existing time series. This field is readonly. The URL parameters may be edited by experts only! Change value in the configuration (step 1).  
		<br>		
	</span> 
</a>
    </td>
  </tr>-->

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
            
      <label for="_timeRepeatInvervalInSecondsl" id="_timeRepeatInvervalInSeconds" type="text" name="_timeRepeatInvervalInSeconds" class="configFileClass" value="timeRepeatInvervalInSeconds" ><%=timeRepeatInvervalInSeconds %> </label>

<a class="tooltip">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> 
		<b></b>
		Displays the repeat time interval for requesting new E-Mails and subsequently sending the extracted data to the Geoserver WFS. 
<br>		
	</span> 
</a>    
    </td>
  </tr> -->
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
    
    <%if(active_urlUnloadingWfsService){ %>
    <td>There is already a service running!<br/> Do you want to stop it?</td>
    <td align="right">  <input formaction="<%=request.getContextPath()%>/GeoserverwfsHandler" formmethod="POST" class="btn btn-colour a" type="submit" value="Stop active Service"/> </td>
                    <%}else{ %>
    <td></td>
    <td align="right">
      
    	<input formaction="<%=request.getContextPath()%>/GeoserverwfsHandler" formmethod="POST" class="btn btn-colour a" type="submit" value="Save Configuration and Start Service"/> 
    </td>                
                    <%} %>
                    
                    
  </tr>  
   <tr>
    <td></td>
    <td align="right"> <!-- <input formaction="/tstp2sos/send-is" formmethod="POST" type="submit" value="Send InsertSensor"/> --></td>
                    
  </tr>
</table>
</form>

<div class="container">
	<div id ="logos" class="center-logos">
			<a  href="http://www.mm-holz.com/" target="_blank"><img class="logoMM" src="img/mm.png" /></a>
			<a href="http://ispace.researchstudio.at/" target="_blank"><img class="logoRSA" src="img/rsa2.png" /></a>
	</div>
</div>
</body>
</html>