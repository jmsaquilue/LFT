//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 1
//Data: 11/10/19
//esercizio5.java

import java.util.Scanner;

public class esercizio5
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
		    if (ch>='A' && ch<='K')
				state = 1;
			else if (ch>='L' && ch<='Z')
			    state = 2;
			else if (Character.isDigit(ch) || Character.isLetter(ch))
				state = 7;
			else
				state = -1;
			break;

	    case 1:
		    if (Character.isLetter(ch))
			    state = 1;
			else if (Character.isDigit(ch))
			    state = (ch%2)+3;
			else 
			    state = -1;
			break;

		case 2:
		    if (Character.isLetter(ch))
			    state = 2;
			else if (Character.isDigit(ch))
			    state = (ch%2)+5;
			else 
			    state = -1;
			break;

		case 3:
			if (Character.isDigit(ch))
			    state = (ch%2)+3;
			else if (Character.isLetter(ch))
			    state = 7;
			else 
			    state = -1;
			break;

		case 4:
			if (Character.isDigit(ch))
			    state = (ch%2)+3;
			else if (Character.isLetter(ch))
			    state = 7;
			else 
			    state = -1;
			break;

		case 5:
			if (Character.isDigit(ch))
			    state = (ch%2)+5;
			else if (Character.isLetter(ch))
			    state = 7;
			else 
			    state = -1;
			break;

		case 6:
			if (Character.isDigit(ch))
			    state = (ch%2)+5;
			else if (Character.isLetter(ch))
			    state = 7;
			else 
			    state = -1;
			break;

		case 7:
			if (Character.isDigit(ch) || Character.isLetter(ch))
			    state = 7;
			else 
			    state = -1;
			break;

	    }
	}
	return state == 3 || state == 6;
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
