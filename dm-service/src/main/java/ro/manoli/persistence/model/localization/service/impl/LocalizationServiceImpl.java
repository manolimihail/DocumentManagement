package ro.manoli.persistence.model.localization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ro.manoli.persistence.model.localization.Country;
import ro.manoli.persistence.model.localization.repository.CountryRepository;
import ro.manoli.persistence.model.localization.service.LocalizationService;

/**
 * 
 * @author Mihail
 *
 */
public class LocalizationServiceImpl implements LocalizationService {

	@Autowired
	private CountryRepository countryRepository;
	
	@Override
	public Country saveCountry(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public List<Country> findAllCountries() {
		return countryRepository.findAll();
	}
}
