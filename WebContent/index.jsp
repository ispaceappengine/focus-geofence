<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="css/css.css">
<link rel="icon" href="favicon.ico?v=2 type="image/vnd.microsoft.icon">
<title>Focus Geofence</title>
</head>
<body>
<br><br><br><br>
<div align="center">
<a href="/focusgeofence/">
<img title="" src="img/focus_logo.png"  alt="Logo" >
</a>
</div>
<br><br>
<table class="tg" align="center">
	 <col width="380">
 	 <col width="680">
  <tr>
    <th class="tg-8zau" colspan="2">Service Description for focusgeofence</th>
  </tr>
     <tr>
    <td class="tg-031e" colspan="2"><p>
    The purpose of the primary web service is to determine, if a forest truck is unloading tree logs. Therefore it is checked, if the "loading event" (based on the SensorAppIII vibration indicator) is taking place in a predefined geographical area (e.g log area saw mill).
    The purpose of the secondary web service is to determine, if a forest truck is located within a predefined geofence.  <br><br>
     Send your data with a POST method to: <a href=${pageContext.request.requestURL}service>${pageContext.request.requestURL}service</a>.
     <!-- You can use this web service (focusgeofence) to check if a point is in a polygon. -->
    <br><br>
    Please use the configuration options to provide additional information. You will be guided through the process on each of the subsites in more detail.</p></td>
  </tr> 
  
    <tr>
    <td class="tg-f2ue"><a href="${pageContext.request.requestURL}unloading">1. Service configuration for log 'unloading' areas WFS link</a></td>
    <td class="tg-z2zr"> <p>
    A service specifically associated with <i>'unlaoding'</i>. Provide the initial settings for the Geoserver WFS services.</p></td>
  </tr>
  
      <tr>
    <td class="tg-i7wz"><a href="${pageContext.request.requestURL}withingeofence">2. Service configuration for 'within drivetime[15min]' geofence WFS link</a></td>
    <td class="tg-031e"> <p>
    A service specifically associated with <i>'within geofence'</i> and WebSockets. Provide the initial settings for the Geoserver WFS services and the WebSocket.</p></td>
  </tr>  

  <!-- 
    <tr>
    <td class="tg-i7wz"><a href="/tstp2sos/zeitreihen">2. Service definition for GetAllTimeSeries</a></td>
    <td class="tg-031e"> <p>
    Query the TSTP server to retrieve all relevant times series (ZRID). 
    Be aware that each time series is treated as a unique sensor.</p></td>
  </tr>
  <!-- 
  <tr>
    <td class="tg-i7wz">Abstract-Description</td>
    <td class="tg-031e">
    <textarea id="_abstract" name="_abstract" cols="49" rows="3" placeholder="Textual description of the service">${cdc.abstract_text}</textarea> 
  </tr>   
    <tr>
    <td class="tg-f2ue"><a href="/tstp2sos/is">3. Service definition for InsertSensor</a></td>
    <td class="tg-z2zr"><p> 
    Use this operation to register a time series/sensor in the OGC SOS. Choose the phenomenon/parameter 
    (e.g. temperature) you want to register.</p>    </td>
  </tr>
  <tr>
    <td class="tg-i7wz"><a href="/tstp2sos/iobs">4. Service definition for InsertObservation</a></td>
    <td class="tg-031e"><p> Create an InsertObservation service to periodically query the TSTP server for new measurement values. 
    The returned data will be automatically inserted into the OGC SOS service. </p>   
  </td>
  </tr>
  -->
  <tr>
    <td></td>
    <td align="right"> <!-- <input formaction="/tstp2sos/send-is" formmethod="POST" type="submit" value="Send InsertSensor"/> --></td>
                    
  </tr>
  
</table>

<div class="container">
	<div id ="logos" class="center-logos">
			<a  href="http://www.mm-holz.com/" target="_blank"><img class="logoMM" src="img/mm.png" /></a>
			<a href="http://ispace.researchstudio.at/" target="_blank"><img class="logoRSA" src="img/rsa2.png" /></a>
	</div>
</div>

  
</body>
</html>