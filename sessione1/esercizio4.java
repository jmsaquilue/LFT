//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 1
//Data: 11/10/19
//esercizio4.java

import java.util.Scanner;

public class esercizio4
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
			if (ch == ' ')
				state = 0;
			else if (Character.isDigit(ch))
			    state = (ch%2)+1;
			else if (Character.isLetter(ch))
			    state = 8;
			else
				state = -1;
			break;

	    case 1:
			if (Character.isDigit(ch))
			    state = (ch%2)+1;
			else if (ch == ' ')
				state = 3;
			else if (ch >= 'A'&& ch <= 'K')
			    state = 5;
			else if (Character.isLetter(ch))
				state = 8;
			else 
			    state = -1;
			break;

		case 2:
			if (Character.isDigit(ch))
			    state = (ch%2)+1;
			else if (ch == ' ')
				state = 4;
			else if (ch >= 'L'&& ch <= 'Z')
			    state = 5;
			else if (Character.isLetter(ch))
				state = 8;
			else 
			    state = -1;
			break;

		case 3:
			if (ch == ' ')
				state = 3;
			else if (ch >= 'A'&& ch <= 'K')
			    state = 5;
			else if (Character.isLetter(ch) || Character.isDigit(ch))
				state = 8;
			else 
			    state = -1;
			break;

		case 4:
			if (ch == ' ')
				state = 4;
			else if (ch >= 'L'&& ch <= 'Z')
			    state = 5;
			else if (Character.isLetter(ch) || Character.isDigit(ch))
				state = 8;
			else 
			    state = -1;
			break;

		case 5:
			if (Character.isLetter(ch))
				state = 5;
			else if (ch == ' ')
				state = 6;
			else if (Character.isDigit(ch))
				state = 8;
			else 
			    state = -1;
			break;

		case 6:
			if (Character.isUpperCase(ch))
				state = 5;
			else if (ch == ' ')
				state = 7;
			else if (Character.isDigit(ch) || Character.isLowerCase(ch))
				state = 8;
			else 
			    state = -1;
			break;

		case 7:
			if (ch == ' ')
				state = 7;
			else if (Character.isDigit(ch) || Character.isLetter(ch))
				state = 8;
			else 
			    state = -1;
			break;

		case 8:
			if (Character.isDigit(ch) || Character.isLetter(ch) || ch == ' ')
				state = 8;
			else 
			    state = -1;
			break;

	    }
	}
	return state >= 5 && state <= 7;
    }

    public static void main(String[] args)
    {
    	Scanner input = new Scanner(System.in);
    	while (input.hasNext()) {
    		String w = input.nextLine();
    		System.out.print(w + " :\t");    		
    		System.out.println(scan(w) ? "OK" : "NOPE");
		}
    }
}
