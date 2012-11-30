/**
 * 
 */
package gov.cdc.irdu.athena.dao;

import gov.cdc.irdu.athena.domain.ServiceFacility;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Joel M. Rives
 * Mar 21, 2011
 */

@Repository("serviceFacilityDAO")
public class ServiceFacilityDAO extends JpaDAO<Long, ServiceFacility> {

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
	
	public ServiceFacility findByName(String name) {
		@SuppressWarnings("unchecked")
		List<ServiceFacility> results = getJpaTemplate().find("select l from ServiceFacility l where l.name = ?1", name);		
		
		if (results.size() == 0)
			return null;
		
		return results.get(0);
	}
}
