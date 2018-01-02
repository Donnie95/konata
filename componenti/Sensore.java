package componenti;

import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;

import exceptions.NullException;
import exceptions.ZeroException;

/**
 * @author Donatello Plantone Matr. 635864 20/10/2017
 * 
 *         Classe rappresentante un Sensore
 */
public class Sensore implements Iterable<Rilevazione> {
	
	private static int count = 0;

	/**
	 * Numero identificativo del Sensore
	 */
	public int id;

	/**
	 * Tipologia del Sensore
	 */
	public String tipologia;

	/**
	 * Marca del Sensore
	 */
	public String marca;

	/**
	 * Modello del Sensore
	 */
	public String modello;

	/**
	 * Anno di Produzione
	 */
	public Date anno;
	
	public int ambientId;



	/**
	 * Lista delle Rilevazioni del singolo Sensore
	 */
	public ArrayList<Rilevazione> listaRilevazioni;

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public int getAmbientId() {
		return ambientId;
	}

	public void setAmbientId(int ambientId) {
		this.ambientId = ambientId;
	}


	/**
	 * Restituisce L'ID del Sensore
	 * 
	 * @return id del Sensore
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Memorizza l'id del Sensore
	 * 
	 * @param ID del Sensore
	 * 
	 * @throws ZeroException
	 *             Verificare che il numero immesso sia maggiore di zero
	 */
	public void setId(int id) throws ZeroException {
		
		if (id <= 0) {
			throw new ZeroException();
		}

		this.id = id;
	}

	/**
	 * Restituisce la Tipologia del Sensore
	 * 
	 * @return Tipologia del Sensore
	 */
	public String getTipo() {
		return this.tipologia;
	}

	/**
	 * Memorizza la Tipologia del Sensore
	 * 
	 * @param Tipologia
	 *            da memorizzare
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 * 
	 */
	public void setTipo(String Tipo) throws NullException {
		if ("".equals(Tipo))
			throw new NullException();

		this.tipologia = Tipo;
	}

	/**
	 * Restituisce la Marca del Sensore
	 * 
	 * @return Marca del Sensore
	 */
	public String getMarca() {
		return this.marca;
	}

	/**
	 * Memorizza la Marca del Sensore
	 * 
	 * @param Marca
	 *            da memorizzare
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 * 
	 */
	public void setMarca(String Marca) throws NullException {
		if ("".equals(Marca))
			throw new NullException();

		this.marca = Marca;
	}

	/**
	 * Restituisce Modello del Sensore
	 * 
	 * @return Modello del Sensore
	 */
	public String getModello() {
		return this.modello;
	}

	/**
	 * Memorizza il Modello del Sensore
	 * 
	 * @param Modello
	 *            da memorizzare
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 * 
	 */
	public void setModello(String Modello) throws NullException {
		if ("".equals(Modello))
			throw new NullException();

		this.modello = Modello;
	}

	/**
	 * Restituisce l'Anno di produzione del Sensore
	 * 
	 * @return Anno della produzione del Sensore
	 */
	public Date getAnno() {
		return this.anno;
	}

	/**
	 * Memorizza l'Anno di produzione del Sensore
	 * 
	 * @param anno
	 *            da memorizzare
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 * 
	 */
	public void setAnno(Date anno) throws NullException {
		if (anno == null)
			throw new NullException();

		this.anno = anno;
	}

	/**
	 * Costruttore della classe Sensore
	 * 
	 * @param Tipo
	 *            del Sensore
	 * 
	 * @param Marca
	 *            del Sensore
	 * 
	 * @param Modello
	 *            del Sensore
	 * 
	 * @param anno
	 *            di produzione del Sensore
	 * 
	 * @throws ZeroException
	 *             Verificare che il numero immesso sia maggiore di zero
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 */
	public Sensore(String Tipo, String Marca, String Modello, Date anno) throws NullException {
		if ("".equals(Tipo) || "".equals(Marca) || "".equals(Modello) || anno == null)
			throw new NullException();
		
		this.id = count++;
		this.tipologia = Tipo;
		this.marca = Marca;
		this.modello = Modello;
		this.anno = anno;
	}
	
	public Sensore(String Tipo, String Marca, String Modello, Date anno, int ambientId) throws NullException {
		if ("".equals(Tipo) || "".equals(Marca) || "".equals(Modello) || anno == null)
			throw new NullException();
		
		this.id = count++;
		this.tipologia = Tipo;
		this.marca = Marca;
		this.modello = Modello;
		this.anno = anno;
		this.ambientId = ambientId;
	}

	public Sensore() {
		this.id = count++;
	}

	/**
	 * Aggiungere la Rilevazione alla lista delle Rilevazioni
	 * 
	 * @param rilevazione
	 *            da aggiungere
	 * 
	 * @throws NullException
	 *             Verifica che la stringa inserita non sia vuota
	 */
	private void aggiungiRilevazione(Rilevazione rilevazione) throws NullException {
		if (rilevazione == null)
			throw new NullException();

		this.listaRilevazioni.add(rilevazione);
	}

	/**
	 * Restituisce un iteratore per ciclare sulla lista dei Rilevamenti appartenenti
	 * al Sensore
	 * 
	 * @return Iterator della lista
	 */
	@Override
	public Iterator<Rilevazione> iterator() {
		return this.listaRilevazioni.iterator();
	}

}