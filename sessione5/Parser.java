//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 5
//Data: 15/11/19
//Parser.java

import java.io.*;



public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
		throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
		if (look.tag == t) {
		    if (look.tag != Tag.EOF) move();
		} else error("syntax error");
    }

    public void start() {
    	if (look.tag == '(' || look.tag == Tag.NUM){
			expr();
			match(Tag.EOF);
		}
		else{
			error("error in start");
		}
    }

    private void expr() {
    	if (look.tag == '(' || look.tag == Tag.NUM){
	    	term();
	    	exprp();
	    }
		else{
			error("error in expr");
		}
    }

    private void exprp() {
		switch (look.tag) {
			case '+':
				match('+');
				term();
				exprp();
				break;
			
			case '-':
				match('-');
				term();
				exprp();
				break;
			
			default:
				if (look.tag != ')' && look.tag != Tag.EOF) {
					error("error in exprp");
				}
				// else epsilon
				break;
			
		}
    }

    private void term() {
		if (look.tag == '(' || look.tag == Tag.NUM){
			fact();
	   		termp();
		}
		else {
			error("error in term");
		}
    }

    private void termp() {
        switch (look.tag) {
	        case '*':
	        	match('*');
				fact();
				termp();
				break;

			case '/':
				match('/');
				fact();
				termp();
				break;

			default:
				if (look.tag != '+' && look.tag != '-' && look.tag != ')' && look.tag != Tag.EOF){
					error("error in termp");
				}
				//else epsilon
				break;
		}

    }

    private void fact() {
        switch (look.tag) {
	        case '(':
	        	match('(');
	        	expr();
	        	match(')');
	        	break;

	        case Tag.NUM:
	        	match(Tag.NUM);
	        	break;

	        default:
	        	error("error in fact");
	        	break;

        }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "try.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}