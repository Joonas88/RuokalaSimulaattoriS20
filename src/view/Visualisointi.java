package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
/**
 * Luokka pitää huolen, että näytölle piirtyy oikeita asioita oikeaan aikaan. Luokan tarkoitsu on hoitaa visuaalinen puoli.
 * 
 * @author Joonas Soininen
 * @version 1.4
 */
public class Visualisointi extends Canvas{

	/**
	 * Olio gc on vastuussa kuvioiden piirtämisestä näkyville.
	 */
	private GraphicsContext gc;
	/**
	 * Muuttuja i vastaa y-akselia
	 */
	double i = 20;
	/**
	 * Muuttuja j vastaa x-akselia
	 */
	double j = 20;

	/**
	 * Tyhjä konstruktori
	 */
	public Visualisointi() {
		super();
	}

	/**
	 * Konstruktori ennalta syötetyillä kokoarvoilla
	 * @param w vastaa näytön leveyttä
	 * @param h vastaa näytön korkeutta
	 */
	public Visualisointi(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}
	
	/**
	 * Metodi tyhjennaNaytto hoitaa näkyvän osuuden tyhjentämisen.
	 */
	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
	
	/**
	 * Metodi uusiAsiakas piirtää näytölle vihreän pallon joka kuvastaa simulaattoriin saapuvaa asiakasta.
	 */
	public void uusiAsiakas() {
		gc.setFill(Color.GREEN);
		gc.fillOval(i,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();			
	}
	
	/**
	 * Metodi ekaPpA kuvastaa sinisellä pallolla ensimmäisen palvelupisteen A-puolta jossa asiakas on
	 */
	public void	ekaPpA() {
		gc.setFill(Color.BLUE);
		gc.fillOval(i+30,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}
	
	/**
	 * Metodi ekaPpB kuvastaa sinisellä neliöllä ensimmäisen palvelupisteen B-puolta jossa asiakas on.
	 */
	public void	ekaPpB() {
		gc.setFill(Color.BLUE);
		gc.fillRect(i+50,j,10,10);

		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}
	
	/**
	 * Metodi tokaPpA kuvastaa punaisella pallolla toisen palvelupisteen A-puolta jossa asiakas on.
	 */
	public void tokaPpA() {
		gc.setFill(Color.RED);
		gc.fillOval(i+70,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}
	
	/**
	 * Metodi tokaPpB kuvastaa punaisella neliöllä toisen palvelupisteen B-puolta jossa asiakas on.
	 */
	public void tokaPpB() {
		gc.setFill(Color.RED);
		gc.fillRect(i+90,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}
	
	/**
	 * Metodi tokaPpC kuvastaa punaisella pallolla toisen palvelupisteen C-puolta jossa asiakas on.
	 */
	public void tokaPpC() {
		gc.setFill(Color.RED);
		gc.fillOval(i+110,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}

	/**
	 * Metodi tokaPpD kuvastaa punaisella neliöllä toisen palvelupisteen D-puolta jossa asiakas on.
	 */
	public void tokaPpD() {
		gc.setFill(Color.RED);
		gc.fillRect(i+130,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}

	/**
	 * Metodi kolmasPpA kuvastaa mustalla pallolla kolmannen palvelupisteen A-puolta jossa asiakas on.
	 */
	public void kolmasPpA() {
		gc.setFill(Color.BLACK);
		gc.fillOval(i+150,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}
	
	/**
	 * Metodi kolmasPpB kuvastaa mustalla neliöllä kolmannen palvelupisteen B-puolta jossa asiakas on.
	 */
	public void kolmasPpB() {
		gc.setFill(Color.BLACK);
		gc.fillRect(i+170,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}
	
	/**
	 * Metodi nejasPp kuvastaa simuloinnista poistuvia asiakkaita vaaleansinisen neliön avulla.
	 */
	public void neljasPp() {
		gc.setFill(Color.CYAN);
		gc.fillRect(i+190,j,10,10);
		
		//i = (i + 10) % this.getWidth();
		j = (j + 10) % this.getHeight();
	}
}
