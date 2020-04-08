package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	public Studente getStudentePerMatricola(Integer matricola) {
		final String sql = "SELECT * " + 
				"FROM studente " + 
				"WHERE matricola = ?";
		
		Studente s = null;		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				s = new Studente(matricola, rs.getString("cognome"), rs.getString("nome"), rs.getString("cds"));
			}
			
			conn.close();
			
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
		
		return s;
	}
	
	public List<Corso> getCorsiPerMatricola(Integer matricola) {
		final String sql = "SELECT c.codins, c.crediti, c.nome, c.pd " + 
				"FROM iscrizione AS i, corso AS c " + 
				"WHERE matricola = ? AND c.codins = i.codins";
		
		List<Corso> corsi = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();
			
			if(!rs.next())
				return null;

			while (rs.next()) {
				String codins = rs.getString("codins");
				Integer crediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				Integer pd = rs.getInt("pd");

				Corso c = new Corso(codins, crediti, nome, pd);
				corsi.add(c);				
			}

			conn.close();
			
			return corsi;
		
		} catch (SQLException e) {
			throw new RuntimeException("Errore Db", e);
		}
	}
}
