package view;
	

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.Trace;
import simu.Trace.Level;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
/**
 * Pääluokka, toimii käyttöliittymänä simulaattorille ja vastaa simulaattorin toiminnasta käyttäjän näkökulmasta.
 * 
 * @author Joonas Soininen
 * @version 1.9
 */
public class Main extends Application implements GuiIf{

	/**
	 * Olio kontrolleri mahdollistaa KontrolleriIf-luokan metodien käytön.
	 */
	private KontrolleriIf kontrolleri;	//Kontrollerin esittely (tarvitaan käyttöliittymässä)
	/**
	 * Muuttuja aika on tekstikenttä simuloinnin ajalle.
	 */
	private TextField aika;
	/**
	 * Muuttuja viive on tekstikenttä simuloinnin viivelle.
	 */
	private TextField viive;
	/**
	 * Muuttuja tulos on teksi johon tulotetaan simuloinnin tulos.
	 */
	private Text tulos;
	/**
	 * Muuttuja käynnistäButton on simuloinnin aloittava kytkin.
	 */
	private Button kaynnistaButton;
	/**
	 * Muuttuja käynnistäButton hidastaa simuloinnin kulkua.
	 */
	private Button hidastaButton;
	/**
	 * Muuttuja nopeutaButton nopeuttaa simuloinnin kulkua.
	 */
	private Button nopeutaButton;
	/**
	 * Muuttuja tuloksetButton avaa tulosten tiedostosijainnin.
	 */
	private Button tuloksetButton;
	/**
	 * Muttuja naytto on simuloinnin viusaalisen ilmentymään.
	 */
	private Visualisointi naytto;
	/**
	 * Muuttuja syote1 on tekstikenttä simuloinnin syötteiden muuttamiseen.
	 */
	private TextField syote1;
	/**
	 * Muuttuja syote2 on tekstikenttä simuloinnin syötteiden muuttamiseen.
	 */
	private TextField syote2;
	/**
	 * Muuttuja syote3 on tekstikenttä simuloinnin syötteiden muuttamiseen.
	 */
	private TextField syote3;
	/**
	 * Muuttuja syote4 on tekstikenttä simuloinnin syötteiden muuttamiseen.
	 */
	private TextField syote4;
	/**
	 * Muuttuja syote5 on tekstikenttä simuloinnin syötteiden muuttamiseen.
	 */
	private TextField syote5;
	/**
	 * Muuttuja syote6 on tekstikenttä simuloinnin syötteiden muuttamiseen.
	 */
	private TextField syote6;
	/**
	 * Muuttuja jono1 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono1;
	/**
	 * Muuttuja jono2 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono2;
	/**
	 * Muuttuja jono3 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono3;
	/**
	 * Muuttuja jono4 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono4;
	/**
	 * Muuttuja jono5 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono5;
	/**
	 * Muuttuja jono6 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono6;
	/**
	 * Muuttuja jono7 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono7;
	/**
	 * Muuttuja jono8 on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text jono8;
	/**
	 * Muuttuja poistuneet on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text poistuneet;
	/**
	 * Muuttuja saapuneet on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text saapuneet;
	/**
	 * Muuttuja kantaVasen on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text kantaVasen;
	/**
	 * Muuttuja kantaOikea on tekstipaikka johon tulostetaan reaaliaikasta titoa simuloinnin edetessä.
	 */
	private Text kantaOikea;
	/**
	 * Muuttuja tulokset on lista jossa tulos-muuttuja näkyy.
	 */
	private ObservableList<String> tulokset = FXCollections.observableArrayList();
	/**
	 * Muuttuja lines on tuloksien haun yhteydessä käytettävä lista.
	 */
	private List<String> lines = new ArrayList<String>();

	/**
	 * Metodi init alustaa kontrolleri-olion.
	 */
	@Override
	public void init(){
		
		Trace.setTraceLevel(Level.INFO);
		
		kontrolleri = new Kontrolleri(this);
	}

