package simu;
/**
 * Luokka on tapahtumien luomiseen tarkoitettu sekä pitää tapahtumia järjestyksessä.
 * 
 * @author Joonas Soininen
 * @version 1.0
 */

public class Tapahtuma implements Comparable<Tapahtuma> {
	
	/**
	 * Olio tyyppi kuvastaa enumia joka annetaan jokaisen tapahtuman yhteyteen määrittäen koska se tapahtuma tapahtuu.
	 */
	private TapahtumanTyyppi tyyppi;
	/**
	 * Muuttuja aika saa arovkseen juoksevan kellonajan.
	 */
	private double aika;
	
	/**
	 * Konstruktori Tapahtuma saa suoraan parametreiski olion tyyppi ja muuttuja aika.
	 * @param tyyppi määrittää tapahtuman tyypin, esim. ARR1 tai DEP3A.
	 * @param aika on juokseva kellonaika joka on määritetty tapahtuman tapahtumajaksi.
	 */
	public Tapahtuma(TapahtumanTyyppi tyyppi, double aika){
		this.tyyppi = tyyppi;
		this.aika = aika;
	}
	
	/**
	 * Metodi setTyyppi määrittää tapahtumalle oikean enumin.
	 * @param tyyppi on metodia kutsuttaessa jo määritetty tapahtumantyyppi.
	 */
	public void setTyyppi(TapahtumanTyyppi tyyppi) {
		this.tyyppi = tyyppi;
	}
	
	/**
	 * Metodi getTyyppi palauttaa asetetun tapahtumantyypin.
	 * @return palauttaa asetetun tapahtumantyypin, esim ARR1, DEP4.
	 */
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}
	
	/**
	 * Metodi setAika määrittää juoksevan kellonajan joka annetaan metodia kutsuttaessa.
	 * @param aika on kellonaika.
	 */
	public void setAika(double aika) {
		this.aika = aika;
	}
	
	/**
	 * Metodi getAika palauttaa muuttujalle aika annetun kellonajan.
	 * @return palauttaa muuttujalle aika annetun kellonajan.
	 */
	public double getAika() {
		return aika;
	}

	/**
	 * Metodi compareTo vertailee tapahtumaikoja ja palauttaa vertailuoperaattorien määräämiä arvoja.
	 * @param arg on Tapahtuma-luokan olio.
	 * @return palauttaa vertailuoperaattiorien mukaisen arvon 0, 1 tai -1.
	 */
	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
	

}
