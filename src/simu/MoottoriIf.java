package simu;
/**
 * Luokka toimii rajapintaja moottorin ja kontrolleri-luokan välillä.
 * 
 * @author Joonas Soininen
 * @version 1.0
 */
public interface MoottoriIf {
		
	// Kontrolleri käyttää tätä rajapintaa
	/**
	 * Metodi setSimulointiaika välittää muuttujan arvon kontrollerille.
	 * @param aika on käyttöliittymässä syötetty simuloinnin kokonaisaika.
	 */
	public void setSimulointiaika(double aika);
	/**
	 * Metodi setViive välittää muuttujan arvon kontrollerille.
	 * @param aika on käyttöliittymässä syötetty simuloinnin viive.
	 */
	public void setViive(long aika);
	/**
	 * Palauttaa kontrollerin kautta käyttöliittymään viiveen.
	 * @return palauttaa viiveen päivittyävänä tietona.
	 */
	public long getViive();
}
