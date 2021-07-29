//Corso: LFT
//Sessione 8
//Data: 6/12/19
//NumberTok.java
public class NumberTok extends Token {
    public final int numero;
  	public NumberTok(int n) { super(Tag.NUM); numero=n; }
	public String toString() { return "<" + Tag.NUM + ", " + numero + ">"; }
}
