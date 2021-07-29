//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 1
//Data: 11/10/19
//esercizio1.java

import java.util.Scanner;

public class esercizio1
{
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
			if (ch == '0')
			    state = 1;
			else if (ch == '1')
			    state = 0;
			else
			    state = -1;
			break;

	    case 1:
			if (ch == '0')
			    state = 2;
			else if (ch == '1')
			    state = 0;
			else
			    state = -1;
			break;

	    case 2:
			if (ch == '0')
			    state = 3;
			else if (ch == '1')
			    state = 0;
			else
			    state = -1;
			break;

	    case 3:
			if (ch == '0' || ch == '1')
			    state = 3;
			else
			    state = -1;
			break;
	    }
	}
	return (state != 3 && state != -1);
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
