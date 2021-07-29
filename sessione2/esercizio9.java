//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 2
//Data: 18/10/19
//esercizio9.java

import java.util.Scanner;

public class esercizio9
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);
	    switch (state) {
	    case 0:
			if (ch == 'J')
				state = 1;
			else
				state = 4;
			break;

	    case 1:
			if (ch == 'o')
			    state = 2;
			else 
			    state = 5;
			break;

		case 2:
			if (ch == 's')
			    state = 3;
			else 
			    state = 6;
			break;

		case 3:
			state = 7;
			break;

		case 4:
			if (ch == 'o')
			    state = 5;
			else 
			    state = 8;
			break;

		case 5:
			if (ch == 's')
			    state = 6;
			else 
			    state = 8;
			break;

		case 6:
			if (ch == 'e')
			    state = 7;
			else 
			    state = 8;
			break;

		case 7:
			state = 8;
			break;

		// Se è lo stato 8 no si fa niente

	    }
	}
	return state == 7;
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
