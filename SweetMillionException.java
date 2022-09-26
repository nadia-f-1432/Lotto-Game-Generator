package edu.cuny.csi.csc330.util.lab6;

public class SweetMillionException extends Exception {
    public static int LOC_FILE_ERROR = 4;
    public static int GAME_NAME_ERROR =0;
    public static int POOL1_ERROR = 1;
    public static int POOL2_ERROR =2;
    public static int LOCATION_NAME_ERROR = 3;


    public static String[] MESSAGE = {
        "Unknown Game - Can't locate specific game", "Can't locate primary pool", "Can't locate secondary pool", "Unknown location Name - Can't locate vendor location"
    };

    protected int code;

    private SweetMillionException(){



}
    private SweetMillionException(String message){
        super(message);




    }

    SweetMillionException(String message, int code){
        super(message);
        this.code = code;





    }

    public int getCode() {
        return code;
    }

    public String toString() {
        return "SweetMillionGameException [getCode()=" + getCode() + "," + "toString()=" + super.toString() + "]\n" + MESSAGE[code];
    }

    public static void main(String[] args) {
        SweetMillionException exception = new SweetMillionException("Error Message ...");
        System.out.println("Ex: " + exception);

        SweetMillionException exceptionnew = new SweetMillionException("Error Message ...");
        System.out.println("Ex: " + exceptionnew);

    }

}
