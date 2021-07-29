//Autore: Jose Manuel Sanchez Aquilue
//Asignatura: LFT
//Sessione 4
//Data: 8/11/19
//NumberTok.java
public class NumberTok extends Token {
    public final int numero;
  	public NumberTok(int n) { super(Tag.NUM); numero=n; }
	public String toString() { return "<" + Tag.NUM + ", " + numero + ">"; }
}
