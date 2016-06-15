package utility;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * ChatServer Client
 *
 */
@ClientEndpoint
public class WebsocketClientEndpoint {

	Logger logger = (Logger)LoggerFactory.getLogger(getClass().getName()+".class");
    public Session userSession = null;
    private MessageHandler messageHandler;

    public WebsocketClientEndpoint() {	}
    
    public WebsocketClientEndpoint(URI endpointURI) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
	public void _createConnection(){
		 try {
			LoadOnStartAppConfiguration.clientEndPoint = new WebsocketClientEndpoint(new URI(LoadOnStartAppConfiguration.urlWebSocketURI));
			logger.info("WebSocket: "+LoadOnStartAppConfiguration.urlWebSocketURI+" available!");
		} catch (Exception e) {
			e.printStackTrace();			
			logger.debug(e.getMessage());			
		}
    }
	
    /**
     * Closes the session and connection to the WebSocket chat.
     */
    public void _closeSession(){
    	try {    		
			this.userSession.close();			
			logger.debug("closing session!");
		} catch (IOException e) {	
			e.printStackTrace();
			logger.error("some error",e);
		}
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
    	logger.debug("opening websocket");        
        this.userSession = userSession;
        
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
    	logger.debug("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     *
     */
    public static interface MessageHandler {

        public void handleMessage(String message);
    }
}