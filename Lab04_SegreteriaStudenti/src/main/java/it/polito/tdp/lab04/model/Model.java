package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	private CorsoDAO corsoDao = new CorsoDAO();
	private StudenteDAO studenteDao= new StudenteDAO();
	
	public List<Corso> getTuttiICorsi() {
		return corsoDao.getTuttiICorsi();
	}
	
	public Studente getStudentePerMatricola(Integer matricola) {
		return studenteDao.getStudentePerMatricola(matricola);
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public List<Corso> getCorsiPerMatricola(Integer matricola) {
		return studenteDao.getCorsiPerMatricola(matricola);
	}
	
	public boolean controllaIscrizione(Studente studente, Corso corso) {
		return corsoDao.controllaIscrizione(studente, corso);
	}
	
	public boolean iscriviStudenteAlCorso(Studente studente, Corso corso) {
		return corsoDao.iscriviStudenteAlCorso(studente, corso);
	}
}
