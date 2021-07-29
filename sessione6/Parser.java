//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 6
//Data: 22/11/19
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

    public void prog() {
    	if (look.tag == '('){
    		stat();
    		match(Tag.EOF);
    	}
    	else {
    		error("error in prog.");
    	} 	
    }

    private void statlist() {
	if (look.tag == '('){
    		stat();
    		statlistp();
    	}
    	else {
    		error("error in statlist.");
    	}
    }

    private void statlistp() {
	if (look.tag == '('){
    		stat();
    		statlistp();
    	}
    	else if (look.tag != ')'){
    		error("error in statlistp.");
    	}
    	// else epsilon
    }

    private void stat() {
    	if (look.tag == '('){
    		match('(');
    		statp();
    		match(')');
    	}
    	else{
    		error("error in stat.");
    	}
    }

    private void statp() {
    	switch(look.tag){
    		case '=':
    			match('=');
    			match(Tag.ID);
    			expr();
    			break;

    		case Tag.COND:
    			match(Tag.COND);
    			bexpr();
    			stat();
    			elseopt();
    			break;

    		case Tag.WHILE:
    			match(Tag.WHILE);
    			bexpr();
    			stat();
    			break;

    		case Tag.DO:
    			match(Tag.DO);
    			statlist();
    			break;

    		case Tag.PRINT:
    			match(Tag.PRINT);
    			exprlist();
    			break;

    		case Tag.READ:
    			match(Tag.READ);
    			match(Tag.ID);
    			break;

    		default:
    			error("error in statp.");
    			break;

    	}
    	

    }

    private void elseopt() {
    	switch(look.tag){
    		case '(':
    			match('(');
    			match(Tag.ELSE);
    			stat();
    			match(')');
    			break;

    		default:
    			if (look.tag != ')'){
    				error("error in elseopt.");
    			}
    			//else epsilon
    			break;
    		
    	}
    }

    private void bexpr() {
    	if (look.tag == '('){
    		match('(');
    		bexprp();
    		match(')');
    	}
    	else{
    		error("error in bexpr.");
    	}
    	
    }
    
    private void bexprp() {
    	if (look.tag == Tag.RELOP){
    		match(Tag.RELOP);
    		expr();
    		expr();
    	}
    	else{
    		error("error in bexprp.");
    	}
    }

    private void expr() {
    	if (look.tag == Tag.ID) {
    		match(Tag.ID);
    	}
    	else if (look.tag == Tag.NUM) {
    		match(Tag.NUM);
    	}
    	else if (look.tag == '('){
    		match('(');
    		exprp();
    		match(')');
    	}
    	else{
    		error("error in expr.");
    	}
    }

    private void exprp() {
    	switch(look.tag){
    		case '+':
    			match('+');
    			exprlist();
    			break;

    		case '-':
    			match('-');
    			expr();
    			expr();
    			break;

    		case '*':
    			match('*');
				exprlist();
    			break;

    		case '/':
    			match('/');
    			expr();
    			expr();
    			break;

    		default:
    			error("error in exprp.");
    			break;

    	}
    }

    private void exprlist() {
    	if (look.tag == Tag.ID|| look.tag == Tag.NUM ||look.tag == '(') {
	    	expr();
	    	exprlistp();
	    }
	    else{
	    	error("error in exprlist.");
	    }
    }

    private void exprlistp() {
    	if (look.tag == Tag.ID|| look.tag == Tag.NUM ||look.tag == '(') {
	    	expr();
    		exprlistp();
	    }
	    else if (look.tag != ')'){
	    	error("error in exprlistp.");
	    }
    	
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "input.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
