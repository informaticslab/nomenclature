/**
 * 
 */
package gov.cdc.irdu.athena.shared;

/**
 *
 * @author Joel M. Rives
 * Mar 30, 2011
 */
public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 6933559319143450105L;

	public AlreadyExistsException(String entity) {
		super("The specified entity " + entity + " already exists");
	}

	
}
