

package ServerConnection;

import ocsf.client.*;

import java.io.*;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ChatClient extends AbstractClient
{
	
  private ArrayList<Object> str=new ArrayList<Object>();
  private ResultSet rs;

  public ResultSet getRs() {
	return rs;
}

public void setRs(ResultSet rs) {
	this.rs = rs;
}

public ChatClient() 
    throws IOException 
  {
    super("localhost", 5550); //Call the superclass constructor
    openConnection();
  }

  public void handleMessageFromServer(Object msg) 
  {
	  ArrayList<Object> dataFromServer = (ArrayList<Object>)msg;
	  String command= (String)dataFromServer.get(0);
	  switch(command)
	  {
	  case "1":
	  {
		  String sql=(String)dataFromServer.get(dataFromServer.size());
		  System.out.println("1");
	  }
	  case "2":
	  {
		  ResultSet rsFromServer=(ResultSet)dataFromServer.get(1);
		  this.rs=rsFromServer;
	  }
	  case "3":
	  {
		  
	  }
	  default:
	  {}
	  }
  }
public ArrayList<Object> getStr()
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
    	System.out.println("entered from client");
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

