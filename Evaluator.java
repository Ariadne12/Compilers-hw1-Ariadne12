import java.io.InputStream;
import java.io.IOException;

class Evaluator {
    private final InputStream in;

    private int lookahead;

    public Evaluator(InputStream in) throws IOException {
        this.in = in;
        lookahead = in.read();
    }

    private void consume(int symbol) throws IOException, ParseError {
        if (lookahead == symbol)
            lookahead = in.read();
        else
            throw new ParseError();
    }

    private boolean isChar(int c) {
        return 'A' <= c && c <= 'z'; // idk how does java treat int and ascii
    }

    public int eval() throws IOException, ParseError {
        int value = exp();

        if (lookahead != -1 && lookahead != '\n')
            throw new ParseError();

        return value;
    }
}