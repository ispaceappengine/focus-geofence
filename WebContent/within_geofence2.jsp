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
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
 <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
 
<title>Focusgeofence - within geofence WFS</title>
</head>
<%
System.out.println("This is within_geofence.jsp");
String path_geoserverwfs = LoadOnStartAppConfiguration.urlWfsPolygons;
String timeRepeatInvervalInSeconds = "LoadOnStartAppConfiguration.timeRepeatInvervalInSeconds";
Boolean active_withinGeofence = LoadOnStartAppConfiguration.active_urlWithinGeofenceWfsService;
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
    <th class="tg-8zau" colspan="2">2. Service configuration for 'within drivetime' geofence WFS</th>
  </tr>
  <tr>
    <td class="tg-031e" colspan="2"><p>Please start a service, if you wish to send data to the provided Geoserver WFS URL<br><br>

    </p></td>
  </tr> 

    <tr>
    <td class="tg-f2ue">URL to get only BoundingBox of Polygons</td>
    <td class="tg-z2zr"><input id="urlWfsBoundingBox_withinGeofence" type="text" name="urlWfsBoundingBox_withinGeofence" size="65" placeholder="provide a url to a WFS to get only bounding box" 
    value="${urlWfsBoundingBox_withinGeofence}"  />    
        
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
    <td class="tg-i7wz">URL to get Polygons</td>
    <td class="tg-031e">
    <input id="urlWfsPolygons_withinGeofence" name="urlWfsPolygons_withinGeofence" size="65" placeholder="provide a url to a WFS to get polygons" 
    value= "${urlWfsPolygons_withinGeofence}"/> 
           
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
  
  <tr>
    <td class="tg-f2ue">WebSocket URL</td>
    <td class="tg-z2zr"><input id="urlWebSocketURI" type="text" name="urlWebSocketURI" size="65" placeholder="provide a url to a WebSocket" 
    value="${urlWebSocketURI}"  />    
        
		<a class="tooltip_custom">
			<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
			<span> 
				<b></b>
				Provide the WebSocket URL. The data will be sent to this WS URL. 
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
        -->
        <!-- 
        <label for="_url" id="_url" type="text" name="_url" class="configFileClass" value="asdf" ><%=path_geoserverwfs %> </label>
    <a class="tooltip">
	<img title="" src="img/tooltip_icon.jpg" alt="tooltip" >
	<span> The TSTP server URL where from to request all existing time series. This field is readonly. The URL parameters may be edited by experts only! Change value in the configuration (step 1).  
		<br>		
	</span> 
</a>
    </td>
  </tr>
 -->


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
    
    <%if(active_withinGeofence){ %>
    <td>There is already a service running!<br/> Do you want to stop it?</td>
    <td align="right">  <input formaction="<%=request.getContextPath()%>/WithinGeofenceHandler" formmethod="POST" type="submit" value="Stop active Service"/> </td>
                    <%}else{ %>
    <td></td>
    <td align="right">  <input formaction="<%=request.getContextPath()%>/WithinGeofenceHandler" formmethod="POST" type="submit" value="Save Configuration and Start Service"/> </td>                
                    <%} %>
                    
                    
  </tr>  
</table>
</form>
<br/>

  <div class="container">
  <p align="center">For debugging purposes:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#WebSocketFeed" >Display WebSocket Feed</button></p>  
  	<div id="WebSocketFeed" class="collapse">
		


<table class="tgneu" align="center">
 	 <col width="380">
 	 <col width="680">
  <tr>
    <td  colspan="2" align="center">
	<div align="left">


<html>
<head>
    <title>Geofence Chat</title>
    <style type="text/css">
        input#chat {
            width: 410px
        }

        #console-container {
            width: 1024px;
        }

        #console {
            border: 1px solid #CCCCCC;
            border-right-color: #999999;
            border-bottom-color: #999999;
            height: 370px;
            overflow-y: scroll;
            padding: 5px;
            width: 100%;
        }

        #console p {
            padding: 0;
            margin: 0;
        }
    </style>
    <script type="text/javascript">
        var Chat = {};

        Chat.socket = null;

        Chat.connect = (function(host) {
            if ('WebSocket' in window) {
                Chat.socket = new WebSocket(host);
            } else if ('MozWebSocket' in window) {
                Chat.socket = new MozWebSocket(host);
            } else {
                Console.log('Error: WebSocket is not supported by this browser.');
                return;
            }

            Chat.socket.onopen = function () {
                Console.log('Info: WebSocket connection for geofence opened.');
                document.getElementById('chat').onkeydown = function(event) {
                    if (event.keyCode == 13) {
                        Chat.sendMessage();
                    }
                };
            };

            Chat.socket.onclose = function () {
            	console.log("onclose event: "+event.code);
                document.getElementById('chat').onkeydown = null;
                Console.log('Info: WebSocket closed.');
            };

            Chat.socket.onmessage = function (message) {
            	console.log(message.data);
                Console.log(message.data);
            };
        });

        Chat.initialize = function() {
            if (window.location.protocol == 'http:') {
                Chat.connect('ws://' + window.location.host + '/geo-websocket/geofence');
            
            } else {
                Chat.connect('wss://' + window.location.host + '/geo-websocket/geofence');
            }
        };

        Chat.sendMessage = (function() {
            var message = document.getElementById('chat').value;
            if (message != '') {
                Chat.socket.send(message);
                document.getElementById('chat').value = '';
            }
        });

        var Console = {};

        Console.log = (function(message) {
            var console = document.getElementById('console');
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            p.innerHTML = message;
            console.appendChild(p);
            while (console.childNodes.length > 25) {
                console.removeChild(console.firstChild);
            }
            console.scrollTop = console.scrollHeight;
        });

        Chat.initialize();

    </script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websockets rely on Javascript being enabled. Please enable
    Javascript and reload this page!</h2></noscript>
<div>
<!-- 
    <p>
        <input type="text" placeholder="type and press enter to chat" id="chat">
    </p>
 -->
    <div id="console-container">
        <div id="console"></div>
    </div>
</div>
</body>
</html>
	 

	</div>
	</td>
  </tr>
</table>


	    	
  	</div>
</div>

</body>
</html>