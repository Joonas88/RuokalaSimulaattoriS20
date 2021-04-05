package simu;
/**
 * Luokka on tulosutsen määrittelyä ja seuraamista varten. Tulostuksille voidaan antaa eri arvoja ja tätä luokkaa muuttamalla määritellä
 * mitä tulostuu konsoliin.
 * 
 * @author Joonas Soininen
 * @version 1.1
 */
public class Trace {

	/**
	 * Enum Level määrittää seurattaville konsolitulostuksille arvot.
	 */
	public enum Level{INFO, WAR, ERR}
	/**
	 * Olio  tracelevel pitää sisällään eri arvot seuranta tageille.
	 */	
	private static Level traceLevel;
	
	/**
	 * Metodi setTraceLevel saa parametrinä seurattavan tulostuksen yhteydessä määritellyn arvon.
	 * @param lvl on arvo mikä määritellään kutsuttaessa metodia. Määrää mikä tulostuu konsoliin
	 */
	public static void setTraceLevel(Level lvl){
		traceLevel = lvl;
	}
	
	/**
	 * Metodi out saa parametreinä lvl-muuttujan ja txt-muuttuja. lvl määrittää mitä tulostetaan vertailuoperaattorin avulla ja txt taas mitä 
	 * tulostetaan konsoliin.
	 * @param lvl on Level-muttujan asettama arvo.
	 * @param txt on tekstiä joka tulostuu konsoliin.
	 */
	public static void out(Level lvl, String txt){
		if (lvl.ordinal() > traceLevel.ordinal()){ //Tuonne =, jos tarve nähdä dataa
			System.out.println(txt);
		}
	}
	
	
	
}