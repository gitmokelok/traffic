package traffic;

import java.io.IOException;

public class QueueThread extends Thread {

    private volatile int timePassedSinceStart = 0;
    public volatile boolean isRunning = true;
    public volatile boolean isPrinting = false;
    public volatile int numberOfRoads = 0;
    public volatile int numberOfIntervals = 0;
    public volatile CircularQueue circularQueue;
    public volatile int openedRoadRemainingTime = 0;
    public volatile int openedRoadIndex = 0;


    @Override
    public synchronized void run() {
        int currentInterval = numberOfIntervals;
        boolean firstStart = false;
        while (isRunning) {
            try {
                Thread.sleep(1000);
                timePassedSinceStart++;
                if (circularQueue.isFull() && !firstStart) firstStart = true;


                if (firstStart) {
                    circularQueue.updateRoadTimes(currentInterval, circularQueue.getSize());
                    //circularQueue.getOpenRoad().decrementTime();
                    currentInterval--;
                    if (currentInterval == 0) {
                        circularQueue.moveToNextRoad(circularQueue.getSize());
                        currentInterval = numberOfIntervals;
                    }
                }

                if(isPrinting) {
                    clearConsole();
                    System.out.print(String.format("""
                    ! %d s. have passed since system startup !
                    ! Number of roads: %d !
                    ! Interval: %d !
                    """, timePassedSinceStart,numberOfRoads, numberOfIntervals));
                    //print Queue of Roads if the queue is not empty
                    if (!circularQueue.isEmpty()) {
                        circularQueue.displayState();
                    }
                    System.out.println("! Press \"Enter\" to open menu !");
                }


            } catch (InterruptedException e) {
                //Thread got interrupted and exit!
                Thread.currentThread().interrupt();
                break;
            }
        }
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

}
