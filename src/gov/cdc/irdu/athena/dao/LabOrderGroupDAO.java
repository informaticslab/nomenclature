/**
 * 
 */
package gov.cdc.irdu.athena.dao;

import gov.cdc.irdu.athena.domain.LabOrderGroup;

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

@Repository("labOrderGroupDAO")
public class LabOrderGroupDAO extends JpaDAO<Long, LabOrderGroup> {

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
	
	public LabOrderGroup findByCode(String code) {
		@SuppressWarnings("unchecked")
		List<LabOrderGroup> results = getJpaTemplate().find("select l from LabOrderGroup l where l.code = ?1", code);		
		
		if (results.size() == 0)
			return null;
		
		return results.get(0);
	}
	
	public List<String> findCodesThatBeginWith(String key) {
		@SuppressWarnings("unchecked")
		List<String> results = getJpaTemplate().find("select l.code from LabOrderGroup l where l.code like ?1", key + "%");		
		return results;
	}
	
	public List<String> getAllCodes() {
		@SuppressWarnings("unchecked")
		List<String> results = getJpaTemplate().find("select code from LabOrderGroup");
		return results;
	}
}
