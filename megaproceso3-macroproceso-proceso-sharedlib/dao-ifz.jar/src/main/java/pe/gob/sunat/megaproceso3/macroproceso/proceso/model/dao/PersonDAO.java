package pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao;

import java.util.List;

import javax.ejb.Local;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.Person;

@Local
public interface PersonDAO {

	public Person addPerson(Person person);

	public Person updatePerson(Person person);

	public void deletePerson(Long id);

	public Person getPerson(Long id);

	public List<Person> findPersons(int startPosition, int maxResults, String sortFields, String sortDirections);
	
    public Integer countPersons();

}
