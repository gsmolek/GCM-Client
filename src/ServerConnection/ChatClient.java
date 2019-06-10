

package ServerConnection;

import ocsf.client.*;

import java.io.*;
import java.util.ArrayList;


public class ChatClient extends AbstractClient
{
	
  private ArrayList<String> str=new ArrayList<String>();

  public ChatClient(String host, int port) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    openConnection();
  }

  public void handleMessageFromServer(Object msg) 
  {
	  ArrayList<Object> dataFromServer = (ArrayList<Object>)msg;
	  String command= (String)dataFromServer.get(0);
	  switch(command)
	  {
	  case "1":
	  {}
	  case "2":
	  {}
	  case "3":
	  {}
	  default:
	  {}
	  }
  }
public ArrayList<String> getStr()
{
	return str;
}
public void clearStr()
{
	this.str.clear();
}

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClient(Object message)  
  {
    try
    {
    	sendToServer(message);
    }
    catch(IOException e)
    {
        System.out.println("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}

