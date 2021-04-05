package controller;

import javafx.application.Platform;
import simu.Moottori;
import simu.MoottoriIf;
import view.*;
/**
 * Kontrolleri-luokka hoitaa kommunikoinnin view-pakkauksen ja simu-pakkauksen välillä. Luokkaa voi kutsua molemmista paketeista ja
 * se välittää tietoa, kuten metodeita ja muuttujien arvoja pakkauksista toiseen.
 * 
 * @author Joonas Soininen
 *
 * @version 1.3 
 */

public class Kontrolleri implements KontrolleriIf {

	/**
	 * Olio moottori on luokan MoottoriIf olio, jolla kyseisen luokan metodeja
	 */
	private MoottoriIf moottori;
	/**
	 * Olio gui on luokan GuiIF olio, jolla suoritataan kyseisen luokan metodeja
	 */
	private GuiIf gui;
	
	/**
	 * Kontrolleri kontruktorilla asetetaan gui-olion arvo/arvot
	 * @param gui saa parametrit metodin kutsun yhteydessä.
	 */
	public Kontrolleri(GuiIf gui) {
		this.gui = gui;
	}	

	/**
	 * Metodilla kaynnistaSimulointi aloitetaan simulattorin toiminta aluksi luomalla uusi moottorisäie ja
	 * kutsumalla moottori-säikeen metodea asetetaan simuloinnille toiminta-aikaa sekä viivettä.
	 * Metodissa myös kutsutaan gui-olion metodia jolla tyhjennetään visualisoinnin näyttö
	 * 	
	 */
	@Override
	public void kaynnistaSimulointi() {
		moottori = new Moottori(this);
		moottori.setSimulointiaika(gui.getAika());
		moottori.setViive(gui.getViive());
		gui.getVisualisointi().tyhjennaNaytto();
		((Thread)moottori).start();
	}
	
	/**
	 * Metodilla hidasta hidastetaan simuloinnin nopeutta kutsumalla moottori-säikeen metodia ja antamalla sille uusi arvo.
	 */
	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*1.10));
	}

	/**
	 * Metodilla nopeuta nopeutetaan simuloinnin nopeatta kutsumalla moottori-säikeen metodia ja antamalla sille uusi arvo.
	 */
	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long)(moottori.getViive()*0.9));
	}
	
	/**
	 * Metodilla syote1 haetaan käyttäjän syöttämä arvo käyttöliittymästä ja se palautetaan tätä metodia toisesta luokasta kutsuttaessa.
	 */
	@Override
	public int syote1() {
		return gui.getSyote1();	
	}
	
	/**
	 * Metodilla syote2 haetaan käyttäjän syöttämä arvo käyttöliittymästä ja se palautetaan tätä metodia toisesta luokasta kutsuttaessa.
	 * @return palauttaa käyttäjän syöttämän numeerisen arvon.
	 */
	@Override
	public int syote2() {
		return gui.getSyote2();		
	}
	
	/**
	 * Metodilla syote3 haetaan käyttäjän syöttämä arvo käyttöliittymästä ja se palautetaan tätä metodia toisesta luokasta kutsuttaessa.
	 * @return palauttaa käyttäjän syöttämän numeerisen arvon.
	 */
	@Override
	public int syote3() {
		return gui.getSyote3();	
	}

	/**
	 * Metodilla syote4 haetaan käyttäjän syöttämä arvo käyttöliittymästä ja se palautetaan tätä metodia toisesta luokasta kutsuttaessa.
	 * @return palauttaa käyttäjän syöttämän numeerisen arvon.
	 */
	@Override
	public int syote4() {
		return gui.getSyote4();	
	}
	
	/**
	 * Metodilla syote5 haetaan käyttäjän syöttämä arvo käyttöliittymästä ja se palautetaan tätä metodia toisesta luokasta kutsuttaessa.
	 * @return palauttaa käyttäjän syöttämän numeerisen arvon.
	 */
	@Override
	public int syote5() {
		return gui.getSyote5();
	}

	/**
	 * Metodilla syote6 haetaan käyttäjän syöttämä arvo käyttöliittymästä ja se palautetaan tätä metodia toisesta luokasta kutsuttaessa.
	 * @return palauttaa käyttäjän syöttämän numeerisen arvon.
	 */
	@Override
	public int syote6() {
		return gui.getSyote6();
	}

	/**
	 * Metodilla naytaJonopituus tuodaan Moottori-luokasta palvelupisteiden jonojen pituudet sekä asiakasmäärä jotka välitetään eteenpäin
	 * kutsumalla GuiIf-luokan metodia setJonopituus.
	 */
	@Override
	public void naytaJonopituus(int jono1, int jono2, int jono3, int jono4, int jono5, int jono6, int jono7,
			int jono8, int pp4, int asiakkaat) {
		gui.setJonopituus(jono1, jono2, jono3, jono4, jono5, jono6, jono7, jono8, pp4, asiakkaat);
		
	}
	
	/**
	 * Metodilla naytaKanta tuodaan Moottori-luokasta tieokannasta haetut arvot jotka välitetään eteenpäin kutsumalla GuiIf-luokan metodia setKanta.
	 */
	@Override
	public void naytaKanta(int vasen, int oikea) {
		gui.setKanta(vasen, oikea);
		
	}
	
	/**
	 * Metodilla naytaTulokset tuodaan Moottori-luokasta tiedostonimi, joka välitetään eteenpäin kutsumalla GuiIf-luokan metodia setTulokset kutsumalla.
	 */
	@Override
	public void naytaTulokset(String out) {
		Platform.runLater(()->gui.setTulokset(out)); 
		
	}
	
	/**
	 * Metodilla naytaLoppuaika tuodaan Moottori-luokasta simuloinnin loppuaika, joka välitetään eteenpäin GuiIg-luokan media setLoppuaika kutsumalla
	 */
	@Override
	public void naytaLoppuaika(double aika) {
		Platform.runLater(()->gui.setLoppuaika(aika)); 
	}

	/**
	 * Metodilla visalisoiAsiakas välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoiAsiakas() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().uusiAsiakas();
			}
		});
	}
	
	/**
	 * Metodilla visalisoiekappA välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoiekappA() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().ekaPpA();;
			}
		});
	}
	
	/**
	 * Metodilla visalisoiekappB välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoiekappB() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().ekaPpB();;
			}
		});
	}

	/**
	 * Metodilla visalisoitokappA välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoitokappA() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().tokaPpA();
			}
		});		
	}

	/**
	 * Metodilla visalisoitokappB välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoitokappB() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().tokaPpB();
			}
		});		
	}
	
	/**
	 * Metodilla visalisoitokappC välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoitokappC() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().tokaPpC();
			}
		});		
	}

	/**
	 * Metodilla visalisoitokappD välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoitokappD() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().tokaPpD();
			}
		});		
	}

	/**
	 * Metodilla visalisoikolmasppA välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoikolmasppA() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().kolmasPpA();;
			}
		});		
	}

	/**
	 * Metodilla visalisoikolmasppB välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoikolmasppB() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().kolmasPpB();;
			}
		});		
	}

	/**
	 * Metodilla visalisoineljaspp välitetään käsky GuiIf-luokan metodille getVisualosointi joka piirtää käyttöliittymään uuden pallon.
	 */	
	@Override
	public void visualisoineljaspp() {
		Platform.runLater(new Runnable(){
			public void run(){
				gui.getVisualisointi().neljasPp();;;
			}
		});	
	}

}
