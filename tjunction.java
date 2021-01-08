package tjunction;


import java.util.concurrent.*;
import java.util.Random;

public class tjunction {

    public static void main(String[] args) throws Exception{
        long startTime = System.nanoTime();
        ScheduledExecutorService road = Executors.newScheduledThreadPool(1);

        road.scheduleAtFixedRate(new eastroad(), 0, 25, TimeUnit.SECONDS);
        road.scheduleAtFixedRate(new westroad(), 5, 25, TimeUnit.SECONDS);
        road.scheduleAtFixedRate(new southroad(), 10, 25, TimeUnit.SECONDS);
        road.scheduleAtFixedRate(new zebracrossing(), 15, 25, TimeUnit.SECONDS);
        road.scheduleWithFixedDelay(new southferry(),30,30, TimeUnit.SECONDS);
    }

}

class eastroad implements Runnable{
    private volatile boolean cancel = false;

    public void run(){
        int number = 0;
        cancel = false;
        Random generator = new Random();
        int westcar = generator.nextInt(10)+1; //at least 1
        int southcar = generator.nextInt(10)+1;
        int faultyrate = generator.nextInt(10);
        if(faultyrate > 7){ //if nextInt gets 8/9, therefore the rate is 20%
            cancel = true;
            System.out.println("ERROR: East traffic malfunctions...");
        }
        while(!cancel) {
            try {
                System.out.println("//\nEast turn:\nEast traffic lights are GREEN now");
                for (int i = 1; i <= westcar; i++) {
                    System.out.println("East car " + i + " is going straight to West road");
                    Thread.sleep(500);
                    number++;
                }

                for (int j = 1; j <= southcar; j++) {
                    System.out.println("East car " + j + " is going left to South road");
                    Thread.sleep(500);
                    number++;
                }
                Thread.sleep(500);
                System.out.println("East traffic lights are turning YELLOW...\nAll cars are slowing down...");
                Thread.sleep(500);
                System.out.println("East traffic lights are now RED...All cars stop...");
                System.out.println("------------------------------------" + number + " cars exited East road ------------------------------------");
                break;
            } catch (Exception e) {
            }
        }
    }

    public void cancel(){
        cancel = true;
    }
}

class westroad implements Runnable{
    private volatile boolean cancel = false;
    public void run(){
        int number = 0;
        cancel = false;
        Random generator = new Random();
        int westcar = generator.nextInt(10)+1;
        int southcar = generator.nextInt(10)+1;
        int faultyrate = generator.nextInt(10);
        if(faultyrate > 7){ //if nextInt gets 8/9, therefore the rate is 20%
            cancel = true;
            System.out.println("ERROR: West traffic malfunctions...");
        }
        while(!cancel) {
            try {
                System.out.println("//\nWest turn:\nWest traffic lights are GREEN now");
                for(int i = 1; i <= westcar; i++){
                    System.out.println("West car " + i + " is going straight to East road");
                    Thread.sleep(500);
                    number++;
                }

                for(int j = 1; j <= southcar; j++){
                    System.out.println("West car " + j + " is going right to South road");
                    Thread.sleep(500);
                    number++;
                }
                Thread.sleep(500);
                System.out.println("West traffic lights are turning YELLOW...\nAll cars are slowing down...");
                Thread.sleep(500);
                System.out.println("West traffic lights are now RED...All cars stop...");
                System.out.println("------------------------------------ " + number + " cars exited West road ------------------------------------");
                break;
            } catch (Exception e) {}
        }
    }

    public void cancel(){
        cancel = true;
    }

}

class southroad implements Runnable{
    private volatile boolean cancel = false;

    public void run(){
        int number = 0;
        cancel = false;
        Random generator = new Random();
        int westcar = generator.nextInt(10)+1;
        int southcar = generator.nextInt(10)+1;
        int faultyrate = generator.nextInt(10);
        if(faultyrate > 7){ //if nextInt gets 8/9, therefore the rate is 20%
            cancel = true;
            System.out.println("ERROR: South traffic malfunctions...");
        }
        while(!cancel) {
            try {
                System.out.println("//\nSouth turn:\nSouth traffic lights are GREEN now");
                for (int i = 1; i <= westcar; i++) {
                    System.out.println("South car " + i + " is going left to West road");
                    Thread.sleep(500);
                    number++;
                }

                for (int j = 1; j <= southcar; j++) {
                    System.out.println("South car " + j + " is going right to East road");
                    Thread.sleep(500);
                    number++;
                }
                Thread.sleep(500);
                System.out.println("South traffic lights are turning YELLOW...\nAll cars are slowing down...");
                Thread.sleep(500);
                System.out.println("South traffic lights are now RED...All cars stop...");
                System.out.println("------------------------------------ " + number + " cars exited South road ------------------------------------");
                break;
            } catch (Exception e) {}
        }

    }

    public void cancel(){
        cancel = true;
    }

}

class zebracrossing implements Runnable{

    public void run(){
        int number = 0;
        Random generator = new Random();
        int peopleflow = generator.nextInt(10)+1;
        try {
            System.out.println("//\nZebra crossing turn:\nPedestrian traffic lights are GREEN now");
            for (int i = 1; i <= peopleflow; i++) {
                System.out.println("pedestrian " + i + " is crossing to the road");
                Thread.sleep(500);
                number++;
            }

            Thread.sleep(500);
            System.out.println("Pedestrian traffic lights are turning YELLOW...");
            Thread.sleep(500);
            System.out.println("Pedestrian traffic lights are now RED...All people stop crossing...");
            System.out.println("------------------------------------ " + number + " pedestrians had crossed the road ------------------------------------");
        } catch (Exception e) {
        }
    }

}

class southferry implements Runnable{

    public void run() {
        int carno = 0;
        Random generator = new Random();
        int ferrycar = generator.nextInt(10)+5; //at least 5; at most 15
        try {
            System.out.println("//\nOnboarding traffic light is GREEN now, Gate opens");
            for(int i = 1; i <= ferrycar; i++){
                System.out.println("Car " + i + " is boarding the Ferry");
                Thread.sleep(500);
                carno++;
            }
            Thread.sleep(500);
            System.out.println("Onboarding traffic light is turning YELLOW...\nThe gate is closing...");
            Thread.sleep(500);
            System.out.println("Onboarding traffic light is now RED...and Ferry is waiting for departure");
            System.out.println("------------------------------------ " + carno + " cars had entered the ferry ------------------------------------");
        } catch (Exception e) {}

    }
}

