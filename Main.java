package traffic;

import java.io.IOException;

import java.util.Scanner;


public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String greeting =  "Welcome to the traffic management system!";
    System.out.println(greeting);

    System.out.print("Input the number of roads: ");

    int numberOfRoads = -1;
    while (numberOfRoads <= 0) {
      try {
        numberOfRoads = Integer.parseInt(scanner.nextLine());
        if (numberOfRoads <= 0) throw new Exception("Number of roads should a be positive number, greater then 0.");
      }
      catch (Exception e) {
        printErrorMessage();
      }
    }

    System.out.print("Input the interval: ");
    int numberOfIntervals = -1;
    while (numberOfIntervals <= 0) {
      try {
        numberOfIntervals = Integer.parseInt(scanner.nextLine());
        if (numberOfIntervals <= 0) throw new Exception("Number of intervals should a be positive number, greater then 0.");
      }
      catch (Exception e) {
        printErrorMessage();
      }
    }

    QueueThread qt = new QueueThread();
    qt.setName("QueueThread");
    qt.numberOfIntervals = numberOfIntervals;
    qt.numberOfRoads = numberOfRoads;
    qt.circularQueue = new CircularQueue(numberOfRoads);
    qt.openedRoadRemainingTime = numberOfIntervals;
    qt.start();


    //clearConsole();
    //printMenu();

    int userInput = -1;
    while (userInput != 0) {
      String output = "";
      clearConsole();
      printMenu();
      try {
        userInput = Integer.parseInt(scanner.nextLine());
        //terminate the programm
        switch (userInput) {
          case 1:
            System.out.print("Input road name: ");
            String roadName = scanner.nextLine();
            if (qt.circularQueue.isFull()) {
              System.out.println("Queue is full.");
            } else {
              Road roadtoAdd = new Road(roadName, numberOfIntervals);
              qt.circularQueue.enqueue(roadtoAdd);
              output = roadName + " Added!";
              System.out.println(output);
            }
            scanner.nextLine();
            break;
          case 2:

            if (qt.circularQueue.isEmpty()) {
              System.out.println("Queue is empty");
            } else {
              Road roadtoRemove = qt.circularQueue.dequeue();
              output = roadtoRemove.getName() + " deleted!";
              System.out.println(output);
            }
            scanner.nextLine();
            break;
          case 3:
            output = "System opened";
            System.out.println(output);
            qt.isPrinting = true;
            scanner.nextLine();
            qt.isPrinting = false;
            break;
          case 0:
            output = "Bye!";
            System.out.println(output);
            qt.interrupt();
            return;
          default:
            throw new Exception("Unsupported user input");
        }
//        if (output.equalsIgnoreCase("bye!")){
//          return;
//        } else {
          //clearConsole();
          //scanner.nextLine();
          //printMenu();
        //}
      }
      catch (Exception e){
        System.out.println("Incorrect option");
        scanner.nextLine();
      }
    }
  }

  public synchronized static void printMenu(){
    String menu = """
            Menu:
            1. Add
            2. Delete
            3. System
            0. Quit""";
    System.out.println(menu);
  }

  public synchronized static void clearConsole() {
    try {
      var clearCommand = System.getProperty("os.name").contains("Windows")
              ? new ProcessBuilder("cmd", "/c", "cls")
              : new ProcessBuilder("clear");
      clearCommand.inheritIO().start().waitFor();
    }
    catch (InterruptedException | IOException e) {}
  }

  public synchronized static void printErrorMessage() {
    System.out.print("""
            Error! Incorrect input. Try again:  """);
  }

}
