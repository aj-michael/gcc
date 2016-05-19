class Main {
    public static void main(String[] args) {
        FibScheduler scheduler = new FibScheduler().init();
        FibThread thread1 = new FibThread();
        thread1.setFibScheduler(scheduler);
        thread1.setThreadNumber(1);
        FibThread thread2 = new FibThread();
        thread2.setFibScheduler(scheduler);
        thread2.setThreadNumber(2);
        FibThread thread3 = new FibThread();
        thread3.setFibScheduler(scheduler);
        thread3.setThreadNumber(3);
        thread1.start();
        Thread.sleep(100L);
        thread2.start();
        Thread.sleep(100L);
        thread3.start();
    }
}

class FibScheduler {
    int nextFibNumber;
    int maxFibNumber;

    public FibScheduler init() {
        nextFibNumber = 0;
        maxFibNumber = 1000;
        return this;
    }

    public synchronized int getNextFibNumber() {
        int result = 0;
        if (nextFibNumber == maxFibNumber) {
            result = -1;
        } else {
            nextFibNumber = nextFibNumber + 1;
            result = nextFibNumber;
        }
        return result;
    }
}

class Math {
    public native double sqrt(double x);
    public native double divide(double a, double b);

    public double exp(double x, int n) {
        double y = 1.0;
        int i = 0;
        while (i < n) {
            y = y * x;
            i = i + 1;
        }
        return y;
    }
}

class FibThread extends Thread {
    FibScheduler scheduler;
    int number;

    public void setFibScheduler(FibScheduler s) {
        scheduler = s;
    }

    public void setThreadNumber(int x) {
        number = x;
    }

    public void run() {
        int n = scheduler.getNextFibNumber();
        Math math = new Math();
        double phi = math.divide(1.0 + math.sqrt(5.0), 2.0);
        double nphi = math.divide(1.0 - math.sqrt(5.0), 2.0);
        while (n != -1) {
            double phin = math.exp(phi, n);
            double nphin = math.exp(nphi, n);
            double fibn = math.divide(phin - nphin, math.sqrt(5.0));
            System.out.println(number);
            System.out.println(fibn);
            Thread.sleep(2000L);
            n = scheduler.getNextFibNumber();
        }
    }
}
