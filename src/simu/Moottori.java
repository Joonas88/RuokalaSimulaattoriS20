package simu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import controller.KontrolleriIf;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;
/**
 * Luokka on simulaattorin sydän. Simulaattori pyörii tämän luokan voimalla. Luokassa luodaan palvelupisteet, suoritetaan palvelupisteiden
 * läpikäynti ja laskteaan simulaation eri arvoja. Moottori-luokka on simulaattorin suurin luokka.
 * 
 * @author Joonas Soininen
 * @version 1.8
 */
public class Moottori extends Thread implements MoottoriIf{	
	/**
	 * Olio kontrolleri on tapa käyttää KontroleriIf-rajapintaa ja kutsua sen metodeja.
	 */
	private KontrolleriIf kontrolleri;
	/**
	 * Muuttuja simulointiaika on koko simuloinnin kestoa kuvaava.
	 */
	private double simulointiaika = 0;
	/**
	 * Muuttuja viive antaa simuloinnille viivettä ja auttaa käyttäjää havainnoimaan paremmin simuloinnin kulkua.
	 */
	private long viive = 0;
	/**
	 * Muuttujat syote1-syote6 ovat käyttäjän syöttämiä jakauman arvoja varten.
	 */
	private int syote1, syote2, syote3, syote4, syote5, syote6;
	/**
	 * Olio palveluvisteet määrittää simulaattorissa käytössä olevien palvelupisteiden määrän.
	 */
	private Palvelupiste[] palvelupisteet = new Palvelupiste[9];
	/**
	 * Olio kello on simulaattorin kellonaikaa tuottava.
	 */
	private Kello kello;
	/**
	 * Olio saapumisprosessi avulla kutsutaan kyseisen luokan metodeja.
	 */
	private Saapumisprosessi saapumisprosessi;
	/**
	 * Olio tapahtumalista avulla kutsutaan kyseisen luokan metodeja.
	 */
	private Tapahtumalista tapahtumalista;
	/**
	 * Olio dao vastaa rajapinnan avulla tietokannan käytöstä ja kutsuu tarvittavia metodeja.
	 */
	private IMoottoriDAO dao = new MoottoriAccessObject();
	/**
	 * Muuttujat pp1a-pp4 tallentavat palvelupisteiden asiakasmäärät.
	 */
	private int pp1a=0, pp1b=0, pp2a=0, pp2b=0, pp2c=0, pp2d=0, pp3a=0, pp3b=0, pp4=0;
	/**
	 * Muuttuja asiakkaat vastaa palveluun saapuneiden asiakkaiden määrästä ja sen avulla tieto välitetään käyttöliittymään.
	 */
	private int asiakkaat=0;
	/**
	 * Muuttujat jono1-jono8 tallentavat palvelupisteiden jonojen pituudet sekä ne välitetään käyttöliittymään.
	 */
	private int jono1, jono2, jono3, jono4, jono5, jono6, jono7, jono8;
	/**
	 * Muuttujat aika1a-aika4 tallentavat palvelupisteiden palveluajat.
	 */
	private double aika1a, aika1b, aika2a, aika2b, aika2c, aika2d, aika3a, aika3b, aika4;
	/**
	 * Muuttuja out on käytössä tiedostonimenä tietojen tallennuksen yhteydessä ja sen merkkijonona toimii sen hetken päivämäärä sekä kellonaika.
	 */
	private String out = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss'.txt'").format(new Date());	
	/**
	 * Muutuja formatter formatoi muuttujien arvot kahden desimaalin tarkuudelle, jos desimaaleja on enemmän.
	 */
	private DecimalFormat formatter = new DecimalFormat("#0.00");
	
	/**
	 * Konstruktori Moottori määrittää arvot kontrolleri-oliolle, muuttujille syote1-syote6, pavleupisteet-olioille kello-oliolle,
	 * saapumisprosessi-oliolle, tapahtumalista-oliolle ja kutsuu saapumisprosessi-olin metodia generoiSeuraava. Tämä metodi generoi 
	 * simulaattoriin aina uuden saapumisprosessi, jolloin simulaattori pyörii kunnes ennalta määrättu aika täyttyy.
	 * @param kontrolleri saa parametrit konstruktorin kutsun yhteydessä.
	 */
	public Moottori(KontrolleriIf kontrolleri){
		
		this.kontrolleri = kontrolleri;
		this.syote1=kontrolleri.syote1();
		this.syote2=kontrolleri.syote2();
		this.syote3=kontrolleri.syote3();
		this.syote4=kontrolleri.syote4();
		this.syote5=kontrolleri.syote5();
		this.syote6=kontrolleri.syote6();
		
		palvelupisteet[0]=new Palvelupiste(new Normal(syote1,syote2), this, TapahtumanTyyppi.DEP1A);	
		palvelupisteet[1]=new Palvelupiste(new Normal(syote1,syote2), this, TapahtumanTyyppi.DEP1B);
		palvelupisteet[2]=new Palvelupiste(new Normal(syote3,syote4), this, TapahtumanTyyppi.DEP2A);
		palvelupisteet[3]=new Palvelupiste(new Normal(syote3,syote4), this, TapahtumanTyyppi.DEP2B);
		palvelupisteet[4]=new Palvelupiste(new Normal(syote3,syote4), this, TapahtumanTyyppi.DEP2C);
		palvelupisteet[5]=new Palvelupiste(new Normal(syote3,syote4), this, TapahtumanTyyppi.DEP2D);
		palvelupisteet[6]=new Palvelupiste(new Normal(syote5,syote6), this, TapahtumanTyyppi.DEP3A);		
		palvelupisteet[7]=new Palvelupiste(new Normal(syote5,syote6), this, TapahtumanTyyppi.DEP3B);
		palvelupisteet[8]=new Palvelupiste(new Negexp(dao.getVasen(),dao.getOikea()), this, TapahtumanTyyppi.DEP4);
		
		kello = Kello.getInstance();
		kello.setAika(0);
		
		saapumisprosessi = new Saapumisprosessi(this, new Negexp(5,2),TapahtumanTyyppi.ARR1);
		tapahtumalista = new Tapahtumalista();	
		saapumisprosessi.generoiSeuraava(); // Ensimmäinen saapuminen!!
		
	}

