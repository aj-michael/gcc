	public static String getGCD(String a, String b) {
		System.out.println((+a+,+b+));
		if(b.equals(0))
			return a;
		String temp = StringMath.mod(a,b);
		a = b;
		b = temp;
		return getGCD(b,temp);
	}
	public static void main(String args[]) {
		int prod = 52*60;
		int e = 17;
		//System.out.println(getGCD(prod,e));
		//System.out.println(getGCD(prod+,e+));
		System.out.println(4626);
	}
}