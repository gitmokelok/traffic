package traffic;

public class CircularQueue {
    public Road[] queue;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    private int currentOpenIndex;


    //Constructor to initialize the queue with given capacity
    public CircularQueue(int capacity) {
        this.capacity = capacity;
        queue = new Road[capacity];
        front = 0; //fronts starts with the first element
        rear = -1; //Rear start before the first element
        size = 0; // Initially, the queue is empty
        currentOpenIndex = 0;
    }

    //Method to add an element to the Queue
    public synchronized boolean enqueue(Road road) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot enqueue " + road.getName());
            return false;
        }

        //Move rear pointer in circular fashion
        rear  = (rear + 1) % capacity;
        queue[rear] = road;
        size++;
        return true;
    }

    //Method to remove element from the queue
    public synchronized Road dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Cannot dequeue");
            return null;
        }

        Road dequeueElement = queue[front];
        queue[front] = null;
        //move front pointer in  circular fashion
        front = (front + 1) % capacity;
        size--;
        return dequeueElement;
    }

    //Method to check if the queue is full
    public boolean isFull() {
        return size == capacity;
    }

    //Method to check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    //Method to get size of Queue
    public int getSize() {
        return size;
    }

    //Get element from Queue by index
    public Road getRoadByIndex(int index) {
        return queue[index];
    }


    //Method to display the element of the queue
    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            Road elementToPrint = queue[(front + i) % capacity];
            System.out.println(elementToPrint.getName());
        }
        System.out.println();
    }

    public synchronized void updateRoadTimes(int interval, int numberOfRoads) {
        int roadIndex = 0;
        int timeToOpen = interval;

        for (Road road : queue) {
            if (road == null) {
                continue;
            }
            if (roadIndex == currentOpenIndex) {
               road.setOpen(true);
               road.setRemainingTime(interval);
            } else {
                road.setOpen(false);
                if(roadIndex > currentOpenIndex) {
                    road.setRemainingTime(timeToOpen);
                    timeToOpen += interval;
                } else {
                    road.setRemainingTime((numberOfRoads - currentOpenIndex + roadIndex) * interval);
                }
            }
            roadIndex++;
        }
    }

    public synchronized void moveToNextRoad(int numberOfRoads) {
        currentOpenIndex = (currentOpenIndex + 1) % numberOfRoads;
    }



    public synchronized void displayState() {
        if (isEmpty()) {
            System.out.println("Queue is empty");
            return;
        }

        System.out.println();
        for (Road road : queue) {
            if (road == null) {
                continue;
            }

            if (road.isOpen()) {
                // The road at openedRoadIndex is open
                System.out.println(road.getName()+ " will be open for " + road.getRemainingTime() + "s.");
            } else {
                System.out.println(road.getName() + " will be closed for " + road.getRemainingTime() + "s.");
            }
        }
        System.out.println();
    }

    // Get the road that is currently open
    public Road getOpenRoad() {
        int index = 0;
        for (Road road : queue) {
            if (index == currentOpenIndex) {
                return road;
            }
            index++;
        }
        return null;  // Should not happen since the index is always valid
    }



    //Method to display the element of the queue
//    public synchronized void display(int openedTimeRemaining, int interval, int openedRoadIndex) {
//        if (isEmpty()) {
//            System.out.println("Queue is empty");
//            return;
//        }
//
//        System.out.println();
//        for (int i = 0; i < size; i++) {
//            Road elementToPrint = queue[i];
//            if (i == openedRoadIndex) {
//                // The road at openedRoadIndex is open
//                System.out.println(elementToPrint.name + " will be open for " + openedTimeRemaining + "s.");
//            } else {
//                // Adjust the formula to account for the circular queue
//                int relativePosition = (i - openedRoadIndex + size) % size;
//                int closedTimeRemaining = interval * (relativePosition - 1) + openedTimeRemaining;
//
//                if (closedTimeRemaining < 0) {
//                    closedTimeRemaining += interval * size; // Wrap around
//                }
//
//                System.out.println(elementToPrint.name + " will be closed for " + closedTimeRemaining + "s.");
//            }
//        }
//        System.out.println();
//    }

}
