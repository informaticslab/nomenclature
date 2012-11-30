/**
 * 
 */
package gov.cdc.irdu.athena.dao;

import gov.cdc.irdu.athena.domain.Diagnosis;

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

@Repository("diagnosisDAO")
public class DiagnosisDAO extends JpaDAO<Long, Diagnosis> {

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
	
	public Diagnosis findByName(String name) {
		@SuppressWarnings("unchecked")
		List<Diagnosis> results = getJpaTemplate().find("select d from Diagnosis d where d.name = ?1", name);
		
		if (results.size() == 0)
			return null;
		
		return results.get(0);
	}
	
}