	/**
	 * Metodi start rakentaa käyttöliittymän jonka käytäjä näkee. Metodissa on määritelty tekstikenttie, nappuloiden ja tekstien paikat, 
	 * asettelut, fonttikoot sekä nappuloiden painalluksen toiminnot. Metodi kutsuu muita metodeja ja sen sisällä luodaan uusia muuttujia, 
	 * jotta saadaan aikaan kaikki se mitä käyttäjä näkee.
	 * @param primaryStage on JavaFX:n visuaalisen käyttöliittymän olio.
	 */
	@Override
	public void start(Stage primaryStage) {

		try {
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			    @Override
			    public void handle(WindowEvent t) {
			        Platform.exit();
			        System.exit(0);
			    }
			});
						
			
			primaryStage.setTitle("Ruokalasimulaattori");

			kaynnistaButton = new Button();
			kaynnistaButton.setText("Käynnistä simulointi");
			

				kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		 
		   				try {
		   					Integer.parseInt(syote1.getText());
		   					Integer.parseInt(syote2.getText());
	    					Integer.parseInt(syote3.getText());
	    					Integer.parseInt(syote4.getText());
	    					Integer.parseInt(syote5.getText());
	    					Integer.parseInt(syote6.getText());
	    					Double.parseDouble(aika.getText());
	    					Long.parseLong(viive.getText());
	    					
			    			if (Integer.parseInt(syote1.getText())<0|Integer.parseInt(syote2.getText())<0|
			    					Integer.parseInt(syote3.getText())<0|Integer.parseInt(syote4.getText())<0|
			    					Integer.parseInt(syote5.getText())<0|Integer.parseInt(syote6.getText())<0) {
			    				Alert alert = new Alert(AlertType.WARNING);		 
			    				alert.setTitle("VAROITUS!");
			    				alert.setHeaderText("Syötteen arvo ei voi olla negatiivinen!");
			    				alert.setContentText("Asenta postiivinen luku ja koita uudelleen.");
			    				alert.showAndWait();		    				
			    			} else if (Double.parseDouble(aika.getText())<0) {
			    				Alert alert = new Alert(AlertType.WARNING);
			    				alert.setTitle("VAROITUS!");
			    				alert.setHeaderText("Ajan arvo ei voi olla negatiivinen!");
			    				alert.setContentText("Asenta postiivinen luku ja koita uudelleen.");
			    				alert.showAndWait();
			    			} else if (Long.parseLong(viive.getText())<0) {
			    				Alert alert = new Alert(AlertType.WARNING);
			    				alert.setTitle("VAROITUS!");
			    				alert.setHeaderText("Viiveen arvo ei voi olla negatiivinen!");
			    				alert.setContentText("Asenta postiivinen luku ja koita uudelleen.");
			    				alert.showAndWait();
			    			} else {	
			    				kaynnistaButton.setDisable(true);
			    				hidastaButton.setDisable(false);
			    				nopeutaButton.setDisable(false);
				                kontrolleri.kaynnistaSimulointi();
				        		lines.clear();
			    			}  	
			    			
	    				} catch (NumberFormatException ex) {
		    				Alert alert = new Alert(AlertType.WARNING);		 
		    				alert.setTitle("VAROITUS!");
		    				alert.setHeaderText("Syötetty arvo ei voi olla kirjain!");
		    				alert.setContentText("Asenta postiivinen luku ja koita uudelleen.");
		    				alert.showAndWait();	
						}
		            	

		            }
		        });

			Label hidastaLabel = new Label("Hidasta simulointia");
			hidastaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
			hidastaButton = new Button();
			hidastaButton.setText("Hidasta");
			hidastaButton.setDisable(true);
			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			Label nopeutaLabel = new Label("Nopeuta simulointia");
			nopeutaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
			nopeutaButton = new Button();
			nopeutaButton.setText("Nopeuta");
			nopeutaButton.setDisable(true);
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());
			
			Label tuloksetButtonLabel = new Label("Tulosten tiedostosijainti:");
			tuloksetButtonLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
			tuloksetButton = new Button();
			tuloksetButton.setText("Tulokset");
			tuloksetButton.setDisable(true);
			tuloksetButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					File file = new File ("data");
				    Desktop desktop = Desktop.getDesktop();
				    try {
				    	desktop.open(file);
				    } catch (Exception e) {
	    				Alert alert = new Alert(AlertType.WARNING);		 
	    				alert.setTitle("VAROITUS!");
	    				alert.setHeaderText("Haettua tiedostosijaintia ei löytnyt");
	    				alert.setContentText("Ota yhteyttä sovelluksen kehittäjään.");
	    				alert.showAndWait();	
					} 
					
				}
				
			});
	        
			Label aikaLabel = new Label("Simulointiaika (ms):");
			aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        aika = new TextField("1000");
	        aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        aika.setPrefWidth(150);

	        Label viiveLabel = new Label("Simuloinnin viive (ms):");
	        viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        viive = new TextField("50");
	        viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        viive.setPrefWidth(150);
	                	        
	        Label kokonaisaikaLabel = new Label("Kello on:");
	        kokonaisaikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        tulos = new Text();
	        tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        	        
	        Label syote1Label = new Label("Käsienpesupisteet\nJakauman huippu:");
	        syote1Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        syote1 = new TextField("10");
	        syote1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        syote1.setPrefWidth(150);
	        
	        Label syote2Label = new Label("Vakiona 10 ja 6\nJakauman leveys:");
	        syote2Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        syote2 = new TextField("6");
	        syote2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        syote2.setPrefWidth(150);
	        
	        Label syote3Label = new Label("Ruokalinjat\nJakauman huippu:");
	        syote3Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        syote3 = new TextField("20");
	        syote3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        syote3.setPrefWidth(150);
	        
	        Label syote4Label = new Label("Vakiona 5 ja 2\nJakauman leveys:");
	        syote4Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        syote4 = new TextField("4");
	        syote4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        syote4.setPrefWidth(150);
	        
	        Label syote5Label = new Label("Astianpalautuspisteet\nJakauman huippu:");
	        syote5Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        syote5 = new TextField("8");
	        syote5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        syote5.setPrefWidth(150);
	        
	        Label syote6Label = new Label("Vakiona 8 ja 4\nJakauman leveys:");
	        syote6Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        syote6 = new TextField("4");
	        syote6.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        syote6.setPrefWidth(150);
	        
	        Label kantaVasenLabel = new Label("Poistuminen\njakauman alkuarvo:");
	        kantaVasenLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        kantaVasen = new Text();
	        kantaVasen.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label kantaOikeaLabel = new Label("Poistuminen\njakauman loppuarvo:");
	        kantaOikeaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        kantaOikea = new Text();
	        kantaOikea.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        GridPane gridVasen = new GridPane();
	        gridVasen.setAlignment(Pos.CENTER);
	        gridVasen.setVgap(20);
	        gridVasen.setHgap(10);

	        gridVasen.add(aikaLabel,0, 0);
	        gridVasen.add(aika, 0, 1);
	        gridVasen.add(viiveLabel, 1, 0);
	        gridVasen.add(viive, 1, 1);               
	        gridVasen.add(syote1Label, 0, 2);
	        gridVasen.add(syote1, 0, 3);
	        gridVasen.add(syote2Label, 1, 2);
	        gridVasen.add(syote2, 1, 3);
	        gridVasen.add(syote3Label, 0, 4);
	        gridVasen.add(syote3, 0, 5);
	        gridVasen.add(syote4Label, 1, 4);
	        gridVasen.add(syote4, 1, 5);     
	        gridVasen.add(syote5Label, 0, 6);
	        gridVasen.add(syote5, 0, 7);
	        gridVasen.add(syote6Label, 1, 6);
	        gridVasen.add(syote6, 1, 7);
	        gridVasen.add(kaynnistaButton,0, 8);
	        gridVasen.add(kokonaisaikaLabel, 0, 9);
	        gridVasen.add(tulos, 1, 9);        
	        gridVasen.add(nopeutaButton, 0, 10);        
	        gridVasen.add(hidastaButton, 1, 10);
	        gridVasen.add(kantaVasenLabel, 0, 11);
	        gridVasen.add(kantaVasen, 0, 12);
	        gridVasen.add(kantaOikeaLabel,1,11);
	        gridVasen.add(kantaOikea, 1, 12);
	        
	        HBox hBox = new HBox();
	        hBox.setPadding(new Insets(15, 12, 15, 12));
	        hBox.setSpacing(10); 
	        hBox.setStyle("-fx-background-color: #ba77ed");
	        
	        HBox listaBox = new HBox();
			listaBox.setPadding(new Insets(15, 12, 15, 12));
			listaBox.setSpacing(10); 
			listaBox.setStyle("-fx-background-color: #ba77ed");

			ListView<String> tulosList = new ListView<String>();
			tulosList.setPrefSize(350, 600);		
			
			tulosList.setItems(tulokset);

			VBox tulosBox = new VBox();
			tulosBox.getChildren().addAll(new Label("Simuloinnin tuloksia"), tulosList);

			listaBox.getChildren().addAll(tulosBox);
	        
			HBox tulos1Box = new HBox();
	        tulos1Box.setPadding(new Insets(15, 12, 15, 12));
	        tulos1Box.setSpacing(10); 
	        tulos1Box.setStyle("-fx-background-color: #6ad9f7");
	        
	        Label jono1Label = new Label("Käsienpesu A:");
	        jono1Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono1 = new Text();
	        jono1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label jono2Label = new Label("Käsienpesu B:");
	        jono2Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono2 = new Text();
	        jono2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label jono3Label = new Label("Ruokalinja A:");
	        jono3Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono3 = new Text();
	        jono3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label jono4Label = new Label("Ruokalinja B:");
	        jono4Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono4 = new Text();
	        jono4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

	        Label jono5Label = new Label("Ruokalinja C:");
	        jono5Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono5 = new Text();
	        jono5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label jono6Label = new Label("Ruokalinja D:");
	        jono6Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono6 = new Text();
	        jono6.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label jono7Label = new Label("Astianpalautus A:");
	        jono7Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono7 = new Text();
	        jono7.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label jono8Label = new Label("Astianpalautus B:");
	        jono8Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        jono8 = new Text();
	        jono8.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label saapuneetLabel = new Label("Saapuneet asiakkaat:");
	        saapuneetLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        saapuneet = new Text();
	        saapuneet.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label poistuneetLabel = new Label("Poistuneet asiakkaat:");
	        poistuneetLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        poistuneet = new Text();
	        poistuneet.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	        
	        Label jonopituudet = new Label("Palvelupisteiden\njonojen pituudet");
	        jonopituudet.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        
	        GridPane gridOikea = new GridPane();
	        gridOikea.setAlignment(Pos.BASELINE_LEFT);
	        gridOikea.setVgap(20);
	        gridOikea.setHgap(10);
	        gridOikea.setPrefSize(250, 500);
	        
	        gridOikea.add(jonopituudet, 0, 0);
	        gridOikea.add(jono1Label, 0, 1);
	        gridOikea.add(jono1, 1, 1);
	        gridOikea.add(jono2Label, 0, 2);
	        gridOikea.add(jono2, 1, 2);
	        gridOikea.add(jono3Label, 0, 3);
	        gridOikea.add(jono3, 1, 3);
	        gridOikea.add(jono4Label, 0, 4);
	        gridOikea.add(jono4, 1, 4);
	        gridOikea.add(jono5Label, 0, 5);
	        gridOikea.add(jono5, 1, 5);
	        gridOikea.add(jono6Label, 0, 6);
	        gridOikea.add(jono6, 1, 6);
	        gridOikea.add(jono7Label, 0, 7);
	        gridOikea.add(jono7, 1, 7);
	        gridOikea.add(jono8Label, 0, 8);
	        gridOikea.add(jono8, 1 ,8);
	        gridOikea.add(saapuneetLabel, 0, 9);
	        gridOikea.add(saapuneet, 1 ,9);
	        gridOikea.add(poistuneetLabel, 0, 10);
	        gridOikea.add(poistuneet, 1 ,10);
	        gridOikea.add(tuloksetButtonLabel, 0, 13);
	        gridOikea.add(tuloksetButton, 0, 14);
		        
	        naytto = new Visualisointi(250,500);
	   
	        GridPane gridNaytto = new GridPane();
	        gridNaytto.setAlignment(Pos.BASELINE_LEFT);
	        gridNaytto.setVgap(20);
	        gridNaytto.setHgap(10);
	        
	        gridNaytto.add(naytto, 0,0);
	        
	        Label nayttoLabel=new Label(""
	        		+ "Vihreä = saapuva asiakas"
					+ "\nSininen = käsienpesu"
					+ "\nPunainen = ruokalinja"
					+ "\nMusta = astianpalatus"
					+ "\nVaaleansininen = poistuminen");       
	        nayttoLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
	        
	        gridNaytto.add(nayttoLabel, 0, 1);
	       
	        hBox.getChildren().addAll(gridVasen, gridNaytto, gridOikea, listaBox);
	        
	        Scene scene = new Scene(hBox);
	        primaryStage.setScene(scene);
	        primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	/**
	 * Metodi getAika on rajapintametodi käyttäjän syöttämän ajan saamiseksi.
	 */
	@Override
	public double getAika(){
		
		return Double.parseDouble(aika.getText());
		
	}

	/**
	 *Metodi getViive on rajapintametodi käyttäjän syöttämän viiveen saamiseksi. 
	 */
	@Override
	public long getViive(){
		return Long.parseLong(viive.getText());
	}

	/**
	 * Metodi setTulokset on rajapintametodi tulosten asettamiseksi visuaaliseen näkymään käyttäjän luettavaksi. Metodin sisällä myös 
	 * toteutetaan tiedostosta lukeminen missä simuloinnin tulokset sijaitsevat sekä niiden listaaminen ja näkyville asettaminen.
	 * @param out on simuloinnin tulosten tiedostonimi.
	 */
	@Override
	public void setTulokset(String out) {
		tulokset.clear();
	    try {
	        File myObj = new File("data\\"+out);
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          Trace.out(Trace.Level.INFO,data);
	          lines.add(data);
	        }
	        myReader.close();
	      } catch (FileNotFoundException e) {
	    	  Trace.out(Trace.Level.ERR,"An error occurred.");
	        e.printStackTrace();
	      }
	    Trace.out(Trace.Level.INFO,lines.toString());
	    tulokset.addAll(lines);
	    kaynnistaButton.setDisable(false);
	    hidastaButton.setDisable(true);
	    nopeutaButton.setDisable(true);  
	    tuloksetButton.setDisable(false);
	}
	
	/**
	 * Metodi setLoppuaika on rajapintametodi jolla asetetaan simuloinnnin loppuaika käyttöliittymään. Loppuaika formatoidaan näkymään
	 * kahden desimaalin tarkuudella.
	 * @param aika on arvo joka palautuu moottorilta.
	 */
	@Override
	public void setLoppuaika(double aika){
		 DecimalFormat formatter = new DecimalFormat("#0.00");
		 this.tulos.setText(formatter.format(aika));		
	}

	/**
	 * Metodi getVisualisointi on rajapintametodi joka palauttaa näyton koon.
	 * @return palauttaa naytto-muuttujalle annetun arvon.
	 */
	@Override
	public Visualisointi getVisualisointi() {
		 return naytto;
	}
	
	/**
	 * Metodi getSyote1 on rajapintametodi jolla viedään moottorille käyttäjän antama syötearvo.
	 */
	@Override
	public int getSyote1() {
		return Integer.parseInt(syote1.getText());
	}
	
	/**
	 * Metodi getSyote2 on rajapintametodi jolla viedään moottorille käyttäjän antama syötearvo.
	 */
	@Override
	public int getSyote2() {
		return Integer.parseInt(syote2.getText());
	}
	
	/**
	 * Metodi getSyote3 on rajapintametodi jolla viedään moottorille käyttäjän antama syötearvo.
	 */
	@Override
	public int getSyote3() {
		return Integer.parseInt(syote3.getText());
	}
	
	/**
	 * Metodi getSyote4 on rajapintametodi jolla viedään moottorille käyttäjän antama syötearvo.
	 */
	@Override
	public int getSyote4() {
		return Integer.parseInt(syote4.getText());
	}
	
	/**
	 * Metodi getSyote5 on rajapintametodi jolla viedään moottorille käyttäjän antama syötearvo.
	 */
	@Override
	public int getSyote5() {
		return Integer.parseInt(syote5.getText());
	}
	
	/**
	 * Metodi getSyote6 on rajapintametodi jolla viedään moottorille käyttäjän antama syötearvo.
	 */
	@Override
	public int getSyote6() {
		return Integer.parseInt(syote6.getText());
	}
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen
	/**
	 * Metodilla käynnistetään JavaFX-sovelluksen käyttöliittymä
	 * @param args vakio Java-luokan käynnistävä parametri.
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Metodi setJonopituus on rajapintametodi joka tuo moottorilta useita arvoja käyttöliittymään tulostettavaksi. Jonojen pituudet, 
	 * saapuneet sekä poistuneet asiakkaat.
	 * @param jono1 ensimmäisen palvelupisteen A jonon pituus.
	 * @param jono2 ensimmäisen palvelupisteen B jonon pituus.
	 * @param jono3 toisen palvelupisteen A jonon pituus.
	 * @param jono4 toisen palvelupisteen B jonon pituus.
	 * @param jono5 toisen palvelupisteen C jonon pituus.
	 * @param jono6 toisen palvelupisteen D jonon pituus.
	 * @param jono7 kolmannen palvelupisteen A jonon pituus.
	 * @param jono8 kolmannen palvelupisteen B jonon pituus.
	 * @param pp4 poistuneet asiakkaat.
	 * @param asiakkaat saapuneet asiakkaat.
	 * 
	 */
	@Override
	public void setJonopituus(int jono1, int jono2, int jono3, int jono4, int jono5, int jono6, int jono7, int jono8, int pp4, int asiakkaat) {
		this.jono1.setText(Integer.toString(jono1));
		this.jono2.setText(Integer.toString(jono2));
		this.jono3.setText(Integer.toString(jono3));
		this.jono4.setText(Integer.toString(jono4));
		this.jono5.setText(Integer.toString(jono5));
		this.jono6.setText(Integer.toString(jono6));
		this.jono7.setText(Integer.toString(jono7));
		this.jono8.setText(Integer.toString(jono8));
		this.poistuneet.setText(Integer.toString(pp4));
		this.saapuneet.setText(Integer.toString(asiakkaat));
	}

	/**
	 * Metodi setKanta on rajapintametodi joka tuo moottorilta tietokannan kaksi arvoa käyttöliittmyään tulostettavaksi.
	 * @param vasen ensimmäinen tietokannan luku.
 	 * @param oikea toinen tietokannan luku.
	 */
	@Override
	public void setKanta(int vasen, int oikea) {
		this.kantaVasen.setText(Integer.toString(vasen));
		this.kantaOikea.setText(Integer.toString(oikea));
		
	}

	
}
