/**
 * 
 */
package gov.cdc.irdu.athena.shared;

/**
 *
 * @author Joel M. Rives
 * Mar 23, 2011
 */
public class ServiceFacilityDTO extends BaseDTO {

	private static final long serialVersionUID = -6595398699162142401L;

	private String name;
	
	public ServiceFacilityDTO() {
	}
	
	public ServiceFacilityDTO(Long id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
