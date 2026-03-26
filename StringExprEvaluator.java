import java.io.InputStream;
import java.io.IOException;

class StringExprEvaluator {
    private final InputStream in;

    private char lookahead;     // ?? or int

    public StringExprEvaluator(InputStream in) throws IOException {
        this.in = in;
        //lookahead = in.read();
        int next = in.read();
        if (next == -1) {
            throw new IOException("End of input");
        }
        lookahead = (char) next;
    }

    private void consume(char symbol) throws IOException, ParseError {
        if (lookahead == symbol){
            int next = in.read();
            if (next == -1) {
                throw new IOException("End of input");
            }
            lookahead = (char) next;
        }
        else
            throw new ParseError();
    }

    private boolean isLetter(int c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z');
    }

    // (entry point)
    public String eval() throws IOException, ParseError {
        String value = exp();

        if (lookahead != -1 && lookahead != '\n')
            throw new ParseError();

        return value;
    }

    // consume προχωράει τον κέρσορα στο input
    // άρα χρησιμοποιείται μόνο στα 
    private String exp() throws IOException, ParseError {
        if (isLetter(lookahead) || lookahead == '(') {
            //consume(lookahead);
            String left = term();
            return expTail(left); 
        }

        throw new ParseError();
    }

    private String expTail(String left) throws IOException, ParseError {
        switch (lookahead) {
            case '/':       // / term expTail
                consume('/');

                String right = term();
                right = expTail(right); // recursion, right associativity

                // implementation of /
                if (left.endsWith(right)) {     // right(b) is suffix of left(a) (a=q∘b)
                    return left.substring(0, left.length() - right.length());   // return q
                } 
                else {
                    return left;
                }
            //case -1:
            case '\n':
            case ')':
                return left;
        }

        throw new ParseError();
    }

    private String term() throws IOException, ParseError {
        if (isLetter(lookahead) || lookahead == '(') {
            String left = factor();
            return termTail(left);
        }

        throw new ParseError();
    }


    private String termTail(String left) throws IOException, ParseError {
        switch (lookahead) {
            // we assume correct input strings, meaning right after a * there will always be a second
            // forming the symbol **
            case '*':       // ** factor termTail
                consume('*');
                consume('*');

                // left associativity
                String right = factor();
                // implementation of **
                // concatenate left (a) with double right (b) (a**b=a∘b∘b)
                left = left + right + right;
                return termTail(left);
            //case -1:
            case '\n':
            case ')':
            case '/':
                return left;
        }

        throw new ParseError();
    }


    private String factor() throws IOException, ParseError {
        if (isLetter(lookahead)) {
            return str();
        }
        else if (lookahead == '(') {    // (exp)
            consume('(');
            String inside = exp();
            consume(')');
            return inside;
        }

        throw new ParseError();
    }

    private String str() throws IOException, ParseError {
        if (isLetter(lookahead)) {
            String left = String.valueOf(Char());
            return left + strTail();
        }

        throw new ParseError();
    }


    private String strTail() throws IOException, ParseError {
        if (isLetter(lookahead)) {
            return str();
        }
        else if (lookahead == '\n' || lookahead == -1 || lookahead == ')' || lookahead == '/' || lookahead == '*') {
            return "";
        }

        throw new ParseError();
    }

    private char Char() throws IOException, ParseError {
        if (isLetter(lookahead)) {
            char c = lookahead;
            consume(lookahead);
            return c; 
        }

        throw new ParseError();
    }
}