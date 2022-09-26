package edu.cuny.csi.csc330.util.lab6;

import java.util.*;

//Nadzeya Fliaha Lab 6 Exceptions Class 

public class QuickPickerException extends Exception{
	public static int LOC_FILE_ERROR = 0;
	// static publicly defined error codes 
	public static int GAME_NAME_ERROR = 1;
	public static int POOL1_ERROR = 2;
	public static int POOL2_ERROR = 3;
	public static int LOCATION_NAME_ERROR = 4;
	
	  // static publicly defined exception messages  
	  public static String[]  MESSAGE = { 
			  "Unknown Game - Can't locate specified game", 
				"Can't locate primary pool", 
				"Can't locate secondary pool",
				"Unknown Location Name - Can't locate vendor location"
				};
	
		protected int code;  // carries an error code like a real exception would 
		
		
	    private  QuickPickerException() { ; } 
	    
	   
	    public QuickPickerException(String message) { 
	    	super(message); 
	    } 
	    
	   
	    public QuickPickerException(String message, int code) { 
	    	super(message);
	    	this.code = code;
	    } 
	
		public int getCode() { 
			return code;
		}
		

	    @Override
		public String toString() {
	    	return "QuickPickerException [getCode()=" + getCode() + ", "
					+ "toString()=" + super.toString() + "]\n" + MESSAGE[code];
		}
	    
	    public static void main(String[] args) {
	    	
			QuickPickerException exception = new QuickPickerException("Error message: ...  ");
			System.out.println("Ex: " + exception);
			
			QuickPickerException exception1 = new QuickPickerException("Error message: ...  ", QuickPickerException.LOC_FILE_ERROR);
			System.out.println("Ex: " + exception1);
		}
}
