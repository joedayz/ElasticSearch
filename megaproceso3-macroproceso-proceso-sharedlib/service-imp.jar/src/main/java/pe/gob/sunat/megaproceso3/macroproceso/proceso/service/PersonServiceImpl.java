package pe.gob.sunat.megaproceso3.macroproceso.proceso.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.Person;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.PersonDAO;

@Stateless
@Profiled
public class PersonServiceImpl implements PersonService {

	private static final Logger logger = Logger.getLogger(PersonServiceImpl.class.getName());

	@Inject
	private PersonDAO personDAO;

	@Override
	public Person addPerson(Person person) {
		logger.log(Level.INFO, "Adding person: {0}", person);

		return personDAO.addPerson(person);
	}

	@Override
	public Person getPerson(Long id) {
		logger.log(Level.INFO, "Getting person: {0}", id);

		return personDAO.getPerson(id);
	}

	@Override
	public Person updatePerson(Person person) {
		logger.log(Level.INFO, "Updating person: {0}", person);

		return personDAO.updatePerson(person);
	}

	@Override
	public void deletePerson(Long id) {
		logger.log(Level.INFO, "Deleting person: {0}", id);

		personDAO.deletePerson(id);
	}

	@Override
	public List<Person> findPersons(int startPosition, int maxResults, String sortFields, String sortDirections) {
		logger.log(Level.INFO,
				"Getting persons with startPosition="+ startPosition +", maxResults="+ maxResults+", "
						+ "sortFields="+ sortFields+", sortDirections=" + sortDirections);
		return personDAO.findPersons(startPosition, maxResults, sortFields, sortDirections);
	}

	@Override
	public Integer countPersons() {
		logger.log(Level.INFO, "Count persons...");
		return personDAO.countPersons();
	}
}
