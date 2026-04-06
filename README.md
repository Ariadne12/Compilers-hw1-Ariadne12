Part1:
The grammar doesn't accept blank spaces, meaning a ** b e.g will cause a parse error.

Part2:
Execution instructions:

$ make compile
$ make execute < input.txt


Overview:
- The output code is produced in stdout - fully aligned format
- The grammar ensures that the input has the format of function declarations and then statements, no reversed or mixing of the two. Otherwise it outputs error.
- No declarations or no statements (or neither) is handled.
- It is given to us that a function declaration must contain exactly one expression.
That expression can be either a string, an identifier, a concatenation of 2 expressions, an if-else clause containing expressions or a function call with expressions as arguments
However its parameters can only be identifers.
- On the top level (the statements) however although the same things apply, no identifiers can be used.
- Either way an if condition can only be a prefix/suffix check and the clause something that is of type string (so not prefix/suffix)


Τα αποκάτω είναι κυρίως σημειώσεις μου καθώς εκανα την εργασία, αλλα όποιος διορθωτης θέλει μπορει να το διαβασει

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


Cup parser:
The last precedence declaration give its terminals the highest precedence, so to implement precedence(if) < precedence(concat) we need to put the concat symbol (+), so the CONCAT under IF.

We make sure that the first production (program) is the one the grammar should start with so we avoid using "start with non-terminal;"

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

Σίγουρα θα θεωρείται σφάλμα αν υπάρχει οποιοδήποτε σωστό input, το οποίο δεν μεταγλωτίζεται σωστά.
οποτε δε θελουμε σίγουρα false negatives 

Για ευκολια κάνω return το ternary στη συναρτηση αντι να δηλωσω ολοκληρο if μέσα της κλπ (αρκετα περιπλοκο-άσχημο) 

suffix - prefix : αναγκαστικά αποτιμούνται τελευταία.

precedences (from the manual):

left-> reduce
if the input is a string of additions, like 3 + 4 + 5 + 6 + 7, the parser will always reduce them from left to right, in this case, starting with 3 + 4
right-> shifted onto the stack. hence, the reductions will take place from right to left. 
So the 6 + 7 would be reduced first in the above string. I
nonassoc-> two consecutive occurrences of equal precedence non-associative terminals generates an error. This is useful for comparison operations. if the input string is 6 == 7 == 8 == 9, the parser should generate an error, if '==' is declared as nonassoc

RESULT
The RESULT variable refers directly to the value of the non-terminal to which a rule reduces, rather than to the object on the parse stack. Hence, RESULT is of the same type the non-terminal to which it reduces.