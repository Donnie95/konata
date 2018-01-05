package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import componenti.Ambiente;
import componenti.Rilevazione;
import componenti.Sensore;
import componenti.UserAccount;
import exceptions.NullException;
import exceptions.ZeroException;
/**
 * 
 * @author gandalf
 *
 */
public class DBUtils {
	
	public static int maxIdAm(Connection conn) throws SQLException {
		
		String sql = "SELECT MAX(ID) FROM Ambiente;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()) {
			
			int nextId = rs.getInt("MAX(ID)");
			
			return nextId;
		}
		return 0;
	}
	
	public static int maxIdSens(Connection conn) throws SQLException {
		 
		String sql = "SELECT MAX(Cod) FROM Sensore;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()) {
			
			int nextId = rs.getInt("MAX(Cod)");
			
			return nextId;
		}
		
		return 0;
	}
	
	public static void clearRilev(Connection conn) throws SQLException {
		String sql = "TRUNCATE Rilevazione;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.executeUpdate();
	}

	public static UserAccount findUser(Connection conn, String userName, String password) throws SQLException {
		
		String sql = "SELECT a.Nome_Utente, a.Password, a.Privilegi FROM Utente a WHERE a.Nome_utente = ? AND a.Password = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()) {
			
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			return user;
		}
		return null;
	}
	
	public static UserAccount findUser(Connection conn, String userName) throws SQLException {
		
		String sql = "SELECT a.Nome_utente, a.Password, a.Privilegi FROM Utente a WHERE a.Nome_Utente = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,  userName);
		
		ResultSet rs = pstm.executeQuery();
		
		if(rs.next()) {
			
			String password = rs.getString("Password");
			int privilegi = rs.getInt("Privilegi");
			
			UserAccount user = new UserAccount();
			user.setUserName(userName);
			user.setPassword(password);
			user.setPrivilegi(privilegi);
			return user;
		}
		return null;
	}
	
	public static void updateUser(Connection conn, String oldUser, UserAccount user) throws SQLException {
		String sql = "UPDATE Utente SET Nome_Utente = ?, Password = ?, Privilegi = ? WHERE  Nome_Utente = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, user.getUserName());
		pstm.setString(2, user.getPassword());
		pstm.setInt(3, user.getPrivilegi());
		pstm.setString(4, oldUser);
		
		pstm.executeUpdate();
	}

	public static void insertUser(Connection conn, UserAccount user) throws SQLException {
		String sql = "INSERT INTO Utente(Nome_Utente, Password, Privilegi, AmbienteID) VALUES (?, ?, ?, ?);";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, user.getUserName());
		pstm.setString(2, user.getPassword());
		pstm.setInt(3, user.getPrivilegi());
		pstm.setInt(4, user.getAmbientID());
		
		pstm.executeUpdate();
	}
	
	public static void deleteUser(Connection conn, String username) throws SQLException {
		
		String sql = "DELETE FROM Utente WHERE Nome_Utente = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, username);
		
		pstm.executeUpdate();
	}
	
	
	
	public static ArrayList<Ambiente> queryAmbienti(Connection conn, String name) throws SQLException, ZeroException, NullException {
				
		String sql = "SELECT a.ID, a.Nome, t.Tipo, a.Ubicazione, a.numeroSensori FROM Ambiente a INNER JOIN Utente u INNER JOIN TipologiaA t WHERE a.id = u.AmbienteID AND a.TipologiaA = t.ID AND Nome_Utente = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, name);
		
		ResultSet rs = pstm.executeQuery();
		
		ArrayList<Ambiente> ambienti = new ArrayList<Ambiente>();
		
		while(rs.next()) {
			
			int id = rs.getInt("ID");
			String nome = rs.getString("Nome");
			String tipologia = rs.getString("Tipo");
			String ubicazione = rs.getString("Ubicazione");
			int n_Sens = rs.getInt("NumeroSensori");
			
			Ambiente ambiente = new Ambiente();
			
			ambiente.setId(id);
			ambiente.setNome(nome);
			ambiente.setTipo(tipologia);
			ambiente.setUbicazione(ubicazione);
			ambiente.setNumeroSensori(n_Sens);
			
			ambienti.add(ambiente);
		}
		return ambienti;
	}
	
	public static Ambiente findAmbiente(Connection conn, String idStr) throws SQLException, ZeroException, NullException {
		
		String sql = "SELECT a.Nome, a.TipologiaA, a.Ubicazione, a.NumeroSensori FROM Ambiente a WHERE a.ID = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, idStr);
		
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()) {
			
			String nome = rs.getString("Nome");
			String tipologia = rs.getString("TipologiaA");
			String ubicazione = rs.getString("Ubicazione");
			int n_Sens = rs.getInt("NumeroSensori");
			
			Ambiente ambiente = new Ambiente(nome, tipologia, ubicazione, n_Sens);
			
			return ambiente;
		}
		return null;
	}
	
	public static void insertAmbient(Connection conn, Ambiente amb) throws SQLException {
		String sql = "INSERT INTO Ambiente(Nome, Ubicazione, TipologiaA, NumeroSensori) VALUES (?, ?, ?, ?);";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, amb.getNome());
		pstm.setString(2, amb.getUbicazione());
		pstm.setString(3, amb.getTipo());
		pstm.setInt(4, amb.getNumeroSensori());
		
		pstm.executeUpdate();
	}
	
	public static void deleteAmbient(Connection conn, String id) throws SQLException {
		String sql = "DELETE FROM Ambiente WHERE Id = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, id);
		
		pstm.executeUpdate();
	}
	
	public static void updateAmbient(Connection conn, Ambiente ambient) throws SQLException {
		String sql = "UPDATE Ambiente SET Nome = ?, Ubicazione = ?, TipologiaA = ?, NumeroSensori = ? WHERE Id = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, ambient.getNome());
		pstm.setString(2, ambient.getUbicazione());
		pstm.setString(3, ambient.getTipo());
		pstm.setInt(4, ambient.getNumeroSensori());
		pstm.setInt(5, ambient.getId() );
		
		pstm.executeUpdate();
	}

	public static ArrayList<Sensore> querySensori(Connection conn, String ambID) throws SQLException, ZeroException, NullException {
		
		String sql = "SELECT s.Cod, s.Modello, s.Marca, t.Tipo, s.Anno FROM Sensore s INNER JOIN Ambiente a INNER JOIN TipologiaS t WHERE a.ID = s.Ambiente AND t.ID = s.TipologiaS AND s.Ambiente = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, ambID);
		
		ResultSet rs = pstm.executeQuery();
		
		ArrayList<Sensore> sensori = new ArrayList<Sensore>();
		
		while(rs.next()) {
			
			int id = rs.getInt("Cod");
			String mod = rs.getString("Modello");
			String marca = rs.getString("Marca");
			String tipologia = rs.getString("Tipo");
			java.sql.Date anno = rs.getDate("Anno");
			
			Sensore sensore = new Sensore();
			
			sensore.setId(id);
			sensore.setModello(mod);
			sensore.setMarca(marca);
			sensore.setTipo(tipologia);
			sensore.setAnno(anno);
			
			
			sensori.add(sensore);
		}
		return sensori;
	}
	
	public static Sensore findSensore(Connection conn, String idStr) throws SQLException, ZeroException, NullException {
		
		String sql = "SELECT s.Cod, s.Modello, s.Marca, s.TipologiaS, s.Anno FROM Sensore s WHERE s.Cod = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, idStr);
		
		ResultSet rs = pstm.executeQuery();
		
		while(rs.next()) {
			
			String mod = rs.getString("Modello");
			String marca = rs.getString("Marca");
			String tipologia = rs.getString("TipologiaS");
			java.sql.Date anno = rs.getDate("Anno");
			
			Sensore sensore = new Sensore(mod, marca, tipologia, anno);
			
			return sensore;
		}
		return null;
	}
	
	public static void insertSensor(Connection conn, Sensore sens) throws SQLException {
		
		String sql = "INSERT INTO Sensore(Marca, Modello, TipologiaS, Anno, Ambiente) VALUES (?, ?, ?, ?, ?);";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, sens.getMarca());
		pstm.setString(2, sens.getModello());
		pstm.setString(3, sens.getTipo());
		pstm.setDate(4, (java.sql.Date) sens.getAnno());
		pstm.setInt(5, sens.getAmbientId());
		
		pstm.executeUpdate();
	}
	
	public static void deleteSensor(Connection conn, String id) throws SQLException {
		
		String sql = "DELETE FROM Sensore WHERE cod = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, id);
		
		pstm.executeUpdate();
	}
	
	public static void updateSensor(Connection conn, Sensore sensor) throws SQLException {
		
		String sql = "UPDATE Sensore SET Marca = ?, Modello = ?, TipologiaS = ?, Anno = ? WHERE Cod = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, sensor.getMarca());
		pstm.setString(2, sensor.getModello());
		pstm.setString(3, sensor.getTipo());
		pstm.setDate(4, sensor.getAnno());
		pstm.setInt(5, sensor.getId());
		
		pstm.executeUpdate();
	}
	
	public static void insertRelev(Connection conn, Rilevazione relev) throws SQLException {
		
		String sql = "INSERT INTO Rilevazione(Messaggio, Descrizione, Sensore, Data) VALUES (?, ?, ?, ?);";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, relev.getMessaggio());
		pstm.setString(2, relev.getDescrizione());
		pstm.setInt(3, relev.getSensID());
		pstm.setTimestamp(4, relev.getData());
		
		pstm.executeUpdate();
	}

	public static ArrayList<Rilevazione> queryRilevazioni(Connection conn, String sensID) throws SQLException, NullException, ZeroException {
		
		String sql = "SELECT r.ID, r.Messaggio, r.Descrizione, r.Data FROM Rilevazione r JOIN Sensore s WHERE s.Cod = r.Sensore AND s.Cod = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, sensID);
		
		ResultSet rs = pstm.executeQuery();
		
		ArrayList<Rilevazione> rilevazioni = new ArrayList<Rilevazione>();
		
		while(rs.next()) {
			
			int id = rs.getInt("ID");
			String msg = rs.getString("Messaggio");
			String descr = rs.getString("Descrizione");
			Timestamp data = rs.getTimestamp("Data");
			
			Rilevazione rilevazione = new Rilevazione();
			
			rilevazione.setId(id);
			rilevazione.setMessaggio(msg);
			rilevazione.setDescrizione(descr);
			rilevazione.setData(data);
			
			rilevazioni.add(rilevazione);
		}
		return rilevazioni;
	}

	public static Rilevazione findRilevazione(Connection conn, String Id) throws SQLException, ZeroException, NullException {
	
		String sql = "SELECT r.ID, r.Messaggio. r.Descrizione FROM Rilevazioni WHERE r.Id = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, Id);
	
		ResultSet rs = pstm.executeQuery();
	
		while(rs.next()) {
		
			String msg = rs.getString("Messaggio");
			String descr = rs.getString("Descrizione");
		
			Rilevazione Rilevazione = new Rilevazione(msg, descr);
		
			return Rilevazione;
		}
		return null;
	}

	public static ArrayList<Rilevazione> querySintesi(Connection conn, String ambID) throws SQLException, ZeroException, NullException {

		String sql = "SELECT s.Modello, s.Marca, r.Messaggio, r.Descrizione, r.Data FROM Rilevazione r JOIN Sensore s JOIN Ambiente a WHERE r.Sensore = s.Cod AND a.ID = s.Ambiente AND a.ID = ? ORDER BY r.Data;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setString(1, ambID);
		
		ResultSet rs = pstm.executeQuery();
		
		ArrayList<Rilevazione> rilevazioni = new ArrayList<Rilevazione>();
		
		while(rs.next()) {
			
			String marca = rs.getString("Marca");
			String mod = rs.getString("Modello");
			String msg = rs.getString("Messaggio");
			String descr = rs.getString("Descrizione");
			Timestamp data = rs.getTimestamp("Data");
			
			Rilevazione rilevazione = new Rilevazione();

			rilevazione.setModello(marca);
			rilevazione.setMarca(mod);
			rilevazione.setMessaggio(msg);
			rilevazione.setDescrizione(descr);
			rilevazione.setData(data);
			
			rilevazioni.add(rilevazione);
		}
		return rilevazioni;
	}
	
	public static ArrayList<UserAccount> queryUtenti(Connection conn) throws SQLException, ZeroException, NullException {

		String sql = "SELECT DISTINCT Nome_Utente, Privilegi FROM utente;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		ResultSet rs = pstm.executeQuery();
		
		ArrayList<UserAccount> utenti = new ArrayList<UserAccount>();
		
		while(rs.next()) {
			
			String username = rs.getString("Nome_Utente");
			int privilegio = rs.getInt("Privilegi");
			
			UserAccount utente = new UserAccount();

			utente.setUserName(username);
			utente.setPrivilegi(privilegio);
			
			utenti.add(utente);
		}
		return utenti;
	}
	
	public static void incrementSens(Connection conn, Ambiente amb, int id) throws SQLException {
		
		String sql = "UPDATE Ambiente SET NumeroSensori = ? WHERE ID = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setInt(1, (amb.getNumeroSensori() + 1));
		pstm.setInt(2, id);
		
		pstm.executeUpdate();
	}
	
	public static void decrementSens(Connection conn, Ambiente amb, int id) throws SQLException {
		
		String sql = "UPDATE Ambiente SET NumeroSensori = ? WHERE ID = ?;";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		
		pstm.setInt(1,(amb.getNumeroSensori() - 1));
		pstm.setInt(2, id);
		
		pstm.executeUpdate();
	}
}
