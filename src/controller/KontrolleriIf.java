package controller;
/**
 * Luokka KontroleriIf on välittäjäluokka, eli rajapinta, jolla välitetään tietoa pakkauksesta toiseen ja täten luokasta toiseen.
 * 
 * @author Joonas Soininen
 * @version 1.4
 */

public interface KontrolleriIf {
	//Rajapinnan metodit jotka tarjotaan moottorille.
	
	/**
	 * Metodi kaynnistaSimulointi aloittaa simulaattorin toiminnan.
	 */
	public void kaynnistaSimulointi();
	/**
	 * Metodi nopeuta nopeuttaa simulaation kulkua.
	 */
	public void nopeuta();
	/**
	 * Metodi hidasta hidastaa simulaation kulkua.
	 */
	public void hidasta();
	/**
	 * Metodi syote1 on käyttöliittymän palauttama käyttäjän syöttämä arvo.
	 */
	public int syote1();
	/**
	 * Metodi syote2 on käyttöliittymän palauttama käyttäjän syöttämä arvo.
	 */
	public int syote2();
	/**
	 * Metodi syote3 on käyttöliittymän palauttama käyttäjän syöttämä arvo.
	 */
	public int syote3();
	/**
	 * Metodi syote4 on käyttöliittymän palauttama käyttäjän syöttämä arvo.
	 */
	public int syote4();
	/**
	 * Metodi syote5 on käyttöliittymän palauttama käyttäjän syöttämä arvo.
	 */
	public int syote5();
	/**
	 * Metodi syote6 on käyttöliittymän palauttama käyttäjän syöttämä arvo.
	 */
	public int syote6();
	
	//Rajapinnan metodit jotka tarjotaan käyttöliittymälle.
	
	/**
	 * Metodi naytaLoppuaika välittää käyttöliittymälle simulaation lopetusajan.
	 * @param aika double-arvo, simulaation loppuaika
	 */
	public void naytaLoppuaika(double aika);
	/**
	 * Metodi visualisoiAsiakas on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoiAsiakas();
	/**
	 * Metodi visualisoiekappA on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoiekappA();
	/**
	 * Metodi visualisoiekappB on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoiekappB();
	/**
	 * Metodi visualisoitokappA on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoitokappA();
	/**
	 * Metodi visualisoitokappB on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoitokappB();
	/**
	 * Metodi visualisoitokappC on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoitokappC();
	/**
	 * Metodi visualisoitokappD on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoitokappD();
	/**
	 * Metodi visualisoikolmasppA on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoikolmasppA();
	/**
	 * Metodi visualisoikolmasppB on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoikolmasppB();
	/**
	 * Metodi visualisoineljaspp on käyttölittymälle piirtyvän simulaation kulkua kuvaavan JavaFX-metodien kutsu.
	 */
	public void visualisoineljaspp();
	/**
	 * Metodi naytaTulokset välittää käyttöliittymälle tiedostonien jonka avulla tulokset saadaa käyttäjän näkyville.
	 * @param out on merkkijono joka sisältää tiedostonimen jonka avulla tulostetaan ajokerran tulokset näkyville.
	 */
	public void naytaTulokset(String out);
	/**
	 * Metodi naytaJonopituus on palvleupisteiden jonojen pituuksia välittävä metodi jonka mukana kulkee myös päivittyvä asiakasmäärä.
	 * @param jono1 on ensimmäisen palvelupisteen lohkon A päivittyvä int-arvo.
	 * @param jono2 on ensimmäisen palvelupisteen lohkon B päivittyvä int-arvo.
	 * @param jono3 on toisen palvelupisteen lohkon A päivittyvä int-arvo.
	 * @param jono4 on toisen palvelupisteen lohkon B päivittyvä int-arvo.
	 * @param jono5 on toisen palvelupisteen lohkon C päivittyvä int-arvo.
	 * @param jono6 on toisen palvelupisteen lohkon D päivittyvä int-arvo.
	 * @param jono7 on kolmannen palvelupisteen lohkon A päivittyvä int-arvo.
	 * @param jono8 on kolmannen palvelupisteen lohkon B  päivittyvä int-arvo.
	 * @param pp4 on järjestelmästä poistuvien asiakkaiden päivittyvä int-arvo.
	 * @param asiakkaat on järjestelmään saapuvien asiakkaiden päivittyvä int-arvo.
	 */
	public void naytaJonopituus(int jono1, int jono2, int jono3, int jono4, int jono5, int jono6, int jono7, int jono8, int pp4, int asiakkaat);
	/**
	 * Metodi naytaKanta välittää tietokannasta kahta numeerista arvoa käyttöliittymään käyttäjän nähtäväksi.
	 * @param vasen on tietokannasta haettu jakauman korkeuden int-arvo.
	 * @param oikea on tietokannasta haettu jakauman leveyden int-arvo.
	 */
	public void naytaKanta(int vasen, int oikea);
}
