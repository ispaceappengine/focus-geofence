<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <link rel="stylesheet" type="text/css" href="css/css.css">
 <link rel="icon" href="favicon.ico type="image/vnd.microsoft.icon">
 <link rel="icon" href="favicon.ico?v=2 type="image/vnd.microsoft.icon">
<title>Focusgeofence - Configuration</title>

</head>
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
    <th class="tg-8zau" colspan="2">1. Configure focusgeofence</th>
  </tr>
  <tr>
    <td class="tg-031e" colspan="2"><p>
    Configure the focusgeofence web service by editing the fields. 
    The settings you provide will be stored on the server. In many cases, the default parameters will suit your needs.<br><br>
	Click the "Save configuration" button to store your edits on the server.</p></td>        
  </tr> 
  <tr>
    <td class="tg-f2ue">SOS server URL    
    </td>
    <td class="tg-z2zr">
    <input id="urlSosService" type="text" name="urlSosService" size="65" placeholder="provide a url for the SOS" 
    value="${urlSosService}"  />
    <a  class="tooltip">
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
    <td class="tg-i7wz">WFS URL to get only BoundingBox</td>
    <td class="tg-031e"><input id="urlWfsBoundingBox" type="text" name="urlWfsBoundingBox" size="65" placeholder="provide a url to a WFS to get only bounding box" 
    value="${urlWfsBoundingBox}"  />    
        
<a class="tooltip">
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
    <td class="tg-i7wz">Abstract-Description</td>
    <td class="tg-031e">
    <textarea id="_abstract" name="_abstract" cols="49" rows="3" placeholder="Textual description of the service">${cdc.abstract_text}</textarea> 
  </tr>-->
  <tr>
    <td class="tg-f2ue">WFS URL to get polygons</td>
    <td class="tg-z2zr">
    <input id="urlWfsPolygons" name="urlWfsPolygons" size="65" placeholder="provide a url to a WFS to get polygons" 
    value= "${urlWfsPolygons}"/> 
           
<a  class="tooltip">
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
    <td></td>
    <td align="right">  <input formaction="<%=request.getContextPath()%>/ConfigurationHandler" formmethod="POST" type="submit" value="Save Configuration"/> </td>
                    
  </tr>  
</table>
</form>
</body>
</html>