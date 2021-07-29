//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 2
//Data: 18/10/19
//esercizio10.java

import java.util.Scanner;

public class esercizio10
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);
	    switch (state) {
	    case 0:
			if (ch == '/')
				state = 1;
			else if (ch == '*' || ch == 'a')
				state = 5;
			else
				state = -1;
			break;

	    case 1:
			if (ch == '*')
			    state = 2;
			else if (ch == '/' || ch == 'a')
			    state = 5;
			else
				state = -1;
			break;

		case 2:
			if (ch == 'a' || ch == '/')
			    state = 2;
			else if (ch == '*')
			    state = 3;
			else
				state =-1;
			break;

		case 3:
			if (ch == 'a')
				state = 2;
			else if (ch == '/')
				state = 4;
			else if (ch == '*')
				state = 3;
			else
				state = -1;
			break;

		case 4:
			if (ch == 'a' || ch == '/' || ch == '*')
			    state = 5;
			else 
			    state = -1;
			break;

		case 5:
			if (ch == 'a' || ch == '/' || ch == '*')
			    state = 5;
			else 
			    state = -1;
			break;

	    }
	}
	return state == 4;
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
