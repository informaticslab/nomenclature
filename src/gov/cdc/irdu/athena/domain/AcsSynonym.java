/**
 * 
 */
package gov.cdc.irdu.athena.domain;

import javax.persistence.Entity;

/**
 *
 * @author Joel M. Rives
 * Mar 15, 2011
 */

@Entity
public class AcsSynonym extends DomainObject {
	
	private static final long serialVersionUID = -9144598611145752809L;

	private String name;

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

}
