package simu;

import java.util.LinkedList;

import eduni.distributions.ContinuousGenerator;
/**
 * Luokan tarkoitus on kuvastaa simulaattorissa palvelupistettä. Se määrittää yhden palvelupisteen, sille jonon, sen statuksen ja laskee myös palveluajan.
 * 
 * @author Joonas Soininen
 * @version 1.2
 */

public class Palvelupiste {

	/**
	 * Olio moottori on Moottori-luokan ilmentymä jolla pystytään käyttämään kyseisen luokan metodeja, eli esimerkiski luomaan uusia tapahtumia.
	 */
	private Moottori moottori;
	/**
	 * Olio generator on ContinuousGenerator-luokan ilmentymä jolla pystytään käyttämään kyseisen luokan metodeja, se tuottaa satunnaisia jakaumia.
	 */
	private ContinuousGenerator generator;
	/**
	 * Olio jono on linkedlist tyyppinen ilmentymä, linkedlist on lista joka toimii periaatteella ensiksi sisään, ensiksi ulos. Se saa Asiakas-luokan
	 * parametrinä.
	 */
	private LinkedList<Asiakas> jono = new LinkedList<Asiakas>();
	/**
	 * Muuttuja skeduloitavanTapahtumanTyyppi on enum joka määrittää mikä tapahtuma simulaattorissa on meneillään.
	 */
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	/**
	 * Muuttuja busy on palvelupisteen palveluaikaa tallentava muuttuja.
	 */
	private double busy=0;
	/**
	 * Muuttuja varttu on palavelupisteen käyttöä kuvaava true/false muuttuja.
	 */
	private boolean varattu = false;

	//JonoStartegia strategia; //optio: asiakkaiden j�rjestys
	
	/**
	 * Konstruktori Palvelupiste määrittää uudelle palvelupisteelle arvot, satunnaislukujakauman, moottorin ominaisuudet sekä mikä palvelupiste on
	 * kyseessä. Arvon määräytyvät Moottori-luokassa syötetyistä parametreistä.
	 * @param generator on jakauman lukujen määrittelyyn tarvittavat arvot.
	 * @param moottori on moottori-olion ilmentymä.
	 * @param tyyppi on oikean tapahtuman tyyppi palvelupisteelle, esim ARR1 tai DEP2C.
	 */
	public Palvelupiste(ContinuousGenerator generator, Moottori moottori, TapahtumanTyyppi tyyppi){
		this.moottori = moottori;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;				
	}

	/**
	 * Metodi lisaaJonoon lisää asiakas-olion palvelupisteen jonoon.
	 * @param a on Asiakas-luokan olio, eli asiakas.
	 */
	public void lisaaJonoon(Asiakas a){   // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		
	}

	/**
	 * Metodi otaJonosta ottaa aina seuraavan asiakas-olion jonosta. Metodi myös muuttaa varattu-muuttujan arvoon false.
	 * @return palauttaa otetun asiakas-olion jonosta.
	 */
	public Asiakas otaJonosta(){  // Poistetaan palvelussa ollut
		varattu = false;
		return jono.poll();
	}

	/**
	 * Metodi aloitaPalvelu aloittaa palvelupisteen palvelun, muuuttaa varattu-muutujan tilaan true, asettaa muuttujalle palveluaika arvon, 
	 * kutsuu Moottoriluokan metodia uusiTapåahtuma antaen sille muita metode kutsumalla parametrit sekä ynnää palvelupisteen palveluajan muuttujaan
	 * busy.
	 */
	public void aloitaPalvelu(){  //Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		varattu = true;
		double palveluaika = generator.sample();
		moottori.uusiTapahtuma(new Tapahtuma(skeduloitavanTapahtumanTyyppi,Kello.getInstance().getAika()+palveluaika));
		busy+=palveluaika;
	}

	/**
	 * Metro onVarattu toimii varattu-muuttujan arvon palauttajana.
	 * @return palauttaa joko true tai false riippuen muuttujan asetetusta arvosta.
	 */
	public boolean onVarattu(){
		return varattu;
	}

	/**
	 * Metodi onJonossa palauttaa jonon tilan.
	 * @return palauttaa joko true tai false.
	 */
	public boolean onJonossa(){
		return jono.size() != 0;
	}
	
	/**
	 * Metodi jononPituus palauttaa palvelupisteen jonon pituuden.
	 * @return palauttaa numeroarvona jonon pituuden.
	 */
	public int jononPituus() {
		return jono.size();
	}
	
	/**
	 * Metodi getBusy palauttaa palvelupisteen palveluajan.
	 * @return palauttaa numeerisen arvon palvelupisteen palveluajasta.
	 */
	public double getBusy() {
		return busy;
	}
	
	

}
