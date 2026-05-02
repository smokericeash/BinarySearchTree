import java.util.*;
import java.io.*;

public class test {
    public static void main(String[] args){
        System.out.println("Please enter the initial sequence of values: ");
        Scanner keyboard = new Scanner(System.in);
        String inputLine = keyboard.nextLine();

        SearchTreeInterface<Integer> myTree = new BinarySearchTree<>();

        //split by one or more characters
        String[] tokens = inputLine.trim().split("\\s+");

        for(String token: tokens){
            try{
                int val = Integer.parseInt(token);
                //add() returns null if the value is new and added successfully
                //returns existing value if duplicate
                myTree.add(val);
            }

            //catches exception in which the user doesn't put a valid number in the input
            catch(NumberFormatException e){
                System.out.println("Skipping non-integer:" + token);
            }
        }

        System.out.println("Pre-order: ");
        System.out.println("In-order: ");
        System.out.println("Post-order: ");
        boolean keepGoing = true;
        while(keepGoing){
            System.out.print("Command? ");
            //removing whitespaces and changing input to lowercase
            char input = keyboard.nextLine().trim().toLowerCase().charAt(0);
            if(input=='i'){

            }
            else if(input=='d'){
                
            }
            else if(input=='p'){
                
            }
            else if(input=='s'){
                
            }
            else if(input=='e'){
                keepGoing = false;
                System.out.println("Thank you for using my program!");
            }
            else if(input=='h'){
                
            }
        }
        keyboard.close();
    }
}
