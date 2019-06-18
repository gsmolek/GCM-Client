

package ServerConnection;

import ocsf.client.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ChatClient extends AbstractClient
{
	
  private ArrayList<Object> str=new ArrayList<Object>();
  private ResultSet rs;
  private ArrayList<ArrayList<String>> array;
  private byte[][] result;

  public byte[][] returnByteArray() {
	  return result;
  }
  public ArrayList<ArrayList<String>> getArray() {
	return array;
}
  public void clearArray() {
	this.array.clear();
}

public void setRs(ResultSet rs) {
	this.rs = rs;
}

public ChatClient() 
  {
    super("localhost", 5550); //Call the superclass constructor
    try {
		openConnection();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  public void handleMessageFromServer(Object msg) 
  {
	  
	  ArrayList<Object> dataFromServer = (ArrayList<Object>)msg;
	  String command= (String)dataFromServer.get(0);
	  switch(command)
	  {
	  case "1":
	  {
		  String sql=(String)dataFromServer.get(dataFromServer.size()-1);
		  System.out.println("1");
	  }
	  case "2":
	  {
		  array = (ArrayList<ArrayList<String>>) dataFromServer.get(1);
		  
		  //ResultSet rsFromServer=(ResultSet)dataFromServer.get(1);
		  //this.rs=rsFromServer;
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
    	ArrayList<Object> sendSQL=(ArrayList<Object>)message;
		InetAddress inetAddress=null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		sendSQL.add(inetAddress);
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

