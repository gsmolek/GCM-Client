package ServerConnection;

import java.io.Serializable;

public class ImageStream implements Serializable{
	private String fileName=null;
	private int size=0;
	public byte[] imageStreamByteArray;
	
	public ImageStream(String fileName)
	{
		this.fileName=fileName;
	}
	public void initArray(int size)
	{
		imageStreamByteArray = new byte [size];	
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public byte[] getImageStreamByteArray() {
		return imageStreamByteArray;
	}
	
	public byte getImageStreamByteArray(int i) {
		return imageStreamByteArray[i];
	}
	public void setImageStreamByteArray(byte[] imageStreamByteArray) {
		
		for(int i=0;i<imageStreamByteArray.length;i++)
		this.imageStreamByteArray[i] = imageStreamByteArray[i];
	}
}
