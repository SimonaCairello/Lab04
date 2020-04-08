package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> choiceCorso;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtRisultato;

    @FXML
    private Button btnReset;

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String matr = txtMatricola.getText();
    	Integer matricola;
    	
    	try {
    		matricola = Integer.parseInt(matr);
    	} catch (NumberFormatException e) {
    		txtRisultato.appendText("Devi inserire un numero di matricola valido!\n");
    		return;
    	}
    	
    	if(model.getCorsiPerMatricola(matricola) == null) {
    		txtRisultato.appendText("Matricola inserita inesistente!\n");
    		return;
    	}
    	else if(model.getStudentePerMatricola(matricola).equals("")) {
    		txtRisultato.appendText("Lo studente non è iscritto a nessun corso!\n");
    		return;
    	}
    	
    	List<Corso> corsi = model.getCorsiPerMatricola(matricola);
    	for(Corso c : corsi)
    		txtRisultato.appendText(c.toString()+"\n");
    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	txtRisultato.clear();
    	
    	Corso c = choiceCorso.getValue();
    	
    	if(c.getNome().equals("")) {
    		txtRisultato.appendText("Selezionare un corso dal menu a tendina!\n");
    		return;
    	}
    	
    	List<Studente> studenti = model.getStudentiIscrittiAlCorso(c);
    	for(Studente s : studenti) {
    		txtRisultato.appendText(s.toString() + "\n");
    	}
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	txtRisultato.clear();
    	
    	Corso c = choiceCorso.getValue();
    	String matr = txtMatricola.getText();
    	Integer matricola;
    	
    	try {
    		matricola = Integer.parseInt(matr);
    	} catch (NumberFormatException e) {
    		txtRisultato.appendText("Devi inserire un numero di matricola valido!\n");
    		return;
    	}
    	
    	if(model.getStudentePerMatricola(matricola) == null) {
    		txtRisultato.appendText("Matricola inserita inesistente!\n");
    		return;
    	}
   
    	Studente s = model.getStudentePerMatricola(matricola);
    	
    	if(c.getNome().equals("")) {
    		txtRisultato.appendText("Selezionare un corso dal menu a tendina!\n");
    		return;
    	}
    	
    	if(model.controllaIscrizione(s, c)) {
    		txtRisultato.appendText("Studente gia iscritto al corso selzionato!\n");
    		return;
    	}
    	
    	if(model.iscriviStudenteAlCorso(s, c)) {
    		txtRisultato.appendText("Studente iscritto con successo!\n");
    		return;
    	}
    }

    @FXML
    void doOK(ActionEvent event) {
    	txtNome.clear();
    	txtCognome.clear();
    	txtRisultato.clear();
    	
    	String matr = txtMatricola.getText();
    	Integer matricola;
    	
    	try {
    		matricola = Integer.parseInt(matr);
    	} catch (NumberFormatException e) {
    		txtRisultato.appendText("Devi inserire un numero di matricola valido!\n");
    		return;
    	}
    	
    	if(model.getStudentePerMatricola(matricola) == null) {
    		txtRisultato.appendText("Matricola inserita inesistente!\n");
    		return;
    	}
    	
    	txtNome.appendText(model.getStudentePerMatricola(matricola).getNome());
    	txtCognome.appendText(model.getStudentePerMatricola(matricola).getCognome());
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtRisultato.clear();
    }

    @FXML
    void initialize() {
        assert choiceCorso != null : "fx:id=\"choiceCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnMatricola != null : "fx:id=\"btnMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	ObservableList<Corso> corsi = FXCollections.observableArrayList();
    	Corso corsoDefault = new Corso(null, null, "", null);
    	
    	for(Corso c : model.getTuttiICorsi()) {
    		corsi.add(c);
    	}
    	corsi.add(corsoDefault);
    	
    	choiceCorso.setItems(corsi);
    	choiceCorso.setValue(corsoDefault);
    	
    }
}

