class Sample2 {
	int  count = 0;
	public static void main(String[] args) {
		while (count > 0) {
			if (count == 5) {
				/* Print 100 when the count is 5*/
				System.out.println(100);
			} else {
				System.out.println(count);
			}
			count = count - 1;
		}
	}
}