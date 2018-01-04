package componenti;

import java.sql.Timestamp;

import exceptions.NullException;
import exceptions.ZeroException;

/**
 * @author Donatello Plantone Matr. 635864 20/10/2017
 * 
 *         Classe rappresentante una Rilevazione
 */
public class Rilevazione {
	
	private static int count = 0;

	/**
	 * numero identificativo della Rilevazione
	 */
	public int id;

	/**
	 * Messaggio della Rilevazione del Sensore
	 */
	public String messaggio;

	/**
	 * Descrizione del Messaggio della Rilevazione
	 */
	public String descrizione;
	/**
	 * Data della rilevazione
	 */
	public Timestamp data;
	/**
	 * id del sensore
	 */
	public int sensID;
	/**
	 * nome dell'ambiente della rilevazione
	 */
	public String nomeA;
	/**
	 * marca del sensore della rilevazione
	 */
	public String marca;
	/**
	 * modello del sensore della rilevazione
	 */
	public String modello;
	
	public Timestamp getData() {
		return data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public int getSensID() {
		return sensID;
	}

	public void setSensID(int sensID) {
		this.sensID = sensID;
	}

	public String getNomeA() {
		return nomeA;
	}

	public void setNomeA(String nomeA) {
		this.nomeA = nomeA;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) throws NullException {
		if ("".equals(messaggio))
			throw new NullException();
		
		this.marca = marca;
	}

	public String getModello() {
		return modello;
	}

	public void setModello(String modello) throws NullException {
		if ("".equals(messaggio))
			throw new NullException();
		
		this.modello = modello;
	}

	/**
	 * Restituisce L'ID della Rilevazione
	 * 
	 * @return id del Rilevazione
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Memorizza l'id della Rilevazione
	 * 
	 * @param ID
	 *            da memorizzare
	 * 
	 * @throws ZeroExceptio
	 *             Verificare che il numero immesso sia maggiore di zero
	 * 
	 */
	public void setId(int id) throws ZeroException {
		if (id <= 0)
			throw new ZeroException();

		this.id = id;
	}

	/**
	 * Restituisce il Messaggio correlato alla Rilevazione
	 * 
	 * @return Messaggio della rilevazione
	 */
	public String getMessaggio() {
		return this.messaggio;
	}

	/**
	 * Memorizza il Messaggio della Rilevazione
	 * 
	 * @param Messaggio
	 *            da memorizzare
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 * 
	 */
	public void setMessaggio(String messaggio) throws NullException {
		if ("".equals(messaggio))
			throw new NullException();
		this.messaggio = messaggio;
	}

	/**
	 * Restituisce la Descrizione del Messaggio della Rilevazione
	 * 
	 * @return Descrizione della Rilevazione
	 */
	public String getDescrizione() {
		return this.descrizione;
	}

	/**
	 * Memorizza la Descrizione del Messaggio della Rilevazione
	 * 
	 * @param Descrizione
	 *            da memorizzare
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 * 
	 */
	public void setDescrizione(String descrizione) throws NullException {
		if ("".equals(descrizione))
			throw new NullException();

		this.descrizione = descrizione;
	}

	/**
	 * Costruttore della classe Rilevazione
	 * 
	 * @param Messaggio
	 *            da Memorizzare della Rilevazione
	 * 
	 * @param Descrizione
	 *            della Rilevazione
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 * 
	 */
	public Rilevazione(String messaggio, String descrizione) throws NullException {
		if ("".equals(messaggio) || "".equals(descrizione))
			throw new NullException();

		this.id = count++;
		this.descrizione = descrizione;
		this.messaggio = messaggio;
	}

	public Rilevazione() {
		this.id = count++;
	}

}