package gov.cdc.irdu.athena.domain;

import javax.persistence.Entity;

@Entity
public class RelatedName extends DomainObject {

	private static final long serialVersionUID = 8076945284465567339L;

	private String name;

	public String getName() {
        return this.name;
    }

	public void setName(String name) {
        this.name = name;
    }

}
