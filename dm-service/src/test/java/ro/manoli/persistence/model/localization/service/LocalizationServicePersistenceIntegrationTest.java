package ro.manoli.persistence.model.localization.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ro.manoli.config.BaseApplicationTest;
import ro.manoli.persistence.model.localization.Country;

/**
 * 
 * @author Mihail
 *
 */
public class LocalizationServicePersistenceIntegrationTest extends BaseApplicationTest {

	@Autowired
	private LocalizationService localizationService;

	@Test
	public void contextIsInitiated() {
		
	}
	
	@Test
	public void createCountry() {
		Country country = new Country();
		country.setName("Moldova");
		localizationService.saveCountry(country);
		
		List<Country> allCountries = localizationService.findAllCountries();
		Assert.assertNotNull(allCountries);
		Assert.assertEquals(1, allCountries.size());
	}
}
