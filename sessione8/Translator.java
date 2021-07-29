//Autore: Jose Manuel Sanchez Aquilue
//Corso: LFT
//Sessione 8
//Data: 6/12/19
//Translator.java
import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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
		} else error("syntax error");    }

    public void prog() {        
		if (look.tag == '('){
	        int lnext_prog = code.newLabel();
	        stat(lnext_prog);
	        code.emitLabel(lnext_prog);
	        match(Tag.EOF);
	        try {
	        	code.toJasmin();
	        }
	        catch(java.io.IOException e) {
	        	System.out.println("IO error\n");
	        };
	    }
		else {
	    		error("error in prog.");
    	}
    }

    private void statlist() {
	if (look.tag == '('){
    		int lnext_prog = code.newLabel();
	        stat(lnext_prog);
	        code.emitLabel(lnext_prog);
    		statlistp();
    	}
    	else {
    		error("error in statlist.");
    	}
    }


	private void statlistp() {
		if (look.tag == '('){
    		int lnext_prog = code.newLabel();
	        stat(lnext_prog);
	        code.emitLabel(lnext_prog);
    		statlistp();
    	}
    	else if (look.tag != ')'){
    		error("error in statlistp.");
    	}
    	// else epsilon
    }

    private void stat(int lnext) {
    	if (look.tag == '('){
    		match('(');
    		statp(lnext);
    		match(')');
    	}
    	else{
    		error("error in stat.");
    	}
    }


    public void statp(int lnext) {
        switch(look.tag) {
			case '=':
    			match('=');
    			if (look.tag==Tag.ID) {
    				int read_id_addr = st.lookupAddress(((Word)look).lexeme);
                    if (read_id_addr==-1) {
                        read_id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }
    				match(Tag.ID);
    				expr();
    				code.emit(OpCode.istore,read_id_addr);
                }
                else
                    error("Error in grammar (stat) after = with " + look);
                break;
    			

    		case Tag.COND:
    			match(Tag.COND);
    			int lnext_prog = code.newLabel();    			
    			bexpr(lnext_prog);
    			int lnext_else = code.newLabel();
    			code.emit(OpCode.GOto,lnext_else);
    			code.emitLabel(lnext_prog);
    			stat(lnext);
    			code.emit(OpCode.GOto,lnext);
    			code.emitLabel(lnext_else);
    			elseopt(lnext);
    			break;

    		case Tag.WHILE:
    			match(Tag.WHILE);
    			int inic = code.newLabel();
    			code.emitLabel(inic);
    			lnext_prog = code.newLabel();
    			bexpr(lnext_prog);
    			code.emit(OpCode.GOto,lnext);
    			code.emitLabel(lnext_prog);
    			stat(inic);
    			code.emit(OpCode.GOto,inic);
    			break;

    		case Tag.DO:
    			match(Tag.DO);
    			statlist();
    			break;

    		case Tag.PRINT:
    			match(Tag.PRINT);
    			exprlist();
    			code.emit(OpCode.invokestatic,1);
    			break;

            case Tag.READ:
                match(Tag.READ);
                if (look.tag==Tag.ID) {
                    int read_id_addr = st.lookupAddress(((Word)look).lexeme);
                    if (read_id_addr==-1) {
                        read_id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }                    
                    match(Tag.ID);
                    code.emit(OpCode.invokestatic,0);
                    code.emit(OpCode.istore,read_id_addr);   
                }
                else
                    error("Error in grammar (stat) after read with " + look);
                break;

            default:
    			error("error in statp.");
    			break;
        }
     }

	private void elseopt(int lnext) {
    	switch(look.tag){
    		case '(':
    			match('(');
    			match(Tag.ELSE);
    			stat(lnext);
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

    private void bexpr(int lnext) {
    	if (look.tag == '('){
    		match('(');
    		bexprp(lnext);
    		match(')');
    	}
    	else{
    		error("error in bexpr.");
    	}
    	
    }

    private void bexprp(int lnext) {
    	if (look.tag == Tag.RELOP){
    		String cond = ((Word)look).lexeme;
    		match(Tag.RELOP);
    		expr();
    		expr();
    		if (cond == "==")
    			code.emit(OpCode.if_icmpeq,lnext);
    		else if (cond == "<=")
    			code.emit(OpCode.if_icmple,lnext);
			else if (cond == "<")
    			code.emit(OpCode.if_icmplt,lnext);
    		else if (cond == "<>")
    			code.emit(OpCode.if_icmpne,lnext);
    		else if (cond == ">=")
    			code.emit(OpCode.if_icmpge,lnext);
    		else if (cond == ">")
    			code.emit(OpCode.if_icmpgt,lnext);
    	}
    	else{
    		error("error in bexprp.");
    	}
    }

    private void expr() {
    	if (look.tag == Tag.ID) {
    		int read_id_addr = st.lookupAddress(((Word)look).lexeme);
            if (read_id_addr==-1) {
                error("ID "+ ((Word)look).lexeme +"does not exits.");
            }
            match(Tag.ID);
            code.emit(OpCode.iload,read_id_addr);
    	}
    	else if (look.tag == Tag.NUM) {
    		code.emit(OpCode.ldc, ((NumberTok)look).numero);
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
        switch(look.tag) {
			case '+':
    			match('+');
    			exprlist();
    			code.emit(OpCode.iadd);    			
    			break;

    		case '-':
    			match('-');
    			expr();
    			expr();
    			code.emit(OpCode.isub);
    			break;

    		case '*':
    			match('*');
				exprlist();
    			code.emit(OpCode.imul);
    			break;

    		case '/':
    			match('/');
    			expr();
    			expr();
    			code.emit(OpCode.idiv);
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
        String path = "input.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator trans = new Translator(lex, br);
            trans.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }

}
