package traffic;

public class Road {
    private String name;
    private boolean isOpen;
    private int remainingTime;

    public Road(String name, int interval) {
        this.name = name;
        this.isOpen = false;
        this.remainingTime = interval;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        this.isOpen = open;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int time) {
        this.remainingTime = time;
    }

    public void decrementTime() {
        if (remainingTime > 0) {
            remainingTime--;
        }
    }

}
