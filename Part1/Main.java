import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = reader.readLine()) != null) {
            try {
                StringExprEvaluator evaluator =
                    new StringExprEvaluator(new ByteArrayInputStream((line + "\n").getBytes()));

                System.out.println(evaluator.eval());
            } catch (IOException | ParseError e) {
                System.err.println(e.getMessage());
            }
        }
    }
}