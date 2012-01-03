package fichier;
/**
 *  FluxReader se charge d'extraire des documents contenus dans un ou plusieurs fichiers
 * @author fingon
 *
 */
public interface FluxReader {

	/**
	 * 
	 * @return the next file's document
	 */
	public String nextDocument();

	public boolean hasNextDocument();

}
