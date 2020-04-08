package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				corsi.add(c);				
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	public Corso getCorso(String codins) {
		final String sql = "SELECT * " + 
				"FROM corso " + 
				"WHERE codins = ?";
		
		Corso c = null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				c = new Corso(codins, rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			}
			
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
		
		return c;
	}

	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		final String sql = "SELECT s.matricola, s.cognome, s.nome, s.cds " + 
				"FROM iscrizione AS i, studente AS s " + 
				"WHERE s.matricola = i.matricola AND i.codins = ?";
		
		List<Studente> corsi = new LinkedList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Integer matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("cds");

				Studente s = new Studente(matricola, cognome, nome, cds);
				corsi.add(s);				
			}

			conn.close();
			
			return corsi;
		
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
	}

	public boolean controllaIscrizione(Studente studente, Corso corso) {
		final String sql = "SELECT * " + 
				"FROM iscrizione AS i " + 
				"WHERE i.matricola = ? AND i.codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			conn.close();
			
			if(!rs.next())
				return false;			

			return true;
		
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	public boolean iscriviStudenteAlCorso(Studente studente, Corso corso) {
		final String sql = "INSERT INTO `iscrizione` (`matricola`, `codins`) VALUES (?, ?)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodins());
			st.executeUpdate();

			conn.close();
			
			return true;
		
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
	}

}
