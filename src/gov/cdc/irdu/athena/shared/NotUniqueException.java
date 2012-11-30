/**
 * 
 */
package gov.cdc.irdu.athena.shared;

/**
 *
 * @author Joel M. Rives
 * Mar 30, 2011
 */
public class NotUniqueException extends RuntimeException {

	private static final long serialVersionUID = 6933559319143450105L;

	public NotUniqueException(String field) {
		super("The value of " + field + " must be unique");
	}

	
}
