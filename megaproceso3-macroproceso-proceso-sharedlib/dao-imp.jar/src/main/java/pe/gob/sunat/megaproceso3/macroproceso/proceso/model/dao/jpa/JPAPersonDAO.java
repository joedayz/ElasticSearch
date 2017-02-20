package pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.Person;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.PersonDAO;

@Stateless
public class JPAPersonDAO implements PersonDAO {
	
	

	private static final Logger LOG = Logger.getLogger(JPAPersonDAO.class);

	@PersistenceContext(unitName = "sunat-unit")
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findPersons(int startPosition, int maxResults, String sortFields, String sortDirections) {

        LOG.warn("warn");
        LOG.info("info");
        LOG.trace("trace");
        LOG.debug("debug");
        LOG.error("error");
        LOG.fatal("fatal");
        
		
		
		return entityManager
				.createNativeQuery("select * from person order by name limit " + startPosition + "," + maxResults,
						Person.class)
				.getResultList();

	}


	@Override
	public Person addPerson(Person person) {
		entityManager.persist(person);
		return person;
	}

	@Override
	public Person getPerson(Long id) {
		return entityManager.find(Person.class, id);
	}

	@Override
	public Person updatePerson(Person person) {
		entityManager.merge(person);
		return person;
	}

	@Override
	public void deletePerson(Long id) {
		entityManager.remove(getPerson(id));
	}
	
	@Override
	public Integer countPersons() {
		return ((Number) entityManager.createNativeQuery("select count(*) from person").getSingleResult()).intValue();
	}
}
