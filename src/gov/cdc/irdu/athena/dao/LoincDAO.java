/**
 * 
 */
package gov.cdc.irdu.athena.dao;

import gov.cdc.irdu.athena.domain.Loinc;

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

@Repository("loincDAO")
public class LoincDAO extends JpaDAO<Long, Loinc> {

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
	
	public Loinc findByCode(String code) {
		@SuppressWarnings("unchecked")
		List<Loinc> results = getJpaTemplate().find("select l from Loinc where l.code = ?1", code);
		
		if (results.size() == 0)
			return null;
		
		return results.get(0);
	}
	
	public List<Loinc> findByClassification(String name) {
		@SuppressWarnings("unchecked")
		List<Loinc> results = getJpaTemplate().find("select l from Loinc l where l.classification = ?1", name);
		return results;
	}
		
}
