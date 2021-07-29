//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 5
//Data: 15/11/19
//Lexer.java

import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                 peek = ' ';
                return Token.rpt;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':  
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';                
                return Token.mult;

            case '/':
            	readch(br);
                if (peek == '/') {
                    do {
                    	readch(br);
                    } while ( peek != (char)-1 && peek != '\n');

                    return lexical_scan(br);

                }
                else if (peek == '*') {
                	for (;;) {
                		readch(br);
                		if (peek == '*'){
                			//readch(br);
                            while (peek == '*'){
                                readch(br);
                    			if (peek == '/'){
                    				peek = ' ';
                    				break;
                    			}
                            }
                		}
                		else if (peek == (char)-1 ){
                			System.err.println("Error: comment not closed." );
                    		return null;
                		}
                	}

                    return lexical_scan(br);
                }
                else{
                	return Token.div;
                }

            case '=':
                readch(br);
                if(peek == '='){
                    peek = ' ';
                    return Word.eq;
                }
                else{
                    return Token.assign; 
                }

            case ';':
                peek = ' ';
                return Token.semicolon;

            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                    return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character"
                            + " after | : "  + peek );
                    return null;
                }

            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                }
                else if(peek == '>'){
                    peek = ' ';
                    return Word.ne;
                } 
                else {
                    return Word.lt;
                }

            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                }
                else {
                    return Word.gt;
                }

            case (char)-1:
                return new Token(Tag.EOF);

            default:
                if (Character.isLetter(peek) || peek == '_') {
                	boolean valido = Character.isLetter(peek);
                    String s= String.valueOf(peek);
                    readch(br);
                    while(Character.isLetter(peek) || Character.isDigit(peek) || peek == '_'){
                    	if (!valido)
                    		valido = (peek != '_');
                        s+=String.valueOf(peek);
                        readch(br);
                    }

                    if (!valido){
                    	System.err.println("Erroneous identifier"
                             + s );
                    	return null;
                    }

                    if (s.equals("cond"))
                        return Word.cond;
                    else if (s.equals("when"))
                        return Word.when;
                    else if (s.equals("then"))
                        return Word.then;
                    else if (s.equals("else"))
                        return Word.elsetok;
                    else if (s.equals("while"))
                        return Word.whiletok;
                    else if (s.equals("do"))
                        return Word.dotok;
                    else if (s.equals("seq"))
                        return Word.seq;
                    else if (s.equals("print"))
                        return Word.print;
                    else if (s.equals("read"))
                        return Word.read;
                    else
                        return new Word(Tag.ID,s);

                } else if (Character.isDigit(peek)) {
                    String s= String.valueOf(peek);
                    readch(br);
                    if (!(s.equals("0"))){
                        while(Character.isDigit(peek)){
                            s+=String.valueOf(peek);
                            readch(br);
                        }
                    }
                    
                    int n = Integer.parseInt(s);
                    return new NumberTok(n);

                } else {
                        System.err.println("Erroneous character: " 
                                + peek );
                        return null;
                }
        }
    }
		
    public static void main(String[] args) {
        
        Lexer lex = new Lexer();
        String path = "try.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}
        
    }

}
