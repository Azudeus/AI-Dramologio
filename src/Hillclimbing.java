import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;


public class Hillclimbing extends CSP{
	private int violation;

	Hillclimbing() {

	}


	public void run() {
		setRandomAllActivity();
		checkViolation();
		violation = countViolation();

	}



    public static void main(String[] args) {
        // TODO code application logic here
        new Hillclimbing().run();
    }


}