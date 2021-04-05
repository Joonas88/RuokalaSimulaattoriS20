package simu;

import java.util.PriorityQueue;
/**
 * Luokka pitää yllä Tapahtuma-luokan olioiden listaa.
 * 
 * @author Joonas Soininen
 * @version 1.0
 */

public class Tapahtumalista {
	/**
	 * Muuttuja lista on Tapahtuma-luokan parametrista muodostettu lista.
	 */
	private PriorityQueue<Tapahtuma> lista = new PriorityQueue<Tapahtuma>();
	
	/**
	 * Tyhjä konstruktori jonka avulla pystytään luomaan luokasta olioita toisissa luokissa.
	 */
	public Tapahtumalista(){
	 
	}
	
	/**
	 * Metodi poista toimii listan ylläpidossa ja tämä metodi poistaa listalta seuraavan olion.
	 * @return palauttaa listalta poistetutn tapahtuma-olion.
	 */
	public Tapahtuma poista(){
		Trace.out(Trace.Level.INFO,"Poisto " + lista.peek());
		return lista.remove();
	}
	
	/**
	 * Metodi lisaa lisää lista-muuttujaan t-olion.
	 * @param t on Tapahtuma-luokan olio.
	 */
	public void lisaa(Tapahtuma t){
		lista.add(t);
	}
	
	/**
	 * Metodi getSeuraavanAika hakee lista-muuttujasta seuraavan olion ajan.
	 * @return palauttaa seuraavan olion asetetun kellonajan.
	 */
	public double getSeuraavanAika(){
		return lista.peek().getAika();
	}	
	
}
