package edu.cuny.csi.csc330.util.lab6;

//Nadzeya Fliaha
//Lab 6 
//Goal, make lab 3 more configurable 

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import edu.cuny.csi.csc330.util.Randomizer;


public class QuickPicker {

	public final static int DEFAULT_GAME_COUNT = 7; 
	private static String gameName;
	private static String locationName;
	private static String heading;
	
	private static int mainPool;
	private static int secondPool;
	private static int amountOfGames; //space in lottery array
	private static int amountOfGames2; //space in lottery array for second pool
	private static int count; //how many rows there are going to be 
	private static boolean hasSecondPool = false;
	

	 public QuickPicker(String fileName, int count)  throws QuickPickerException {
			this.count = count; 
			initFromPropBundle(fileName);
			//init();
		}
	private static void initFromPropBundle(String fileName) throws QuickPickerException {
		
		// using the template from PropertyBundleDemo.java 
		ResourceBundle bundle = ResourceBundle.getBundle(fileName);

		if (bundle.containsKey("GameName")) { //if GameName is included in the property bundle, get the name 
			gameName = bundle.getString("GameName");
		}
		else  //throw an exception that that game name isn't found in the property file 
			throw new QuickPickerException("This specified game name doesn't exist in the property file ERROR: ", QuickPickerException.GAME_NAME_ERROR);

		if (bundle.containsKey("Vendor")) { //if Vendor is included in the property bundle, get the name of location
			locationName = bundle.getString("Vendor");
		}
		else // throw an exception if vendor information is missing
			throw new QuickPickerException("There was no specified lotto vendor location ERROR: ", QuickPickerException.LOCATION_NAME_ERROR);
		
		//next lines of code are doing the same thing with if else exception statements only now with the main pool and second pool
		if (bundle.containsKey("Pool1")) {
			String str = bundle.getString("Pool1");
			
			//needed to break down the "string" arguments in pool1 to pass as arguments 
			//used str.split which is 
			//"The string split() method breaks a given string around matches of the given regular expression. 
			//After splitting against the given regular expression, this method returns a String array." according to geeksforgeeks
			
			String[] pool1 = str.split("/", 2); //splitting pool1 at the "/" to get 2 seperate integer array values 
			
			mainPool= Integer.parseInt(pool1[1]); //how high can we go in lottery picks 
			amountOfGames = Integer.parseInt(pool1[0]); //how many picks we make per one row 
		}
		else 
			throw new QuickPickerException("Primary pool is not found in the file ERROR: ", QuickPickerException.POOL1_ERROR);

		//check if there is secondary pool in the file
		if (bundle.containsKey("Pool2")) {
			String str = bundle.getString("Pool2");
			String[] pool2 = str.split("/", 2); //splitting pool2 into the amount chosen out of the total number
			secondPool = Integer.parseInt(pool2[1]);
			amountOfGames2 = Integer.parseInt(pool2[0]);
			if (secondPool > 0)
				hasSecondPool = true;
		}
		else 
			throw new QuickPickerException("Secondary pool not found in the file ERROR: ", QuickPickerException.POOL2_ERROR);

	}
	
	public QuickPicker(int games) {
		init(games); 
	}


	private void init(int games) {
		
	}
	
	public void displayTicket() {

		// display ticket heading 
		displayHeading();
		
		draw();
		// display ticket footer 
		displayFooter(); 
		
	}
	private void draw() { //drawing out the lotto values from the random numbers 

		// draw primary pool
		Randomizer(mainPool, amountOfGames); 

		// draw secondary pool
		if (hasSecondPool == true)
			Randomizer(secondPool, amountOfGames2);

	}
	
	protected void displayHeading() {
	 
		 System.out.printf("%1$s%2$30s%3$s", "-----------------------------------------\r\n" + //type of game title with formatting 
			 		"--------------" + gameName + "-------------\r\n" + 
			 		"", new Date(), "\r\n\r\n"); //date and time from import java.util.*;
	}

	public void Randomizer(int poolSize, int n) //generates lotto values and n is the amount of games (in one row) 
	{
		
	int[] lotteryArray = new int[n]; //lotteryArray stores the number of games per 1 row
	Set<Integer> s = new HashSet<Integer>(); //checking for repeating values 
	
       for (int i = 0; i < n; i++) {
        	lotteryArray[i]=generateInt(1,poolSize); //with each step from 0 to length of array, a random integer between 1 and poolsize 	
        	     if (s.add(i) == false) //if we can't add it to a set, then there's a repeating value so that index value gets deleted
        	        i--;
        	     
        }
		Arrays.sort(lotteryArray);
        for(int i=0; i<lotteryArray.length; i++) { 
			if(lotteryArray[i]<10 && n ==amountOfGames2) //if n is for the second pool, put (( )) around the value 
				System.out.printf("((%02d)) ",lotteryArray[i]); 
			else if (lotteryArray[i]>=10 && n ==amountOfGames2)
				System.out.printf("((%d))",lotteryArray[i]);
			else if (lotteryArray[i]<10)
				System.out.printf("%02d ",lotteryArray[i]);//adding a leading 0 if random number is in the single digits
			else
				System.out.printf(lotteryArray[i]+" ");
		}//end of for loop
		
        
	}//end of Randomizer class
	
        	//!!!!!! failed attempt at using collections haha
        	// I got to the non-repeating part, but couldn't get the sorted values or add the leading 0's before #'s under 10
        
    		// Insert all array elements in hash
        	// table
        	//  Map<Integer, Integer> m = new HashMap<>();
        	//            if (m.containsKey(lotteryArray[i])) 
        	//                m.put(lotteryArray[i], m.get(lotteryArray[i]) + 1);
        	//            else 
        	//                m.put(lotteryArray[i], 1);
        
			// Traverse through map only and
			// using for-each loop for iteration over Map.entrySet()
			        
			//        for (Map.Entry<Integer, Integer> x : m.entrySet()) {
			//        	if (x.getValue() == 1) 
			//            	System.out.print(x.getKey() + " ");
			//        }
       
   
	int generateInt(int low, int high) { //generates a random number between two arguments low (inclusive) and high (exclusive)
		
		Random rand = new Random();
		int result= rand.nextInt((high+1)-low) + low; // formula obtained from online, I modified it with (high +1) b/c by itself, high is exclusive
													// but (high + 1) makes the user's chosen upper value inclusive 
		return result; 	
	}
	
	protected void displayFooter() {
	
		//simple formatted output with your choice of location
		System.out.printf("\r\n--------- " + locationName +"-----------\r\n"+ 
				"-----------------------------------------\r\n" + ""); 
	}
	

	public static void main(String[] args) throws QuickPickerException {
		
		String gameName=args[0];
	
		if (args.length == 0)
		{
			count = DEFAULT_GAME_COUNT;
			System.err.println("No game name has been entered, please provide a valid argument.");
			System.exit(1);
		}
		
		//gameName = args[0];
		
		if (args.length > 1)
			count = Integer.parseInt(args[1]); //the amount of rows is configurable now 
		
		try {
				QuickPicker lotto = new QuickPicker(gameName, count);
				lotto.displayHeading();
			
					for(int i =1; i<=count; i++) {
						System.out.print("( "+ i +") ");
						lotto.draw();
						System.out.print("\n");
					}
				lotto.displayFooter();
			}
		
		catch (MissingResourceException exception) {
			throw new QuickPickerException("Unknown game name: " + gameName, QuickPickerException.LOC_FILE_ERROR);
		}
	}
}