	/**
	 * Metodilla setSimulointiaika annetaan simulaattorille sen suoritusaika, jonka simulaattori pyörii.
	 * Metodin sisällä myös luodaan uusi tekstitiedosto minne tulokset tallennetaan.
	 * @param aika on käyttäjän syöttämä arvo jonka metodi saa kutsun yhteydessä. 
	 */
	@Override
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
		try {
		      File myObj = new File("data\\"+out);
		      if (myObj.createNewFile()) {
		    	  Trace.out(Trace.Level.INFO,"File created: " + myObj.getName()); 
		      } else {
		    	Trace.out(Trace.Level.WAR,"File already exists.");
		      }
		    } catch (IOException e) {
		    	Trace.out(Trace.Level.ERR,"An error occurred.");
		    	e.printStackTrace();
		    }
	}

	/**
	 * Metodilla setViive määritellään mahdollinen simulaattorin hidastus, että käyttäjä voi nähdä tuloksia käyttöliittymästä.
	 * @param viive on käyttäjän syöttämä arvo jonka metodi saa kutsun yhteydessä.
	 */
	@Override
	public void setViive(long viive) {
		this.viive = viive;
	}
	
	/**
	 * Metodilla getViive palautetaan asetettu viivearvo, jolloin tiedetään ettei simulaattori kulje ns. oikeaa nopeutta, vaan kulkua on viivästetty.
	 * @return palauttaa viive-muuttujalle asetetun arvon.
	 */
	@Override
	public long getViive() {
		return viive;
	}
	
	/**
	 * Metodilla run käynnistetään simulaattorin kulku. Metodi toteuttaa while-toistorakennetta jonka sisällä kutsuu viive-metodia, asettaa
	 * kello-oliolle ajan, kutsuu metodia suoritaBtapahtuma ja metodia yritaCTapahtuma. Toistorakenteen loputtua kutsuu metodia tulokset.
	 */
	@Override
	public void run(){
		while (simuloidaan()){
			viive();
			kello.setAika(nykyaika());
			suoritaBTapahtumat();
			yritaCTapahtumat();

		}
		tulokset();
	}
	
	/**
	 * Metodi suoritaBTapahtuma suorittaa while-toistorakenteella tapahtumalistalla olevia tapahtumia ja poistaa suoritetut tapahtumat listasta.
	 * Metodin suoritus jatkuu kunnes simulointiaika on loppunut.
	 */
	private void suoritaBTapahtumat(){
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()){
			suoritaTapahtuma(tapahtumalista.poista());
		}
	}

	/**
	 * Metodi yrutaCTapahtumat aloittaa asiakas-olion palvelun palvelupisteellä, jos palvleupiste on vapaa ja asikas on jonossa.
	 * Metodin suoritus jatkuu kunnes simulointiaika on loppunut, jos ehdot täyttyvät.
	 */
	void yritaCTapahtumat(){
		for (Palvelupiste p: palvelupisteet){
			if (!p.onVarattu() && p.onJonossa()){
				p.aloitaPalvelu();
			}
		}
	}

	/**
	 * Metodi suoritaTapahtuma on Moottori-luokan suurin metodi ja sen avulla suoritetaan monia eri metodeja ja lasketaan monia arvoja.
	 * Metodissa käsitellään asiakas-oliota, palvelupisteet-olioita, kontrolleri-oion metodeja sekä annetaan usealle muuttujalle arvoja.
	 * Switch-case rakenne määrittelee mitä tapahtumaa ja palvelupistettä t-olion avulla. Asiakas a-olio kulkee tapahtumasta toiseen,
	 * eli palvelupisteestä toiseen, kunnes poistuu järjestelmästä.Tapahtuma ARR1 lisää asiakkaan palvelupisteen 1 jonoon a tai b, tämä 
	 * määritellään arpomalla lukua 0 tai 1. Samassa tapahtumassa lasketaan asiakkaat-muuttujaan aina yksi lisää, sekä jonon a tai b arvoon
	 * yksi lisää. Tapahtumassa myös kutsutaan KontrollerIf metodia visualisoiAsiakas joka käyttäjälle käyttöliittymään näyttää uuden asiakkaan.
	 * Taphatumassa myös annetaan arvo muuttujalle jono1 tai jono2 jonka avulla käyttäjälle näytetään jonojen pituudet käyttöliittymässä.
	 * Tapahtuman lopussa Saapumisprosessi-luokan metodia generoiSeuraava kutsutaan, jolloin luodaan uusi saapumistapahutma simulaatioon.
	 * Tapahtumat DEP1A ja DEP1B ottavat a-olion jonosta ja määrittävät olion palveluajan lopun edellisessä palvelupisteessä. Tapahtumat lisäävät
	 * a-olion seuraavan palvelupisteen jonoon jonon pituuden mukaan vertailuoperaattoria käyttäen. a-oliolle määritetään uuden palvelupisteen
	 * aloitusaika sen mukaan mihin palvelupistejonoon a-olio päätyi. Muuttuja pp1a tai pp1b lisääntyy aina yhden asiakkaan kuljettua tapahtuman
	 * läpi. Tapahtumat DEP2A-DEP3B noudattavat samoja käytäntöjä, poistavat a-olion jonosta, lisäävät sen seuraavaan jonoon jonon pituuden
	 * mukaan ja lisäävät muuttujien arvoja sekä asettavat a-oliolle kellonaikoja. Jokainen tapahtuma myös lähettää visualisointi komennon
	 * KontrollerIf metodin avulla jolloin käyttäjä näkee käyttöliittymästä simuloinnin ja asiakkaiden kulkua. DEP4 ottaa a-olion jonosta, 
	 * asettaa tälle palvelupisteen lopetusajan sekä simuloinnin lopetusajan olion kohdalla. Tapahtuma myös kutsuu a-olion metodeja joilla
	 * saadaan konsoliin tulostettua olion käyttämiä aikoja. 
	 * @param t on Tapahtuma-luokan olio jolla määritellään metodissa suoritettavien tapausten kulku.
	 */
	void suoritaTapahtuma(Tapahtuma t){  // Keskitetty algoritmi tapahtumien käsittelyyn ja asiakkaiden siirtymisten hallintaan
		
		Asiakas a;
		
		switch (t.getTyyppi()){
			
		case ARR1:
					kontrolleri.visualisoiAsiakas();
					asiakkaat++;
					
					int arpa =  (int) (Math.random()*2);
					if (arpa==1) {
							kontrolleri.visualisoiekappA();
							palvelupisteet[0].lisaaJonoon(new Asiakas());
							Trace.out(Trace.Level.INFO, "1a: "+palvelupisteet[0].jononPituus());
							jono1=palvelupisteet[0].jononPituus();
					} else {
							kontrolleri.visualisoiekappB();
							palvelupisteet[1].lisaaJonoon(new Asiakas());
							Trace.out(Trace.Level.INFO, "1b: "+palvelupisteet[1].jononPituus());
							jono2=palvelupisteet[1].jononPituus();
				 	}				
										
					saapumisprosessi.generoiSeuraava();	

			       	break;
	
		case DEP1A: a = palvelupisteet[0].otaJonosta();
					pp1a++;
			        a.setLopetusaika(kello.getAika());			        
			        Trace.out(Trace.Level.INFO, "DEP 1A Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
			       	aika1a+=a.ppPalveluaika();
			       	a.palveluaika();
			       	a.setAloitusaika(kello.getAika());
					if (palvelupisteet[2].jononPituus()<palvelupisteet[3].jononPituus()|palvelupisteet[2].jononPituus()<palvelupisteet[4].jononPituus()|palvelupisteet[2].jononPituus()<palvelupisteet[5].jononPituus()) {
						kontrolleri.visualisoitokappA();
						palvelupisteet[2].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2a: "+palvelupisteet[2].jononPituus());
						jono3=palvelupisteet[2].jononPituus();
					} else if (palvelupisteet[3].jononPituus()<palvelupisteet[2].jononPituus()|palvelupisteet[3].jononPituus()<palvelupisteet[4].jononPituus()|palvelupisteet[3].jononPituus()<palvelupisteet[5].jononPituus()) {
						kontrolleri.visualisoitokappB();
						palvelupisteet[3].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2b: "+palvelupisteet[3].jononPituus());
						jono4=palvelupisteet[3].jononPituus();
					} else if (palvelupisteet[4].jononPituus()<palvelupisteet[2].jononPituus()|palvelupisteet[4].jononPituus()<palvelupisteet[3].jononPituus()|palvelupisteet[4].jononPituus()<palvelupisteet[5].jononPituus()) {
						kontrolleri.visualisoitokappC();
						palvelupisteet[4].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2c:"+palvelupisteet[4].jononPituus());
						jono5=palvelupisteet[4].jononPituus();
					} else {
						kontrolleri.visualisoitokappD();
						palvelupisteet[5].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2d: "+palvelupisteet[5].jononPituus());
						jono6=palvelupisteet[5].jononPituus();
					}
			   	    
			   	   break;
			   	   
		case DEP1B: a = palvelupisteet[1].otaJonosta();
					pp1b++;
			        a.setLopetusaika(kello.getAika());
			        Trace.out(Trace.Level.INFO, "DEP 1B Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
			       	aika1b+=a.ppPalveluaika();
			       	a.palveluaika();
			       	a.setAloitusaika(kello.getAika());
					if (palvelupisteet[2].jononPituus()<palvelupisteet[3].jononPituus()|palvelupisteet[2].jononPituus()<palvelupisteet[4].jononPituus()|palvelupisteet[2].jononPituus()<palvelupisteet[5].jononPituus()) {
						kontrolleri.visualisoitokappA();
						palvelupisteet[2].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2a: "+palvelupisteet[2].jononPituus());
						jono3=palvelupisteet[2].jononPituus();
					} else if (palvelupisteet[3].jononPituus()<palvelupisteet[2].jononPituus()|palvelupisteet[3].jononPituus()<palvelupisteet[4].jononPituus()|palvelupisteet[3].jononPituus()<palvelupisteet[5].jononPituus()) {
						kontrolleri.visualisoitokappB();
						palvelupisteet[3].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2b: "+palvelupisteet[3].jononPituus());
						jono4=palvelupisteet[3].jononPituus();
					} else if (palvelupisteet[4].jononPituus()<palvelupisteet[2].jononPituus()|palvelupisteet[4].jononPituus()<palvelupisteet[3].jononPituus()|palvelupisteet[4].jononPituus()<palvelupisteet[5].jononPituus()) {
						kontrolleri.visualisoitokappC();
						palvelupisteet[4].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2c:"+palvelupisteet[4].jononPituus());
						jono5=palvelupisteet[4].jononPituus();
					} else {
						kontrolleri.visualisoitokappD();
						palvelupisteet[5].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "2d: "+palvelupisteet[5].jononPituus());
						jono6=palvelupisteet[5].jononPituus();
					}
				   	   break;
				   	   
		case DEP2A: 
			       	a = palvelupisteet[2].otaJonosta();
			       	pp2a++;
			        a.setLopetusaika(kello.getAika());
			        Trace.out(Trace.Level.INFO, "DEP 2A Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
			       	aika2a+=a.ppPalveluaika();
			       	a.palveluaika();
			       	a.setAloitusaika(kello.getAika());
					if (palvelupisteet[6].jononPituus()<palvelupisteet[7].jononPituus()) {
						kontrolleri.visualisoikolmasppA();
						palvelupisteet[6].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3a: "+palvelupisteet[6].jononPituus());
						jono7=palvelupisteet[6].jononPituus();
					} else {
						kontrolleri.visualisoikolmasppB();
						palvelupisteet[7].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3b: "+palvelupisteet[7].jononPituus());
						jono8=palvelupisteet[7].jononPituus();
					}	
					break;
			       
		case DEP2B: 
			       	a = palvelupisteet[3].otaJonosta();
			       	pp2b++;
			        a.setLopetusaika(kello.getAika());
			        Trace.out(Trace.Level.INFO, "DEP 2B Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
			       	aika2b+=a.ppPalveluaika();
			       	a.palveluaika();
			       	a.setAloitusaika(kello.getAika());
					if (palvelupisteet[6].jononPituus()<palvelupisteet[7].jononPituus()) {
						kontrolleri.visualisoikolmasppA();
						palvelupisteet[6].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3a: "+palvelupisteet[6].jononPituus());
						jono7=palvelupisteet[6].jononPituus();
					} else {
						kontrolleri.visualisoikolmasppB();
						palvelupisteet[7].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3b: "+palvelupisteet[7].jononPituus());
						jono8=palvelupisteet[7].jononPituus();
					}	
					break;
			       
		case DEP2C: 
			       	a = palvelupisteet[4].otaJonosta();
			       	pp2c++;
			        a.setLopetusaika(kello.getAika());
			        Trace.out(Trace.Level.INFO, "DEP 2C Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
			       	aika2c+=a.ppPalveluaika();
			       	a.palveluaika();
			       	a.setAloitusaika(kello.getAika());
					if (palvelupisteet[6].jononPituus()<palvelupisteet[7].jononPituus()) {
						kontrolleri.visualisoikolmasppA();
						palvelupisteet[6].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3a: "+palvelupisteet[6].jononPituus());
						jono7=palvelupisteet[6].jononPituus();
					} else {
						kontrolleri.visualisoikolmasppB();
						palvelupisteet[7].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3b: "+palvelupisteet[7].jononPituus());
						jono8=palvelupisteet[7].jononPituus();
					}			
					break;
		       
		case DEP2D: 
			       	a = palvelupisteet[5].otaJonosta();
			       	pp2d++;
			        a.setLopetusaika(kello.getAika());
			        Trace.out(Trace.Level.INFO, "DEP 2D Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
			       	aika2d+=a.ppPalveluaika();
			       	a.palveluaika();
			       	a.setAloitusaika(kello.getAika());
					if (palvelupisteet[6].jononPituus()<palvelupisteet[7].jononPituus()) {
						kontrolleri.visualisoikolmasppA();
						palvelupisteet[6].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3a: "+palvelupisteet[6].jononPituus());
						jono7=palvelupisteet[6].jononPituus();
					} else {
						kontrolleri.visualisoikolmasppB();
						palvelupisteet[7].lisaaJonoon(a);
						Trace.out(Trace.Level.INFO, "3b: "+palvelupisteet[7].jononPituus());
						jono8=palvelupisteet[7].jononPituus();
					}		
					break;
					
		case DEP3A:
					a = palvelupisteet[6].otaJonosta();
					pp3a++;
				    a.setLopetusaika(kello.getAika());
				    Trace.out(Trace.Level.INFO, "DEP 3A Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
				    aika3a+=a.ppPalveluaika();
					a.palveluaika();
				    a.setAloitusaika(kello.getAika());
					kontrolleri.visualisoineljaspp();
					palvelupisteet[8].lisaaJonoon(a);
					Trace.out(Trace.Level.INFO, "4: "+palvelupisteet[8].jononPituus());
				    break;			

		case DEP3B:
					a = palvelupisteet[7].otaJonosta();
					pp3b++;
				    a.setLopetusaika(kello.getAika());
				    Trace.out(Trace.Level.INFO, "DEP 3B Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
				   	aika3b+=a.ppPalveluaika();
					a.palveluaika();
				    a.setAloitusaika(kello.getAika());		
					palvelupisteet[8].lisaaJonoon(a);
					Trace.out(Trace.Level.INFO, "4: "+palvelupisteet[8].jononPituus());
				   	break;
				   	
		case DEP4:
					a = palvelupisteet[8].otaJonosta();
					kontrolleri.visualisoineljaspp();
					pp4++;
					a.setLopetusaika(kello.getAika());
					a.setPoistumisaika(kello.getAika());
					Trace.out(Trace.Level.INFO, "DEP 4 Jonosta poistuminen ja jonon palveluaika: "+a.ppPalveluaika());
					aika4+=a.ppPalveluaika();					
					a.palveluaika();
					Trace.out(Trace.Level.INFO, "DEP 4 Palvelusta poistuminen");
		        	a.raportti();
		}	
	}

	/**
	 * Metodilla uusiTapahtuma luodaan Tapahtumalista-luokkaan uusi tapahtuma.
	 * @param t on Tapahtuma-luokan olio joka lisätään tapahtumalistaan.
	 */
	void uusiTapahtuma(Tapahtuma t){
		tapahtumalista.lisaa(t);
	}

	/**
	 * Metodi nykyaika palauttaa Tapahtumalista-luokan seuraavan tapahtuman ajankohdan.
	 * @return palauttaa tapahtumalista.getSeuraavaAika-metodilla seuraavan tapahtuman ajankohdan.
	 */
	private double nykyaika(){
		return tapahtumalista.getSeuraavanAika();
	}
	
	/**
	 * Metodi simuloidaan on simuloinnnin aikana jatkuvasti kutsuttava jonka avulla pystytää palauttamaan juoksevia lukuja. Metodin avulla myös
	 * kutsutaan KontrolleriIf-luokan metodeja joiden avulla saadaan käyttäjälle käyttöliittymään tulostettua juoksevia lukuja, jotka kertovat
	 * jonojen reaaliaikaisia pituuksia sekä saapuvia, että poistuvia asiakasmääriä.
	 * @return palauttaa sen hetken kellonaikaa, joka simulaattorissa on meneillään.
	 */
	private boolean simuloidaan(){
		Trace.out(Trace.Level.INFO, "Kello on: " + kello.getAika());
		kontrolleri.naytaLoppuaika(kello.getAika());
		kontrolleri.naytaJonopituus(jono1, jono2, jono3, jono4, jono5, jono6, jono7, jono8, pp4, asiakkaat);
		kontrolleri.naytaKanta(dao.getVasen(), dao.getOikea());
		return kello.getAika() < simulointiaika;
	}
	
	/**
	 * Metodi viive on simuloinnin kulkua kontrolloiva arvo jonka käyttäjä on syöttänyt ennen simuloinnnin alkua.
	 */
	private void viive() {
		Trace.out(Trace.Level.INFO,"Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodi tulokset on simuloinnin loputtua kutsuttava metodi, joka palauttaa simuloinnnin tuloksia. Tämä metdoi palauttaa kaikki simuloinnissa
	 * tulleet tulokset ja metodissa lasketaan niin asiakasmäärät kuin kaikki suureet. Metodi myös välittää KontrollerIf-luokan metodien avulla 
	 * simuloinnin tulokset käyttöliittymään, sekä simuloinnin kokonaisajan. Metodissa myös hoidetaan tulosten tallentaminen ulkoiseen tekstitiedostoon.
	 */
	private void tulokset(){
		double 	utilization1a=(palvelupisteet[0].getBusy()*100)/kello.getAika(),
				utilization1b=(palvelupisteet[1].getBusy()*100)/kello.getAika(),
				utilization2a=(palvelupisteet[2].getBusy()*100)/kello.getAika(),
				utilization2b=(palvelupisteet[3].getBusy()*100)/kello.getAika(), 
				utilization2c=(palvelupisteet[4].getBusy()*100)/kello.getAika(), 
				utilization2d=(palvelupisteet[5].getBusy()*100)/kello.getAika(),
				utilization3a=(palvelupisteet[6].getBusy()*100)/kello.getAika(),
				utilization3b=(palvelupisteet[7].getBusy()*100)/kello.getAika(),
				utilization4=(palvelupisteet[8].getBusy()*100)/kello.getAika();
		
		double 	troughput1a=(pp1a*100)/kello.getAika(),
				troughput1b=(pp1b*100)/kello.getAika(),
				troughput2a=(pp2a*100)/kello.getAika(),
				troughput2b=(pp2b*100)/kello.getAika(),
				troughput2c=(pp2c*100)/kello.getAika(),
				troughput2d=(pp2d*100)/kello.getAika(),
				troughput3a=(pp3a*100)/kello.getAika(),
				troughput3b=(pp3b*100)/kello.getAika(),
				troughput4=(pp4*100)/kello.getAika();
		
		double  service1a=palvelupisteet[0].getBusy()/pp1a,
				service1b=palvelupisteet[1].getBusy()/pp1b,
				service2a=palvelupisteet[2].getBusy()/pp2a,
				service2b=palvelupisteet[3].getBusy()/pp2b,
				service2c=palvelupisteet[4].getBusy()/pp2c,
				service2d=palvelupisteet[5].getBusy()/pp2d,
				service3a=palvelupisteet[6].getBusy()/pp3a,
				service3b=palvelupisteet[7].getBusy()/pp3b,
				service4=palvelupisteet[8].getBusy()/pp4;
		
		double 	responsetime1a=aika1a/pp1a,
				responsetime1b=aika1b/pp1b,
				responsetime2a=aika2a/pp2a,
				responsetime2b=aika2b/pp2b,
				responsetime2c=aika2c/pp2c,
				responsetime2d=aika2d/pp2d,
				responsetime3a=aika3a/pp3a,
				responsetime3b=aika3b/pp3b,
				responsetime4=aika4/pp4;
		
		double 	jonopituus1a=aika1a/kello.getAika(),
				jonopituus1b=aika1b/kello.getAika(),
				jonopituus2a=aika2a/kello.getAika(),
				jonopituus2b=aika2b/kello.getAika(),
				jonopituus2c=aika2c/kello.getAika(),
				jonopituus2d=aika2d/kello.getAika(),
				jonopituus3a=aika3a/kello.getAika(),
				jonopituus3b=aika3b/kello.getAika(),
				jonopituus4=aika4/kello.getAika();
		
		kontrolleri.naytaTulokset(out);
		kontrolleri.naytaLoppuaika(kello.getAika());
		
		 try {
		        FileWriter myWriter = new FileWriter("data\\"+out);
		       
		        myWriter.write("Tiedosto: "+out);
		        	        
		        myWriter.write("\nSimuloinnin syötteet:"
		        		+"\nSaapuvat asiakkaat Negexp 5, 2"
						+"\nKäsienpesu A Normal, korkeus: "+syote1+" leveys: "+syote2
						+"\nKäsienpesu B Normal, korkeus: "+syote1+" leveys: "+syote2
						+"\nRuokalinja A Normal, korkeus: "+syote3+" leveys: "+syote4
						+"\nRuokalinja B Normal, korkeus: "+syote3+" leveys: "+syote4
						+"\nRuokalinja C Normal, korkeus: "+syote3+" leveys: "+syote4
						+"\nRuokalinja D Normal, korkeus: "+syote3+" leveys: "+syote4
						+"\nAstianpalautus A Normal, korkeus: "+syote5+" leveys: "+syote6
						+"\nAstianpalautus B Normal, korkeus: "+syote5+" leveys: "+syote6
						+"\nPoistuminen Negexp, "+dao.getVasen()+", "+dao.getOikea());
		        
		        myWriter.write("\n\nSimulointi päättyi kello:" + formatter.format(kello.getAika()));
		        
		        myWriter.write("\n\nTulokset:\n"
		        		+"\nSaapuneet asiakkaat (A): "+asiakkaat
						+"\nAsiakasmäärät (C): "
						+"\nKäsienpesu A: "+pp1a
						+"\nKäsienpesu B: "+pp1b
						+"\nRuokalinja A: "+pp2a
						+"\nRuokalinja B: "+pp2b
						+"\nRuokalinja C: "+pp2c
						+"\nRuokalinja D: "+pp2d
						+"\nAstianpalautus A: "+pp3a
						+"\nAstianpalautus B: "+pp3b
						+"\nPoistuminen: "+pp4);
		        
		        myWriter.write("\n\nAktiiviajat (B):"
						+ "\nKäsienpesu A: "+formatter.format(palvelupisteet[0].getBusy())
						+ "\nKäsienpesu B: "+formatter.format(palvelupisteet[1].getBusy())
						+ "\nRuokalinja A: "+formatter.format(palvelupisteet[2].getBusy())
						+ "\nRuokalinja B: "+formatter.format(palvelupisteet[3].getBusy())
						+ "\nRuokalinja C: "+formatter.format(palvelupisteet[4].getBusy())
						+ "\nRuokalinja D: "+formatter.format(palvelupisteet[5].getBusy())
						+ "\nAstianpalautus A: "+formatter.format(palvelupisteet[6].getBusy())
						+ "\nAstianpalautus B: "+formatter.format(palvelupisteet[7].getBusy())
						+ "\nPoistuminen: "+formatter.format(palvelupisteet[8].getBusy()));
		    	
		        myWriter.write("\n\nPalvelupisteiden käyttöasteet (U):"
						+ "\nKäsienpesu A: "+formatter.format(utilization1a)+"%"
						+ "\nKäsienpesu B: "+formatter.format(utilization1b)+"%"
						+ "\nRuokalinja A: "+formatter.format(utilization2a)+"%"
						+ "\nRuokalinja B: "+formatter.format(utilization2b)+"%"
						+ "\nRuokalinja C: "+formatter.format(utilization2c)+"%"
						+ "\nRuokalinja D: "+formatter.format(utilization2d)+"%"
						+ "\nAstianpalautus A: "+formatter.format(utilization3a)+"%"
						+ "\nAstianpalautus B: "+formatter.format(utilization3b)+"%"
						+ "\nPoistuminen: "+formatter.format(utilization4)+"%");
		        
		        myWriter.write("\n\nSuoritusteho (X):"
						+ "\nKäsienpesu A: "+formatter.format(troughput1a)
						+ "\nKäsienpesu B: "+formatter.format(troughput1b)
						+ "\nRuokalinja A: "+formatter.format(troughput2a)
						+ "\nRuokalinja B: "+formatter.format(troughput2b)
						+ "\nRuokalinja C: "+formatter.format(troughput2c)
						+ "\nRuokalinja D: "+formatter.format(troughput2d)
						+ "\nAstianpalautus A: "+formatter.format(troughput3a)
						+ "\nAstianpalautus B: "+formatter.format(troughput3b)
						+ "\nPoistuminen: "+formatter.format(troughput4));
		        
		        myWriter.write("\n\nKeskimääräinen palveluaika (S):"
						+ "\nKäsienpesu A: "+formatter.format(service1a)
						+ "\nKäsienpesu B: "+formatter.format(service1b)
						+ "\nRuokalinja A: "+formatter.format(service2a)
						+ "\nRuokalinja B: "+formatter.format(service2b)
						+ "\nRuokalinja C: "+formatter.format(service2c)
						+ "\nRuokalinja D: "+formatter.format(service2d)
						+ "\nAstianpalautus A: "+formatter.format(service3a)
						+ "\nAstianpalautus B: "+formatter.format(service3b)
						+ "\nPoistuminen: "+formatter.format(service4));
		        
		        myWriter.write("\n\nKeskimääräinen läpimenoaika (R)"
						+ "\nKäsienpesu A: "+formatter.format(responsetime1a)
						+ "\nKäsienpesu B: "+formatter.format(responsetime1b)
						+ "\nRuokalinja A: "+formatter.format(responsetime2a)
						+ "\nRuokalinja B: "+formatter.format(responsetime2b)
						+ "\nRuokalinja C: "+formatter.format(responsetime2c)
						+ "\nRuokalinja D: "+formatter.format(responsetime2d)
						+ "\nAstianpalautus A: "+formatter.format(responsetime3a)
						+ "\nAstianpalautus B: "+formatter.format(responsetime3b)
						+ "\nPoistuminen: "+formatter.format(responsetime4));
		        
		        myWriter.write("\n\nPalvelupisteiden keskimääräinen jonopituus (N):"
						+ "\nKäsienpesu A: "+formatter.format(jonopituus1a)
						+ "\nKäsienpesu B: "+formatter.format(jonopituus1b)
						+ "\nRuokalinja A: "+formatter.format(jonopituus2a)
						+ "\nRuokalinja B: "+formatter.format(jonopituus2b)
						+ "\nRuokalinja C: "+formatter.format(jonopituus2c)
						+ "\nRuokalinja D: "+formatter.format(jonopituus2d)
						+ "\nAstianpalautus A: "+formatter.format(jonopituus3a)
						+ "\nAstianpalautus B: "+formatter.format(jonopituus3b)
						+ "\nPoistuminen: "+formatter.format(jonopituus4));	        
		        
		        myWriter.write("\n\nLäpimenoajat (W): "
						+"\nKäsienpesu A: "+formatter.format(aika1a)
						+"\nKäsienpesu B: "+formatter.format(aika1b)
						+"\nRuokalinja A: "+formatter.format(aika2a)
						+"\nRuokalinja B: "+formatter.format(aika2b)
						+"\nRuokalinja C: "+formatter.format(aika2c)
						+"\nRuokalinja D: "+formatter.format(aika2d)
						+"\nAstianpalautus A: "+formatter.format(aika3a)
						+"\nAstianpalautus B: "+formatter.format(aika3b)
						+"\nPoistuminen: "+formatter.format(aika4));
		        
		        myWriter.close();
		        Trace.out(Trace.Level.INFO,"Successfully wrote to the file.");
		      } catch (IOException e) {
		    	  Trace.out(Trace.Level.ERR,"An error occurred.");
		        e.printStackTrace();
		      }

		 Trace.out(Trace.Level.INFO, "Tiedosto: "+out);
        
		 Trace.out(Trace.Level.INFO, "\nSimuloinnin syötteet:"
	        		+"\nSaapuvien asiakkaiden korkeus ja leveystiedot "
	        		+"\narvotaan jokaisen uuden saapumisprosessin yhteydessä"
	        		+"\nSaapuvat asiakkaat Negexp, korkeus: "+dao.getVasen()+" leveys: "+dao.getOikea()
					+"\nKäsienpesu A Normal, korkeus: "+syote1+" leveys: "+syote2
					+"\nKäsienpesu B Normal, korkeus: "+syote1+" leveys: "+syote2
					+"\nRuokalinja A Normal, korkeus: "+syote3+" leveys: "+syote4
					+"\nRuokalinja B Normal, korkeus: "+syote3+" leveys: "+syote4
					+"\nRuokalinja C Normal, korkeus: "+syote3+" leveys: "+syote4
					+"\nRuokalinja D Normal, korkeus: "+syote3+" leveys: "+syote4
					+"\nAstianpalautus A Normal, korkeus: "+syote5+" leveys: "+syote6
					+"\nAstianpalautus B Normal, korkeus: "+syote5+" leveys: "+syote6
					+"\nPoistuminen Negexp, korkeus: 5 , leveys: 2");
        
		 Trace.out(Trace.Level.INFO, "\n\nSimulointi päättyi kello:" + formatter.format(kello.getAika()));
        
		 Trace.out(Trace.Level.INFO, "\n\nTulokset:\n"
	        		+"\nSaapuneet asiakkaat (A): "+asiakkaat
					+"\nAsiakasmäärät (C): "
					+"\nKäsienpesu A: "+pp1a
					+"\nKäsienpesu B: "+pp1b
					+"\nRuokalinja A: "+pp2a
					+"\nRuokalinja B: "+pp2b
					+"\nRuokalinja C: "+pp2c
					+"\nRuokalinja D: "+pp2d
					+"\nAstianpalautus A: "+pp3a
					+"\nAstianpalautus B: "+pp3b
					+"\nPoistuminen: "+pp4);
        
		 Trace.out(Trace.Level.INFO, "\n\nAktiiviajat (B):"
					+ "\nKäsienpesu A: "+formatter.format(palvelupisteet[0].getBusy())
					+ "\nKäsienpesu B: "+formatter.format(palvelupisteet[1].getBusy())
					+ "\nRuokalinja A: "+formatter.format(palvelupisteet[2].getBusy())
					+ "\nRuokalinja B: "+formatter.format(palvelupisteet[3].getBusy())
					+ "\nRuokalinja C: "+formatter.format(palvelupisteet[4].getBusy())
					+ "\nRuokalinja D: "+formatter.format(palvelupisteet[5].getBusy())
					+ "\nAstianpalautus A: "+formatter.format(palvelupisteet[6].getBusy())
					+ "\nAstianpalautus B: "+formatter.format(palvelupisteet[7].getBusy())
					+ "\nPoistuminen: "+formatter.format(palvelupisteet[8].getBusy()));
    	
		 Trace.out(Trace.Level.INFO, "\n\nPalvelupisteiden käyttöasteet (U):"
					+ "\nKäsienpesu A: "+formatter.format(utilization1a)+"%"
					+ "\nKäsienpesu B: "+formatter.format(utilization1b)+"%"
					+ "\nRuokalinja A: "+formatter.format(utilization2a)+"%"
					+ "\nRuokalinja B: "+formatter.format(utilization2b)+"%"
					+ "\nRuokalinja C: "+formatter.format(utilization2c)+"%"
					+ "\nRuokalinja D: "+formatter.format(utilization2d)+"%"
					+ "\nAstianpalautus A: "+formatter.format(utilization3a)+"%"
					+ "\nAstianpalautus B: "+formatter.format(utilization3b)+"%"
					+ "\nPoistuminen: "+formatter.format(utilization4)+"%");
        
		 Trace.out(Trace.Level.INFO, "\n\nSuoritusteho (X):"
					+ "\nKäsienpesu A: "+formatter.format(troughput1a)
					+ "\nKäsienpesu B: "+formatter.format(troughput1b)
					+ "\nRuokalinja A: "+formatter.format(troughput2a)
					+ "\nRuokalinja B: "+formatter.format(troughput2b)
					+ "\nRuokalinja C: "+formatter.format(troughput2c)
					+ "\nRuokalinja D: "+formatter.format(troughput2d)
					+ "\nAstianpalautus A: "+formatter.format(troughput3a)
					+ "\nAstianpalautus B: "+formatter.format(troughput3b)
					+ "\nPoistuminen: "+formatter.format(troughput4));
        
		 Trace.out(Trace.Level.INFO, "\n\nKeskimääräinen palveluaika (S):"
					+ "\nKäsienpesu A: "+formatter.format(service1a)
					+ "\nKäsienpesu B: "+formatter.format(service1b)
					+ "\nRuokalinja A: "+formatter.format(service2a)
					+ "\nRuokalinja B: "+formatter.format(service2b)
					+ "\nRuokalinja C: "+formatter.format(service2c)
					+ "\nRuokalinja D: "+formatter.format(service2d)
					+ "\nAstianpalautus A: "+formatter.format(service3a)
					+ "\nAstianpalautus B: "+formatter.format(service3b)
					+ "\nPoistuminen: "+formatter.format(service4));
        
		 Trace.out(Trace.Level.INFO, "\n\nKeskimääräinen läpimenoaika (R)"
					+ "\nKäsienpesu A: "+formatter.format(responsetime1a)
					+ "\nKäsienpesu B: "+formatter.format(responsetime1b)
					+ "\nRuokalinja A: "+formatter.format(responsetime2a)
					+ "\nRuokalinja B: "+formatter.format(responsetime2b)
					+ "\nRuokalinja C: "+formatter.format(responsetime2c)
					+ "\nRuokalinja D: "+formatter.format(responsetime2d)
					+ "\nAstianpalautus A: "+formatter.format(responsetime3a)
					+ "\nAstianpalautus B: "+formatter.format(responsetime3b)
					+ "\nPoistuminen: "+formatter.format(responsetime4));
        
		 Trace.out(Trace.Level.INFO, "\n\nPalvelupisteiden keskimääräinen jonopituus (N):"
					+ "\nKäsienpesu A: "+formatter.format(jonopituus1a)
					+ "\nKäsienpesu B: "+formatter.format(jonopituus1b)
					+ "\nRuokalinja A: "+formatter.format(jonopituus2a)
					+ "\nRuokalinja B: "+formatter.format(jonopituus2b)
					+ "\nRuokalinja C: "+formatter.format(jonopituus2c)
					+ "\nRuokalinja D: "+formatter.format(jonopituus2d)
					+ "\nAstianpalautus A: "+formatter.format(jonopituus3a)
					+ "\nAstianpalautus B: "+formatter.format(jonopituus3b)
					+ "\nPoistuminen: "+formatter.format(jonopituus4));	        
        
		 Trace.out(Trace.Level.INFO, "\n\nLäpimenoajat (W): "
					+"\nKäsienpesu A: "+formatter.format(aika1a)
					+"\nKäsienpesu B: "+formatter.format(aika1b)
					+"\nRuokalinja A: "+formatter.format(aika2a)
					+"\nRuokalinja B: "+formatter.format(aika2b)
					+"\nRuokalinja C: "+formatter.format(aika2c)
					+"\nRuokalinja D: "+formatter.format(aika2d)
					+"\nAstianpalautus A: "+formatter.format(aika3a)
					+"\nAstianpalautus B: "+formatter.format(aika3b)
					+"\nPoistuminen: "+formatter.format(aika4));
		
	}

}