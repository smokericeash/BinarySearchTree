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

        System.out.print("Pre-order: ");
        Iterator<Integer> iter = myTree.getPreorderIterator();

        while(iter.hasNext()){
            System.out.print(iter.next() + " ");
        }
        System.out.println(""); //finish pre-order sequence

        System.out.print("In-order: ");
        Iterator<Integer> iter2 = myTree.getInorderIterator();

        while(iter2.hasNext()){
            System.out.print(iter2.next() + " ");
        }
        System.out.println(""); //finish in-order sequence

        System.out.print("Post-order: ");
        Iterator<Integer> iter3 = myTree.getPostorderIterator();

        while(iter3.hasNext()){
            System.out.print(iter3.next() + " ");
        }
        System.out.println(""); //finish post-order sequence

        boolean keepGoing = true;
        while(keepGoing){
            System.out.print("Command? ");
            //removing whitespaces and changing input to lowercase
            String line = keyboard.nextLine().trim();
            if(line.isEmpty()) continue;

            //Split line by spaces where part[0] is command and part[1] is the number
            String[] parts = line.split("\\s+");
            char input = parts[0].toLowerCase().charAt(0);

            //second part of the command(the number)
            int value = 0;
            if(parts.length > 1){
                try{
                    value = Integer.parseInt(parts[1]);
                }
                catch(NumberFormatException e){
                    System.out.println("Invalid number format.");
                    continue;
                }
            }
            if(input=='i'){
                if(myTree.add(value)==null){
                    System.out.println(value + " inserted.");
                    System.out.print("In-order: ");
                    Iterator<Integer> iter4 = myTree.getInorderIterator();

                    while(iter4.hasNext()){
                        System.out.print(iter4.next() + " ");
                    }
                    System.out.println(""); //finish in-order sequence
                }
                else{
                    System.out.println(value + " already exists.");
                }

            }
            else if(input=='d'){
                if(myTree.remove(value)!=null){
                    System.out.println(value + " deleted.");
                    System.out.print("In-order: ");
                    Iterator<Integer> iter5 = myTree.getInorderIterator();

                    while(iter5.hasNext()){
                        System.out.print(iter5.next() + " ");
                        }
                    System.out.println(""); //finish in-order sequence
                }
                else{
                    System.out.println(value + " not found.");
                }
            }
            else if(input=='p'){
                Integer pred = myTree.getPredecessor(value);
                System.out.println("Predecessor: " + (pred == null ? "None" : pred));
            }
            else if(input=='s'){
                Integer succ = myTree.getSuccessor(value);
                System.out.println("Successor: " + (succ == null ? "None" : succ));
            }
            else if(input=='e'){
                keepGoing = false;
                System.out.println("Thank you for using my program!");
            }
            else if(input=='h'){
                System.out.println("I   Insert a value");
                System.out.println("D   Delete a value");
                System.out.println("P   Find predecessor");
                System.out.println("S   Find successor");
                System.out.println("E   Exit the program");
                System.out.println("H   Display this message");
            }
            else{ //an input different than the ones listed
                System.out.println("Sorry, that is not a valid option, please view the help menu.");
            }
        }
        keyboard.close();
    } //end main
}
