package pe.gob.sunat.megaproceso3.macroproceso.proceso.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.bean.PaginatedListWrapper;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.Person;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.service.PersonService;

@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SecurePersonServiceRest {
	@Inject
	private PersonService personService;

	private Integer countPersons() {
		return personService.countPersons();
	}
	
    
    private List<Person> findPersons(int startPosition, int maxResults, String sortFields, String sortDirections) {
        return personService.findPersons(startPosition, maxResults, sortFields, sortDirections);
    }
    
    private PaginatedListWrapper findPersons(PaginatedListWrapper wrapper) {
        wrapper.setTotalResults(countPersons());
        int start = (wrapper.getCurrentPage() - 1) * wrapper.getPageSize();
        wrapper.setList(findPersons(start,
                                    wrapper.getPageSize(),
                                    wrapper.getSortFields(),
                                    wrapper.getSortDirections()));
        return wrapper;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PaginatedListWrapper listPersons(@DefaultValue("1")
                                            @QueryParam("page")
                                            Integer page,
                                            @DefaultValue("id")
                                            @QueryParam("sortFields")
                                            String sortFields,
                                            @DefaultValue("asc")
                                            @QueryParam("sortDirections")
                                            String sortDirections){
        PaginatedListWrapper paginatedListWrapper = new PaginatedListWrapper();
        paginatedListWrapper.setCurrentPage(page);
        paginatedListWrapper.setSortFields(sortFields);
        paginatedListWrapper.setSortDirections(sortDirections);
        paginatedListWrapper.setPageSize(10);
        return findPersons(paginatedListWrapper);
    }


    @GET
    @Path("{id}")
    public Person getPerson(@PathParam("id") Long id)  {
        return personService.getPerson(id);
    }
    
    @POST
    public Person savePerson(Person person) {
        if (person.getId() == null) {
            person = personService.addPerson(person);
        } else {
            person = personService.updatePerson(person);
        }

        return person;
    }
    
    
    @DELETE
    @Path("{id}")
    public void deletePerson(@PathParam("id") Long id) {
        personService.deletePerson(id);
    }
}
