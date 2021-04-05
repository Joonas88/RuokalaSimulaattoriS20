package simu;
import eduni.distributions.*;
/**
 * Luokka generoi uusia saapumisia simulaattorin simuloimaan järjestelmään. Simulaattori ei pyörisi ilman uusia saapumisia.
 * 
 * @author Joonas Soininen
 * @version 1.0
 */
public class Saapumisprosessi {
	/**
	 * Olio moottori on Moottori-luokan ilmentymä jolla pystytään käyttämään kyseisen luokan metodeja, eli esimerkiski luomaan uusia tapahtumia.
	 */
	private Moottori moottori;
	/**
	 * Olio generator on ContinuousGenerator-luokan ilmentymä jolla pystytään käyttämään kyseisen luokan metodeja, se tuottaa satunnaisia jakaumia.
	 */
	private ContinuousGenerator generaattori;
	/**
	 * Muuttuja yyyppi on enum joka määrittää mikä tapahtuma simulaattorissa on meneillään.
	 */
	private TapahtumanTyyppi tyyppi;

	/* Testej� varten,  ks. alla oleva kommentoitu osuus  
	int i = 0;
	double sum = 0;
	double max = 0;
	double min = 100;
	*/
	
	/**
	 * Konstruktori Saapumisprosessi saa parametreikseen moottori-olion, jakauman lukuarvot ja tapahtuman tyypin.
	 * @param m on moottori-olion ilmentymä.
	 * @param g on jakauman lukujen arvo.
	 * @param tyyppi on tapahtumatyypin määritelmä ja se on tässä tapauksessa aina ARR1.
	 */
	public Saapumisprosessi(Moottori m, ContinuousGenerator g, TapahtumanTyyppi tyyppi){
		this.moottori = m;
		this.tyyppi = tyyppi;
		this.generaattori = g;
	}

	/**
	 * Metodi genroiSeuraava luo aina seuraavan tulevan tapahtuman, t-olion, jolle annetaan useita eri arvoja muista luokista sekä kutsutaan 
	 * Moottori-luokan metodia uusiTapahtuma.
	 */
	public void generoiSeuraava(){
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika()+generaattori.sample());
		moottori.uusiTapahtuma(t);
		
		/* Generaattorin tuottamien lukujen tutkimista (keskiarvo, peinin, suurin) 		
		double aika = generaattori.sample();
		sum = sum + aika;
		i++;
		System.out.println(sum/i);
		System.out.println(min);
		System.out.println(max);
		System.out.println();
		if (max < aika) max = aika;
		if (min > aika) min = aika;
		*/
	}

}
