// Jaiveer Lalli
// 1-29-2026
// CSE 122
// TA: Yang Wu 
// C1: Todo List Manager

// This program runs a todo list manager. The user is able to add, 
// remove, load, save, and print out their todo list. Through a creative
// extension the user is also able to sort their to do list in order
// from the shortest item to longest item in terms of length if 
// EXTENSION_FLAG is true

import java.util.*;
import java.io.*;

public class TodoListManager {
    public static final boolean EXTENSION_FLAG = false;

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to your TODO List Manager!");
        List<String> todos = new ArrayList<>();
        Scanner console = new Scanner(System.in);
        
        
        String answer = "";
        while (!answer.equalsIgnoreCase("Q")) {
        
            System.out.println("What would you like to do?");
            System.out.print("(A)dd TODO, (M)ark TODO as done, (L)oad TODOs, " +
                            "(S)ave TODOs, (Q)uit? ");
            
            answer = console.nextLine();
            
            if (answer.equalsIgnoreCase("Q")) {
            
            } else if (answer.equalsIgnoreCase("A")) {
                addItem(console, todos);
            } else if (answer.equalsIgnoreCase("M")) {
                markItemAsDone(console, todos);
            } else if (answer.equalsIgnoreCase("L")) {
                loadItems(console, todos);
            } else if (answer.equalsIgnoreCase("S")) {
                saveItems(console, todos);
            } else {
                System.out.println("Unknown input: " + answer);
                printTodos(todos);
            }
        }
    }


    // takes in the list of todos as a parameter and print out the current items 
    // in the list. If there is nothing in the list, the user is notified that 
    // the list is empty
    public static void printTodos(List<String> todos) {
        
        System.out.println("Today's TODOs:");

        if (todos.size() > 0) {
            for (int i = 0; i < todos.size(); i++) {
                System.out.println("  " + (i + 1) + ": " + todos.get(i));
            }
            
        } else {
            System.out.println("  You have nothing to do yet today! Relax!");
        }
    }


    // takes in scanner console for user input and the list of the users to do list so far.
    // If the list already has multiple items, the user is asked where they would like to 
    // place their new to do. The user is also able to just press enter to place the new item
    // at the end of the list. The to do list gets updated with the new item and the updated
    // list prints out
    public static void addItem(Scanner console, List<String> todos) {
        int positionOfTodo = todos.size();
        System.out.print("What would you like to add? ");
        String activity = console.nextLine();


        if (todos.size() > 0) {
            if (EXTENSION_FLAG) {
                System.out.println("TODOs will be sorted in order of length");
            } else {
                System.out.print("Where in the list should it be (1-" + (todos.size() + 1) +
                                ")? (Enter for end): ");
                String input = console.nextLine();
                if (!input.isEmpty()) {
                    positionOfTodo = Integer.parseInt(input) - 1;
                }
            }
        } 
        todos.add(positionOfTodo, activity);

        if (EXTENSION_FLAG) {
            sortByLength(todos);
        }
        printTodos(todos);
    }

    

    // We take in scanner console for the users input and our current list of to do items 
    // if the list has something in it, the user is asked where in the list the item 
    // that has been completed is located and then we remove that item from the list.
    // If there is nothing to remove, the user is notified that there is nothing left to remove
    public static void markItemAsDone(Scanner console, List<String> todos) {
        if (todos.size() > 0) {
            System.out.print("Which item did you complete (1-" + todos.size() + ")? ");
            String removeActivity = console.nextLine();
            int numOfActivity = Integer.parseInt(removeActivity);
            todos.remove(numOfActivity - 1);
        } else {
            System.out.println("All done! Nothing left to mark as done!");
        }
        printTodos(todos);
    }



    // We take in scanner console for user input and our list of to do items
    // We ask the user for a file name and then clear our current list and add
    // each line from the file to the list
    public static void loadItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        Scanner fileScan = new Scanner(new File(fileName));
        todos.clear();
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            todos.add(line);
        }
        printTodos(todos);  
    }

    
    // We take in scanner console for user input and our list of to do items.
    // We prompt the user for the name of the file and then print out our current
    // items to the file.
    public static void saveItems(Scanner console, List<String> todos)
                                throws FileNotFoundException {
        System.out.print("File name? ");
        String fileName = console.nextLine();
        PrintStream out = new PrintStream(fileName);
        for (int i = 0; i < todos.size(); i++) {
            out.println(todos.get(i));
        }
        out.close();
        printTodos(todos);
    }
    
    

    // We take in our list of todo items and are able to find the item in the list 
    // with the least amount of characters. The list goes through and reorganizes the list
    // putting the item with the least characters first index and the most in the last index.
    public static void sortByLength(List<String> todos) {
        List<String> sortedList = new ArrayList<>();
        while (todos.size() > 0) {
            String placeHolder = todos.get(0);
            int shortestIndex = 0;
            for (int i = 1; i < todos.size(); i++) {
                if (todos.get(i).length() < placeHolder.length()) {
                    placeHolder = todos.get(i);
                    shortestIndex = i;
                }
            }
            sortedList.add(todos.remove(shortestIndex));

        }
        todos.addAll(sortedList);
    }
} 
