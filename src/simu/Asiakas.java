package simu;
/**
 * Luokka toimii asiakkaiden määrittelyluokkana. Simulaattoriin saapuva asiakas generoidaan tällä luokalla ja asiakkaalle annetaan tiettyjä arvoja.
 * 
 * @author Joonas Soininen
 * @version 1.3
 */
public class Asiakas {
	/**
	 * Muuttuja saapumisaika on asiakkaan simulaattoriin saapumista merkitsevä aika.
	 */
	private double saapumisaika;
	/**
	 * Muuttuja poistumisaika on asikkaan simulaattorista poistumista merkitsevä aika.
	 */
	private double poistumisaika;
	/**
	 * Muuttuja aloitusaika on asiakkaan palvelun aloittamista kuvaava aika palvelupisteessä.
	 */
	private double aloitusaika;
	/**
	 * Muuttuja lopetusaika on asikkaan palvelun lopettamista kuvaava aika palvelupisteessä.
	 */
	private double lopetusaika;
	/**
	 * Muuttuja id on asiakkaan id simulaattorissa.
	 */
	private int id;
	/**
	 * Muuttuja i on staattinen arvo, jolla määritellään id:n arvo.
	 */
	private static int i = 1;
	/**
	 * Muuttuja sum on asiakkaan viipymisaika simulaattorissa.
	 */
	private static long sum = 0;
	
	/**
	 * Konstruktorissa Asiakas annetaan oliolle id, simulaattoriin saapumisaika sekä palvelupisteen palvelun aloitusaika.
	 */
	public Asiakas(){
	    id = i++;
	    aloitusaika= Kello.getInstance().getAika();
		saapumisaika = Kello.getInstance().getAika();
		Trace.out(Trace.Level.INFO, "Uusi asiakas: " + id + " : "+saapumisaika);
	}

	/**
	 * Metodilla getPoistumisaika saadaan asiakkaan simulaattorissa viettämä koknaisaika.
	 * @return palauttaa asiakas-olion simulaattorissa viettämän ajan,
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/**
	 * Metodi setPoistumisaika asetetaan asiakas-olion poistumisaika muuttujaan.
	 * @param poistumisaika on asikas-olion aikaleima simulaattorista poistuessa.
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/**
	 * Metodi getSaapumisaika on asiakas-olion simulaattoriin saapumista kuvaava aika.
	 * @return palauttaa asiakas-olion simulaattoriin annetun aikaleiman olion saapuessa.
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}
	
	/**
	 * Metodi setSaapumisaika asettaa asiakas-olion saapumisajan muuttujaan.
	 * @param saapumisaika on asiakas-olion aikaleima simulaattroiin saapueassa.
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}
	
	/**
	 * Metodilla raportti tulostetaan konsoliin asiakas-olion arvoja simuloinnin etenimsen tarkkailuksi.
	 */
	public void raportti(){
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui: " +saapumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui: " +poistumisaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi: " +(poistumisaika-saapumisaika));
		sum += (poistumisaika-saapumisaika);
		double keskiarvo = sum/id;
		Trace.out(Trace.Level.INFO, "Asiakkaiden läpimenoaikojen keskiarvo "+ keskiarvo);
	}
	
	/**
	 * Metodi getAloitusaika on asiakas-olion palvelupisteeseen saapumista kuvaava aika.
	 * @return palauttaa asiakas-olion simulaattoriin annetun aikaleiman olion saapuessa palvelupisteeseen.
	 */
	public double getAloitusaika() {
		return aloitusaika;
	}

	/**
	 * Metodilla getPoistumisaika saadaan asias-olion palvelupisteessä viettämä koknaisaika.
	 * @return palauttaa asiakas-olion palvelupisteess viettämän ajan,
	 */
	public double getLopetusaika() {
		return lopetusaika;
	}

	/**
	 * Metodi setAloitusaika asettaa asiakas-olion saapumisajan palvelupisteeseen muuttujaan.
	 * @param aloitusaika on asiakas-olion aikaleima palvelupisteeseen saapueassa.
	 */
	public void setAloitusaika(double aloitusaika) {
		this.aloitusaika = aloitusaika;
	}

	/**
	 * Metodi setLopetusaika asetetaan asiakas-olion pavlelupisteestä poistumisajan muuttujaan.
	 * @param lopetusaika on asikas-olion aikaleima palvelupisteestä poistuessa.
	 */
	public void setLopetusaika(double lopetusaika) {
		this.lopetusaika = lopetusaika;
	}
	
	/**
	 * Metodi palveluaika tulostaa konsoliin asiakas-olion id:n sekä palvelupisteessä viettämän ajan.
	 */
	public void palveluaika() {
		Trace.out(Trace.Level.INFO, "Asiakas "+id+ " saapui palvelupisteeseen: " +aloitusaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " poistui palvelupisteestä: " +lopetusaika);
		Trace.out(Trace.Level.INFO,"Asiakas "+id+ " viipyi palvelupisteessä: " +(lopetusaika-aloitusaika));
	}
	
	/**
	 * Metodi ppPalveluaika palauttaa asiakas-olion palvelupisteessä viettämän ajan.
	 * @return palauttaa ajan jonka asiakas-olio vietti palvelupisteessä.
	 */
	public double ppPalveluaika() {
		return lopetusaika-aloitusaika;
	}
}
