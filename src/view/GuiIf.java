package view;
/**
 * Käyttöliittymän rajapinta joka välittää tietoa käyttäjältä moottorille ja päinvastoin.
 * 
 * @author Joonas Soininen
 * @version 1.3
 */
public interface GuiIf {
	
	/**
	 * Metodi getAika välittää käyttäjän syöttämän simulointiajan moottorille.
	 */
	public double getAika();
	/**
	 * Metodi getViive välittää käyttäjän syöttämän viiveen moottorille.
	 */
	public long getViive();
	/**
	 * Metodi getSyote1 välittää käyttäjän syöttämän syötearvon moottorille.
	 */
	public int getSyote1();
	/**
	 * Metodi getSyote2 välittää käyttäjän syöttämän syötearvon moottorille.
	 */
	public int getSyote2();
	/**
	 * Metodi getSyote3 välittää käyttäjän syöttämän syötearvon moottorille.
	 */
	public int getSyote3();
	/**
	 * Metodi getSyote4 välittää käyttäjän syöttämän syötearvon moottorille.
	 */
	public int getSyote4();
	/**
	 * Metodi getSyote5 välittää käyttäjän syöttämän syötearvon moottorille.
	 */
	public int getSyote5();
	/**
	 * Metodi getSyote6 välittää käyttäjän syöttämän syötearvon moottorille.
	 */
	public int getSyote6();
	
	/**
	 * Metodi setLoppuaika välittää moottorilta simuloinnnin loppuajan käyttöliittymään
	 * @param aika on kellonaika.
	 */
	public void setLoppuaika(double aika);
	/**
	 * Metodi getVisualisointi välittää moottorilta Visulaisoitni-luokkaan tietoja
	 */	
	public Visualisointi getVisualisointi();	
	/**
	 * Metdoi setTulokset välittää tulosten tiedostonimen käyttölittyymään, joka tulostetaan käyttäjälle.
	 * @param out on tiedostonimi.
	 */
	public void setTulokset(String out);
	/**
	 * Metodi setJonopituus välitää mottorilta reaaliaikasta tietoa käyttöliittymään.
	 * @param jono1 on palvelupiste1A jononpituus.
	 * @param jono2 on palvelupiste1B jononpituus.
	 * @param jono3 on palvelupiste2A jononpituus.
	 * @param jono4 on palvelupiste2B jononpituus.
	 * @param jono5 on palvelupiste2C jononpituus.
	 * @param jono6 on palvelupiste2D jononpituus.
	 * @param jono7 on palvelupiste3A jononpituus.
	 * @param jono8 on palvelupiste3B jononpituus.
	 * @param pp4 on poistuvien asiakkaiden määrä.
	 * @param asiakkaat on saapuvien asiakkaiden määrä.
	 */
	public void setJonopituus(int jono1, int jono2, int jono3, int jono4, int jono5, int jono6, int jono7, int jono8, int pp4, int asiakkaat);
	/**
	 * Metodi setKanta välittää moottorilta tietokannan tulostuksia.
	 * @param vasen on tietokannasta haettu satunnaisluku.
	 * @param oikea on tietokannasta haettu satunnaisluku.
	 */
	public void setKanta(int vasen, int oikea);
	






}
