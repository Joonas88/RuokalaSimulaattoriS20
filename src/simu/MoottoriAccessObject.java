package simu;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * Luokka toimii tietokannan lukijana.
 * 
 * @author Joonas Soininen
 * @version 1.0
 */

public class MoottoriAccessObject implements IMoottoriDAO {

	/**
	 * Olio myCon on tietokannan yhteyden ilmentymä.
	 */
	private Connection myCon;

	/**
	 * Konstruktorilla määritellään yhteys tietokantaan
	 */
	public MoottoriAccessObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			myCon = DriverManager.getConnection("jdbc:mysql://localhost/alkukanta","joonas","joonas");  
			} catch (Exception e) {
				System.err.println("Virhe tietokantayhteyden muodostamisessa.");
				System.exit(-1);// turha jatkaa...  }
		}

		
	}
	
	/**
	 * Metodilla getVasen haetaan tietokannasta yksi satunnaisluku.
	 */
	@Override
	public int getVasen() {
		int vasen = 0;
		
	    List<Integer> givenList = Arrays.asList(10, 15, 5, 6, 7, 8, 9);
	    Random rand = new Random();
	    int randomElement = givenList.get(rand.nextInt(givenList.size()));
	    
		try (PreparedStatement myStatement = myCon.prepareStatement("SELECT * FROM lukutaulu WHERE vasenluku=?"))
		{
			myStatement.setLong(1, randomElement);
			ResultSet myRs = myStatement.executeQuery();
			if (myRs.next()) {
				int rs_tunnus = myRs.getInt("vasenluku");
				vasen = rs_tunnus;
			}
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: " +e.getMessage());
				System.err.println("Virhekoodi: " +e.getErrorCode());
				System.err.println("SQL-tilakoodi: " +e.getSQLState());
			} while (e.getNextException() !=null);
		}
		//System.out.println(vasen);
		return vasen;
	}

	/**
	 * Metodilla getOikea haetaan tietokannasta yksi satunnaisluku.
	 */
	@Override
	public int getOikea() {
		int oikea = 0;
		
	    List<Integer> givenList = Arrays.asList(10, 15, 5, 6, 7, 8, 9);
	    Random rand = new Random();
	    int randomElement = givenList.get(rand.nextInt(givenList.size()));
		
		try (PreparedStatement myStatement = myCon.prepareStatement("SELECT * FROM lukutaulu WHERE vasenluku=?"))
		{
			myStatement.setLong(1, randomElement);
			ResultSet myRs = myStatement.executeQuery();
			if (myRs.next()) {
				int rs_tunnus = myRs.getInt("oikealuku");
				oikea = rs_tunnus;
			}
		} catch (SQLException e) {
			do {
				System.err.println("Viesti: " +e.getMessage());
				System.err.println("Virhekoodi: " +e.getErrorCode());
				System.err.println("SQL-tilakoodi: " +e.getSQLState());
			} while (e.getNextException() !=null);
		}
		//System.out.println(oikea);
		return oikea;
	}

	/**
	 * Metodi filaize sulkee tietokantayhteyden.
	 */
	@Override
	protected void finalize() {
		try {	
			if (myCon !=null)
				myCon.close(); 
			}catch (Exception e) {
				e.printStackTrace(); 
				} 
		
	}

}
