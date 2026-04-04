import java_cup.runtime.*;
import java.io.*;

public class Main {
    public static void main(String[] argv) throws Exception{
        Parser p = new Parser(new Scanner(new InputStreamReader(System.in)));
        p.parse();
    }
}