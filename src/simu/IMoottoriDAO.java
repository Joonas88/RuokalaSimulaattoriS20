package simu;
/**
 * Luokka on rajapinta tietokantaa käyttävän MoottoriAccessObject-luokan ja moottori-luokan välillä.
 * 
 * @author Joonas Soininen
 * @version 1.0 *
 */
public interface IMoottoriDAO {
	/**
	 * Metodi getVasen on komento jolla haetaan tietokannasta yksi arvo.
	 */
	int getVasen();
	/**
	 * Metodi getOikea on komento jolla heataan tietokannasta yksi arvo. 
	 */	
	int getOikea();
}
