class Main {

    public static void main(String[] args) {
        Counter counter = new Counter();
        counter.reset();
        CounterIncrementer ci1 = new CounterIncrementer();
        ci1.setCounter(counter);
        ci1.setIncrement(100000);
        CounterIncrementer ci2 = new CounterIncrementer();
        ci2.setCounter(counter);
        ci2.setIncrement(100000);
        ci1.start();
        ci2.start();
        ci1.join();
        ci2.join();
        counter.printValue();
    }
}

class Counter {
    int value;

    public void reset() {
        value = 0;
    }

    public void increment() {
        synchronized(this) {
            value = value + 1;
        }
    }

    public void printValue() {
        System.out.println(value);
    }
}

class CounterIncrementer extends Thread {
    Counter counter;
    int increment;

    public void run() {
        int y = increment;
        while (y > 0) {
            counter.increment();
            y = y - 1;
        }
    }

    public void setIncrement(int i) {
        increment = i;
    }

    public void setCounter(Counter c) {
        counter = c;
    }
}

