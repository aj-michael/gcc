class Main {

    public static void main(String[] args) {
        mthread t1 = new mthread();
        mthread t2 = new mthread();
        t1.start();
        t2.start();
    }
}

class mthread extends Thread {
    public void run() {
        int y = 100;
        while (y > 0) {
            System.out.println(1);
            y = y - 1;
        }
    }
}

