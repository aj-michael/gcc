class Main {
  public static void main(String[] args) {
    Foo foo = new Foo();
    foo.setBar(17);
    System.out.println(foo.getBar());
  }
}

class Foo {
  int bar;

  public void setBar(int x) {
    bar = x;
  }

  public int getBar() {
    return bar;
  }
}
