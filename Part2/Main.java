import java_cup.runtime.*;
import java.io.*;

public class Main {
    public static void main(String[] argv) throws Exception{
        Parser p = new Parser(new Scanner(new InputStreamReader(System.in)));
        
        Symbol resultSymbol = p.parse();
        
        // Cast the value to String and print it
        String result = (String) resultSymbol.value;
        System.out.println(result);
    }
}