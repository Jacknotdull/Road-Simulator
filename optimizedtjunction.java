package optimizedtjunction;

import java.util.concurrent.*;
import java.util.Random;

public class optimizedtjunction {

    public static void main(String[] args) throws Exception{
        ExecutorService road = Executors.newFixedThreadPool(1); //allowing 1 thread to run at a same time
        int carID = 0, pedestrianID = 0, carNumber = 0, pedestrianNumber = 0;
        car c = new car(carID, carNumber);
        pedestrian p = new pedestrian(pedestrianID, pedestrianNumber);

        while (true) {
            try {
                c.number = 0;
                Future<car> east = road.submit(new eastroad(c));
                System.out.println("------------------------------------ " + east.get().number + " cars had exited East road ------------------------------------");
                //Thread.sleep(100);

                Future<car> southferry = road.submit(new southferry(east.get())); //assume that only east road car go to ferry station in south road
                System.out.println("------------------------------------ " + southferry.get().number + " cars had entered the ferry and waiting for departure ------------------------------------");
                c.number = 0;
                //Thread.sleep(100);

                c.number = 0;
                Future<car> west = road.submit(new westroad(c));
                System.out.println("------------------------------------ " + west.get().number + " cars had exited West road ------------------------------------");
                //Thread.sleep(100);

                Future<car> southferry2 = road.submit(new southferry(west.get())); //assume that only east road car go to ferry station in south road
                System.out.println("------------------------------------ " + southferry2.get().number + " cars had entered the ferry and waiting for departure ------------------------------------");
                c.number = 0;
                //Thread.sleep(100);

                Future<car> south = road.submit(new southroad(c));
                System.out.println("------------------------------------ " + south.get().number + " cars had exited South road ------------------------------------");
                c.number = 0;
                //Thread.sleep(100);

                Future<pedestrian> zebra = road.submit(new zebracrossing(p));
                System.out.println("------------------------------------ " + zebra.get().number + " pedestrians had crossed the road ------------------------------------");
                p.number = 0;
                //Thread.sleep(100);

            } catch (Exception e) {
            }
        }

    }

}

class car{
    int id;
    int number;

    public car(int id, int number){
        this.id = id;
        this.number = number;
    }
}

class pedestrian{
    int id;
    int number;

    public pedestrian(int id, int number){
        this.id = id;
        this.number = number;
    }
}

class eastroad implements Callable<car>{
    car c;
    private volatile boolean cancel = false;

    public eastroad(car c){
        this.c = c;
    }

    public car call() throws Exception{
        cancel = false;
        Random generator = new Random();
        int faultyrate = generator.nextInt(10);

        if(faultyrate > 7){ //if nextInt gets 8/9, therefore the rate is 20%
            cancel = true;
            System.out.println("ERROR: East traffic malfunctions...");
            c.number = 0;
        }
        while(!cancel) {
            System.out.println("//\nEast turn:\nEast traffic lights are GREEN now");
            int carflow = generator.nextInt(10)+1;
            for (int i = 1; i <= (carflow); i++) {
                System.out.println("East car " + i + " is going straight to West road");
                Thread.sleep(500);
                c.number++;
                System.out.println("East car " + i + " is going left to South road");
                Thread.sleep(500);
                c.number++;
            }
            Thread.sleep(500);
            System.out.println("East traffic lights are turning YELLOW...\nAll cars are slowing down...");
            Thread.sleep(500);
            System.out.println("East traffic lights are now RED...All cars stop...");
            break;
        }
        return c;
    }

    public void cancel(){
        cancel = true;
    }
}

class westroad implements Callable<car>{
    car c;
    private volatile boolean cancel = false;

    public westroad(car c){
        this.c = c;
    }

    public car call() throws Exception{
        cancel = false;
        Random generator = new Random();
        int faultyrate = generator.nextInt(10);

        if(faultyrate > 7){ //if nextInt gets 8/9, therefore the rate is 20%
            cancel = true;
            System.out.println("ERROR: West traffic malfunctions...");
            c.number = 0;
        }
        while(!cancel) {
            System.out.println("//\nWest turn:\nWest traffic lights are GREEN now");
            int carflow = generator.nextInt(10)+1;
            for (int i = 1; i <= carflow; i++) {
                System.out.println("West car " + i + " is going straight to East road");
                Thread.sleep(500);
                c.number++;
                System.out.println("West car " + i + " is going right to South road");
                Thread.sleep(500);
                c.number++;
            }
            Thread.sleep(500);
            System.out.println("West traffic lights are turning YELLOW...\nAll cars are slowing down...");
            Thread.sleep(500);
            System.out.println("West traffic lights are now RED...All cars stop...");
            break;
        }
        return c;
    }

    public void cancel(){
        cancel = true;
    }
}

class southroad implements Callable<car>{
    car c;
    private volatile boolean cancel = false;

    public southroad(car c){
        this.c = c;
    }

    public car call() throws Exception{
        cancel = false;
        Random generator = new Random();
        int faultyrate = generator.nextInt(10);

        if(faultyrate > 7){ //if nextInt gets 8/9, therefore the rate is 20%
            cancel = true;
            System.out.println("ERROR: South traffic malfunctions...");
            c.number = 0;
        }
        while(!cancel) {
            System.out.println("//\nSouth turn:\nSouth traffic lights are GREEN now");
            int carflow = generator.nextInt(10)+1;
            for (int i = 1; i <= carflow; i++) {
                System.out.println("South car " + i + " is going left to West road");
                Thread.sleep(500);
                c.number++;
                System.out.println("South car " + i + " is going right to East road");
                Thread.sleep(500);
                c.number++;
            }
            Thread.sleep(500);
            System.out.println("South traffic lights are turning YELLOW...\nAll cars are slowing down...");
            Thread.sleep(500);
            System.out.println("South traffic lights are now RED...All cars stop...");
            break;
        }
        return c;
    }

    public void cancel(){
        cancel = true;
    }
}

class zebracrossing implements Callable<pedestrian>{
    pedestrian p;

    public zebracrossing(pedestrian p){
        this.p = p;
    }

    public pedestrian call() throws Exception{
        System.out.println("//\nZebra crossing turn:\nPedestrian traffic lights are GREEN now");
        Random generator = new Random();
        int peopleflow = generator.nextInt(10)+1;

        for(int i = 1; i <= peopleflow; i++){
            System.out.println("East pedestrian " + i + " is crossing to West road");
            Thread.sleep(500);
            p.number++;
            System.out.println("West pedestrian " + i + " is crossing to East road");
            Thread.sleep(500);
            p.number++;
        }
        Thread.sleep(500);
        System.out.println("Pedestrian traffic lights are turning YELLOW...");
        Thread.sleep(500);
        System.out.println("Pedestrian traffic lights are now RED...All people stop crossing...");
        return p;
    }

}

class southferry implements Callable<car>{
    car c;

    public southferry(car c){
        this.c = c;
    }

    public car call() throws Exception{
        if(c.number > 0){
            System.out.println("//\nOnboarding traffic light is GREEN now, Gate opens");
            for(int i = 1; i <= c.number; i++){
                System.out.println("Car " + i + " is boarding the Ferry");
                c.number--;
                Thread.sleep(500);
            }
            Thread.sleep(500);
            System.out.println("Onboarding traffic light is turning YELLOW...\nGate is closing...");
            Thread.sleep(500);
            System.out.println("Onboarding traffic light is now RED...Ferry is waiting for departure...");
            return c;
        }
        return c;
    }
}