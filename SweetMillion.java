package edu.cuny.csi.csc330.util.lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import edu.cuny.csi.csc330.util.Randomizer;

public class SweetMillion {

    public final static int DEFAULT_GAME_COUNT = 1;
    private static int amountOfGames;
    private static String gameName;
    private static String locationName;
    private static int mainPool;
	private static int secondPool;
	
	private static int amountOfGames2; //space in lottery array for second pool

    private final static int SELECTION_POOL_SIZE = 80;
    private final static int SELECTION_COUNT = 5;

    private RandomNumber randomizer;
    private static boolean hasSecondPool = false;

    
    

    private int gameCount = DEFAULT_GAME_COUNT;
    private int selectionPoolSize;
    private int selectionCount;


    public SweetMillion(int gameCount) {
        init(gameCount);
    }
    
    public SweetMillion(String fileName, int count)  throws SweetMillionException, FileNotFoundException {
		amountOfGames = count;
		Scanner scanner = new Scanner(new File("C:/Users/GWC/eclipse-workspace/CSC330/src/edu/cuny/csi/csc330/extracredit/Words.txt"));
		initFromPropBundle(fileName);
		init();
	}
    
    private static void initFromPropBundle(String fileName) throws SweetMillionException {
    	
		ResourceBundle bundle = ResourceBundle.getBundle("PropertyBundleDemo");

		if (bundle.containsKey("GameName")) { //if GameName is included in the property bundle, get the name 
			gameName = bundle.getString("GameName");
		}
		else  //throw an exception that that game name isn't found in the property file 
			throw new SweetMillionException("This specified game name doesn't exist in the property file ERROR: ", SweetMillionException.GAME_NAME_ERROR);

		if (bundle.containsKey("Vendor")) { //if Vendor is included in the property bundle, get the name of location
			locationName = bundle.getString("Vendor");
		}
		else // throw an exception if vendor information is missing
			throw new SweetMillionException("There was no specified lotto vendor location ERROR: ", SweetMillionException.LOCATION_NAME_ERROR);
		
		//next lines of code are doing the same thing with if else exception statements only now with the main pool and second pool
		if (bundle.containsKey("Pool1")) {
			String str = bundle.getString("Pool1");
			String[] pool1 = str.split("/", 2); //splitting pool1 into the amount chosen out of the total number
		}
		else 
			throw new SweetMillionException("Primary pool is not found in the file ERROR: ", SweetMillionException.POOL1_ERROR);

		//check if there is secondary pool in the file
		if (bundle.containsKey("Pool2")) {
			String str = bundle.getString("Pool2");
			String[] pool2 = str.split("/", 2); //splitting pool2 into the amount chosen out of the total number
		}
		else 
			throw new SweetMillionException("Secondary pool not found in the file ERROR: ", SweetMillionException.POOL2_ERROR);
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
			throw new SweetMillionException("Primary pool is not found in the file ERROR: ", SweetMillionException.POOL1_ERROR);

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
			throw new SweetMillionException("Secondary pool not found in the file ERROR: ", SweetMillionException.POOL2_ERROR);

	}
	
    
    


    private void init() {
        Random randomizer = new RandomNumber();
        this.loadGameSettings( SELECTION_COUNT, SELECTION_POOL_SIZE);
    }


    private void init(int gameCount) {
        init();
        this.gameCount = gameCount;

    }

    protected void loadGameSettings(int selectionCount, int selectionPooSize) {
        
        this.selectionCount = selectionCount;
        this.selectionPoolSize = selectionPooSize;
    }

    //displayTicket()

    public void displayTicket() {
        displayHeading();   //print ticket heading

        //generate random numbers

        for (int i = 0; i < gameCount; ++i) {
            int random[] = generateNumbers();
            runGame(i + 1, random);
        }

        displayFooter(); //display ticket footer
    }
    


     //displayHeading()

    protected void displayHeading() {
        System.out.printf("---------------------------------\n");
        System.out.printf("-------- %s --------\n", "Sweet Million Game");
        System.out.println(" " + new Date() + "\n");
    }

    //displayFooter()

    protected void displayFooter() {
        System.out.printf("\n----- (c) %s -----\n", "Three Hands Deli");
        System.out.printf("---------------------------------\n");
    }


    protected void runGame(int index, int[] randomNumbers) {
        if (index != 0)
            System.out.printf(" (%2d) ", index);
        for (int i = 0; i < randomNumbers.length; ++i) {
            System.out.printf("%02d ", randomNumbers[i]);
        }
        System.out.printf("\n");
    }
    
    public void RandomNumber(int poolSize, int n) //generates lotto values and n is the amount of games (in one row) 
	{
		
	int[] lotteryArray = new int[n]; //lotteryArray stores the number of games per 1 row
	Set<Integer> s = new HashSet<Integer>(); //checking for repeating values 
	
       for (int i = 0; i < n; i++) {
        	lotteryArray[i]=generateInt(1,poolSize); //with each step from 0 to length of array, a random integer between 1 and poolsize 	
        	     if (s.add(i) == false) //if we can't add it to a set, then there's a repeating value so that index value gets deleted
        	        i--;
       }
        	     
        }
       
       int generateInt(int low, int high) { //generates a random number between two arguments low (inclusive) and high (exclusive)
   		
   		Random rand = new Random();
   		int result= rand.nextInt((high+1)-low) + low; // formula obtained from online, I modified it with (high +1) b/c by itself, high is exclusive
   													// but (high + 1) makes the user's chosen upper value inclusive 
   		return result; 	
   	}


    protected int[] generateNumbers() {
        int randomNumbers[] = new int[selectionCount];
        int i = 0;
        do {
            randomNumbers[i] = this.randomizer.generateInt(1, selectionPoolSize);
            ++i;
        } while (i < randomNumbers.length);
        Arrays.sort(randomNumbers); //sort the random numbers
        return randomNumbers;
    }
    
    private void draw() { //drawing out the lotto values from the random numbers 

		// draw primary pool
		RandomNumber(mainPool, amountOfGames); 

		// draw secondary pool
		if (hasSecondPool == true)
			RandomNumber(secondPool, amountOfGames2);

	}


//    

    public static void main(String[] args) {
		
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
