package lexer;

/**
 *  The Lexer class is responsible for scanning the source file
 *  which is a stream of characters and returning a stream of 
 *  tokens; each token object will contain the string (or access
 *  to the string) that describes the token along with an
 *  indication of its location in the source program to be used
 *  for error reporting; we are tracking line numbers; white spaces
 *  are space, tab, newlines
 */
public class Lexer {

    private boolean atEOF = false;
    private char ch;     // next character to process
    private static SourceReader source;
    // positions in line of current token
    private int startPosition, endPosition, lineno;

    public Lexer(String sourceFile) throws Exception {
        new TokenType();  // init token table
        source = new SourceReader(sourceFile);
        ch = source.read();
    }

    public static void main(String args[]) {
        Token tok;
        try {
            if (args.length == 0) {
                System.err.println("Error: a single file name argument is required!");
                System.exit(1);
            }
            Lexer lex = new Lexer(args[0]);
            while (true) {
                tok = lex.nextToken();
                String p = TokenType.tokens.get(tok.getKind()) + "";
                if ((tok.getKind() == Tokens.Identifier)
                        || (tok.getKind() == Tokens.INTeger)) {
                    p += tok.toString();
                }
                p += "\t\tleft: " + tok.getLeftPosition()
                        + " right: " + tok.getRightPosition();
                System.out.println(p + " line: " + tok.getLineNumber());
            }
        } catch (Exception e) {
            e.getMessage();
        }
        System.out.println("");
        System.out.println("");
        System.out.println(source.getSavedOutput());
        System.exit(0);
    }

    /**
     *  newIdTokens are either ids or reserved words; new id's will be inserted
     *  in the symbol table with an indication that they are id's
     *  @param id is the String just scanned - it's either an id or reserved word
     *  @param startPosition is the column in the source file where the token begins
     *  @param endPosition is the column in the source file where the token ends
     *  @return the Token; either an id or one for the reserved words
     */
    public Token newIdToken(String id, int startPosition, int endPosition, int lineNumber) {
        return new Token(startPosition, endPosition, lineNumber, Symbol.symbol(id, Tokens.Identifier));
    }

    /**
     *  number tokens are inserted in the symbol table; we don't convert the 
     *  numeric strings to numbers until we load the bytecodes for interpreting;
     *  this ensures that any machine numeric dependencies are deferred
     *  until we actually run the program; i.e. the numeric constraints of the
     *  hardware used to compile the source program are not used
     *  @param number is the int String just scanned
     *  @param startPosition is the column in the source file where the int begins
     *  @param endPosition is the column in the source file where the int ends
     *  @return the int Token
     */
    public Token newNumberToken(String number, int startPosition, int endPosition, int lineNumber) {
        return new Token(startPosition, endPosition, lineNumber,
                Symbol.symbol(number, Tokens.INTeger));
    }

    /**
     *  build the token for operators (+ -) or separators (parens, braces)
     *  filter out comments which begin with two slashes
     *  @param s is the String representing the token
     *  @param startPosition is the column in the source file where the token begins
     *  @param endPosition is the column in the source file where the token ends
     *  @return the Token just found
     */
    public Token makeToken(String s, int startPosition, int endPosition, int lineNumber) {
        if (s.equals("//")) {  // filter comment
            try {
                int oldLine = source.getLineno();
                do {
                    ch = source.read();
                } while (oldLine == source.getLineno());
            } catch (Exception e) {
                atEOF = true;
            }
            return nextToken();
        }
        Symbol sym = Symbol.symbol(s, Tokens.BogusToken); // be sure it's a valid token
        if (sym == null) {
            System.out.println("******** illegal character: " + s);
            System.out.println("");
            System.out.println(source.getSavedOutput());
            System.exit(1);
            atEOF = true;
            return nextToken();
        }
        return new Token(startPosition, endPosition, lineNumber, sym);
    }

    /**
     *  @return the next Token found in the source file
     */
    public Token nextToken() { // ch is always the next char to process
        if (atEOF) {
            if (source != null) {
                source.close();
                source = null;
            }
            return null;
        }
        try {
            while (Character.isWhitespace(ch)) {  // scan past whitespace
                ch = source.read();
            }
        } catch (Exception e) {
            atEOF = true;
            return nextToken();
        }
        startPosition = source.getPosition();
        endPosition = startPosition - 1;
        lineno = source.getLineno();

        if (Character.isJavaIdentifierStart(ch)) {
            // return tokens for ids and reserved words
            String id = "";
            try {
                do {
                    endPosition++;
                    id += ch;
                    ch = source.read();
                } while (Character.isJavaIdentifierPart(ch));
            } catch (Exception e) {
                atEOF = true;
            }
            return newIdToken(id, startPosition, endPosition, lineno);
        }
        if (Character.isDigit(ch)) {
            // return number tokens
            String number = "";
            try {
                do {
                    endPosition++;
                    number += ch;
                    ch = source.read();
                } while (Character.isDigit(ch));
            } catch (Exception e) {
                atEOF = true;
            }
            return newNumberToken(number, startPosition, endPosition, lineno);
        }

        // At this point the only tokens to check for are one or two
        // characters; we must also check for comments that begin with
        // 2 slashes
        String charOld = "" + ch;
        String op = charOld;
        Symbol sym;
        try {
            endPosition++;
            ch = source.read();
            op += ch;
            // check if valid 2 char operator; if it's not in the symbol
            // table then don't insert it since we really have a one char
            // token
            sym = Symbol.symbol(op, Tokens.BogusToken);
            if (sym == null) {  // it must be a one char token
                return makeToken(charOld, startPosition, endPosition, lineno);
            }
            endPosition++;
            ch = source.read();
            return makeToken(op, startPosition, endPosition, lineno);
        } catch (Exception e) {
        }
        atEOF = true;
        if (startPosition == endPosition) {
            op = charOld;
        }
        return makeToken(op, startPosition, endPosition, lineno);
    }
}