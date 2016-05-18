class Main {
    public static void main(String[] args) {
        LoopThread t = new LoopThread();
        t.start();
        Thread.sleep(2000L);
        t.stopRunning();
    }
}

class LoopThread extends Thread {
    volatile boolean running;

    public void stopRunning() {
        System.out.println(false);
        running = false;
    }

    public void run() {
        running = true;
        System.out.println(running);
        int x = 0;
        while (running) {
        }
        System.out.println(true);
    }

}
