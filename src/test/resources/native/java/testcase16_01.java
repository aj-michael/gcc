class Main {
  public static void main(String[] args) {
    System.out.println(new Squarer().square(2));
  }
}

class Squarer {

    public native int square(int i);

}
