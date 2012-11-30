/**
 * 
 */
package gov.cdc.irdu.athena.dao;

import gov.cdc.irdu.athena.domain.LabOrder;

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

@Repository("labOrderDAO")
public class LabOrderDAO extends JpaDAO<Long, LabOrder> {

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}
	
	public List<String> findNamesThatBeginWith(String key) {
		@SuppressWarnings("unchecked")
		List<String> results = getJpaTemplate().find("select l.name from LabOrder l where l.name like ?1", key + "%");		
		return results;
	}
	
	public List<LabOrder> findByEquivalance(String code) {
		@SuppressWarnings("unchecked")
		List<LabOrder> results = getJpaTemplate().find("select l from LabOrder l where l.equivalanceCode = ?1", code);		
		return results;
	}
	
	public LabOrder findByName(String name) {
		@SuppressWarnings("unchecked")
		List<LabOrder> results = getJpaTemplate().find("select l from LabOrder l where l.name = ?1", name);		
		
		if (results.size() == 0)
			return null;
		
		return results.get(0);
	}
	
}
