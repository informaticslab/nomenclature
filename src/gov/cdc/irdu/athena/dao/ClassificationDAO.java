/**
 * 
 */
package gov.cdc.irdu.athena.dao;

import gov.cdc.irdu.athena.domain.Classification;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Joel M. Rives
 * Mar 22, 2011
 */
@Repository("classificationDAO")
public class ClassificationDAO extends JpaDAO<Long, Classification> {

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PostConstruct
	public void init() {
		super.setEntityManagerFactory(entityManagerFactory);
	}

	public List<Classification> findByType(Integer type) {
		@SuppressWarnings("unchecked")
		List<Classification> results = getJpaTemplate().find("select c from Classification c where c.type = ?1", type);
		return results;
	}
		
}
