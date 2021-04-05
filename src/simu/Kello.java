package simu;
/**
 * Luokka on simulaattorin kello jonka tarkoitus on tuottaa aika jota simulaattorissa käytetään.
 * 
 * @author Joonas Soininen
 * @version 1.1
 */

public class Kello {
	/**
	 * Muuttuja aika on kellonaikaa kuvaava.
	 */
	private double aika;	
	/**
	 * Olio instanssi on varmistamassa, että jokainen luokkaa kutsuva saa itselleen kellonajan.
	 */
	private static Kello instanssi;
	
	/**
	 * Kontruktorissa alustetaan muuttuja aika arvoon 0.
	 */
	private Kello(){
		aika = 0;
	}
	
	/**
	 * Metodilla getInstance katsotaan instanssi-muuttjan arvo ja sen ollessa null luodaan muuttujasta uusi kello, jolloin se aa itselleen arvon.
	 * @return palauttaa instanssi-muuttujan arvon, joko jo olevan kellonajan tai uuden kellonajan.
	 */
	public static Kello getInstance(){
		if (instanssi == null){
			instanssi = new Kello();	
		}
		return instanssi;
	}
	
	/**
	 * Metodilla setAika asetetaan muuttujalle aika uusi arvo.
	 * @param aika on kellonaika.
	 */
	public void setAika(double aika){
		this.aika = aika;
	}

	/**
	 * Metodilla getAika haetaan sen hetken kellonaika.
	 * @return palauttaa kellonajan.
	 */
	public double getAika(){
		return aika;
	}
}
