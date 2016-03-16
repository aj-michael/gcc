class Main {
	public static void main (String[] args){
		Foo bar = new Foo();
		System.out.println(bar.setF(5));
		System.out.println(bar.setF(12));
		}
}

class Foo {
	int f = 0;

 	public int setF(int f2){
		int f0 = f;
		f = f2;
		return f0;
	}
}