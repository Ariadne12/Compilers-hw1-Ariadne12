
Execution instructions:

$ make compile
$ make execute < input.txt


Lexer:
This routine is responsible for reading individual characters, removing things things like white space and comments, recognizing which terminal symbols from the grammar each group of characters represents, then returning Symbol objects representing these symbols to the parser.



From the manual for gfeg: "If two regular expressions both have the longest match for a certain
input, the scanner chooses the action of the expression that appears first in the specification."
For that reason in the section of <YYINITIAL> we put the keywords before the identifiers.

An identifier is a user-defined name given to program elements—such as variables, functions, classes, or modules.
The definition of an identifier (again based on the jflex manual) is "a letter [a-zA-Z] followed by a sequence of zero or more letters, digits or underscores [a-zA-Z0-9_]" so in regular expression form we define that as [a-zA-Z][a-zA-Z0-9_]*

From the example inputs and since the language doesn't mention it we don't support the character ';', but '{', '}' and ',' for the function defintions and '(', ')' for both the definition and call of the function and the conditionals. 

A lexical state acts like a start condition. If the scanner is in lexical state STRING, only expressions
that are preceded by the start condition <STRING> can be matched. 
A start condition of a regular expression can contain more than one lexical state. It is then matched when the lexer is in any of these lexical states.


For the cup file:
The last precedence declaration give its terminals the highest precedence, so to implement precedence(if) < precedence(concat) we need to put the concat symbol (+), so the PLUS under IF.


the first non-terminal expr has been labeled with e1, and the second with e2

We make sure that the first production is the one the grammar should start with so we avoid using "start with non-terminal;"

Each symbol on the right hand side can optionally be labeled with a name. Label names appear after the symbol name separated by a colon (:). Label names must be unique within the production, and can be used within action code to refer to the value of the symbol.

Actions appear in the right hand side as code strings (e.g., Java code inside {: ... :} delimiters). These are executed by the parser at the point when the portion of the production to the left of the action has been recognized. (Note that the scanner will have returned the token one past the point of the action since the parser needs this extra lookahead token for recognition.)

In trying to make the grammar for the arguments inside the declaration of functions we couldnt just use something like:
```params ::= ID COMMA params
             | ID
             |  empty
```
because we would be dealing with a trailing comma at the end. So instead we are treating the possibility of no parameters as an entirely separate case and otherwise make it so as to always end on an ID. So we split it into 2 rules:
```params ::= params_list
        | empty

params_list ::= ID
            | params_list COMMA ID
```

From the exercise we have "", so mistakes in the input like same function name in multiple function declarations or a function call for a function that has not be declared or other things taken care of in the semantic stage will not be covered here.

It is given to us that a function must contain exactly one expression.
That expression can be either a function call, an if-else clause or a concatenation

Μπορείς να έχεις identifier σαν arg σε κλήση συνάρτησης αν είναι nested μέσα σε άλλη συνάρτηση.
Δεν είναι ανάγκη να το εξασφαλίσετε αυτό, μια και θέλει μεγάλη προσπάθεια στη γραμματική
Αλλά μπορεί να θέλετε να το εξασφαλίσετε για να αφαιρέσετε κάποιο shift-reduce error στον parser generator.

Difference between parameter (in func declaration) and arguments (in func call) is that arguments can be virtually anything
The "controversials" are probably the identifer and the boolean check of prefix/suffix.
In the case of a function nested in another it can use the parameters of the outer one as arguments, which are identifiers.
An argument can also be the check of prefix and suffix, since the result of that can be used inside the function in an if/else condition.

So it's technically an expr.


Parentheses in the grammar: control structure (how expressions are parsed)
Parentheses in Java code (RESULT = ...): only matter if needed for operator precedence

here all parentheses are needed for correct java


Θα πρότεινα να αλλάξεις το όνομα της κλάσης του παραγόμενου προγράμματος σε κάτι άλλο, πχ MyMain. Με αυτήν την αλλαγή, θα μπορείς να τυπώνεις τον παραγόμενο Java κώδικα σε ένα αντίστοιχο MyMain.java χωρίς να έχεις conflict με το όνομα του μεταφραστή σου(και μας διευκολύνεις και εμάς στη διόρθωση).

επισης προβλημα το if ουσιαστικα μεσα του μπορει  να δεχθει μόνο το prefix suffix ουσιαστικα. αρα πρεπει να το λλαξω


Σίγουρα θα θεωρείται σφάλμα αν υπάρχει οποιοδήποτε σωστό input, το οποίο δεν μεταγλωτίζεται σωστά.
οποτε θελουμε false positives 

Για ευκολια κάνω return το ternary στη συναρτηση αντι να δηλωσω ολοκληρο if μέσα της κλπ (αρκετα περιπλοκο) 

suffix - prefix : άρα αναγκαστικά αποτιμούνται τελευταία.
αφου αναγκαστικα, δε χρειαζεται να κανω κατι ετσι?


precedences:

left-> reduce
if the input is a string of additions, like 3 + 4 + 5 + 6 + 7, the parser will always reduce them from left to right, in this case, starting with 3 + 4
right-> shifted onto the stack. hence, the reductions will take place from right to left. 
So the 6 + 7 would be reduced first in the above string. I
nonassoc-> two consecutive occurrences of equal precedence non-associative terminals generates an error. This is useful for comparison operations. if the input string is 6 == 7 == 8 == 9, the parser should generate an error, if '==' is declared as nonassoc

RESULT
The RESULT variable refers directly to the value of the non-terminal to which a rule reduces, rather than to the object on the parse stack. Hence, RESULT is of the same type the non-terminal to which it reduces

Terminal symbols are symbols that cannot be replaced by antyhting else on the vocab. so anything that appears on the left?

Things i need to consider:
- functions cant have 
- να βαλω το ternary σε παρενθεσεις???? δκ
- nmz na αλλαξω τα ονοματα κτ functions list dk
