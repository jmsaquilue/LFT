//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 2
//Data: 18/10/19
//esercizio7.java

import java.util.Scanner;

public class esercizio7
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
			if (ch == 'a')
				state = 4;
			else if (ch == 'b')
			    state = 1;
			else
				state = -1;
			break;

	    case 1:
			if (ch == 'a')
			    state = 4;
			else if (ch == 'b')
				state = 2;
			else 
			    state = -1;
			break;

		case 2:
			if (ch == 'a')
			    state = 4;
			else if (ch == 'b')
				state = 3;
			else 
			    state = -1;
			break;

		case 3:
			if (ch == 'a' || ch == 'b')
				state = 3;
			else 
			    state = -1;
			break;

		case 4:
			if (ch == 'a' || ch == 'b')
				state = 4;
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
