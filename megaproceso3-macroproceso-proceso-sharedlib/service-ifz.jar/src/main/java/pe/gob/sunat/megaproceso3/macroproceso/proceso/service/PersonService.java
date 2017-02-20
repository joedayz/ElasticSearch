package pe.gob.sunat.megaproceso3.macroproceso.proceso.service;

import java.util.List;

import javax.ejb.Local;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.Person;


@Local
public interface PersonService {

    public Person addPerson(Person person);

    public Person getPerson(Long id);

    public Person updatePerson(Person person);

    public void deletePerson(Long id);
    
    public List<Person> findPersons(int startPosition, int maxResults, String sortFields, String sortDirections);
    
    public Integer countPersons();
}
