package pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.jpa;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.Person;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.model.dao.PersonDAO;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.service.Profiled;
import pe.gob.sunat.megaproceso3.macroproceso.proceso.service.ProfilingInterceptor;

@RunWith(Arquillian.class)
public class NativeSqlTest {
	
	@Inject
	PersonDAO dao;

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "testSharedlib.war")
				.addClasses(PersonDAO.class, JPAPersonDAO.class, Person.class, Profiled.class,
						ProfilingInterceptor.class)
				.addPackages(true, "org.apache.log4j")
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")			
				.addAsWebInfResource("test-beans.xml", "beans.xml");
				
	}

	/**
	 * In the test, we're just going to invoke the only available operation in
	 * the +EmployeeBean+ and assert a few details to confirm that the native
	 * query was successfully executed.
	 */
	@Test
	public void testNativeSql() {
		
		int result = dao.countPersons();
		assertTrue(result > 0);
	}
}
