class Main {
	public static void main (String[] args){
		Boo boo = new Boo();
		System.out.println(boo.toInt());
		boo.switch();
		System.out.println(boo.toInt());
	}

class Boo {
	boolean b = false;
	public boolean switch(){
		b = !b;
		return true;
	}
	public int toInt(){
		int a = 0;
		if(b) {
			a = 1; 
		} else {
			a =0;
		}
		return a;
	}
	
}