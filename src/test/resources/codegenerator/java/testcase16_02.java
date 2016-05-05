class Main {

    public static void main(String[] args) {
        mthread t1 = new mthread();
        mthread t2 = new mthread();
        mthread t3 = new mthread();
        mthread t4 = new mthread();
        mthread t5 = new mthread();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();
    }
}

class mthread extends Thread {
    public void run() {
        int y = 1000;
        while (y > 0) {
            System.out.println(1);
            y = y - 1;
        }
    }
}

