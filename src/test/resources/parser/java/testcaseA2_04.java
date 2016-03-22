/*  parserTest4.java
    Parser Testfile
    invalid minijava program
    declares i without initializing it
    uses for loop instead of while
    the helper method is now private
*/
class Test{
    public static void main(String[] args){
        int i;
        for(i = 0; i < 10; i++){
            print();//cool
            i = i + 1;
        }
        System.out.println("all done");//whee!
    }
    // helper method to print a message
    private static void print(){
        System.out.println("All your base are belong to us");
    }
}