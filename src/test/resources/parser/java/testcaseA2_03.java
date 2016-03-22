/*  parserTest3.java
    Parser Testfile
    slightly more complicated program with separate method
*/
class Test{
    public static void main(String[] args){
        int i = 0;
        while(i < 10){
            print();//cool
            i = i + 1;
        }
        System.out.println("all done");//whee!
    }
    // helper method to print a message
    public static void print(){
        System.out.println("All your base are belong to us");
    }
}