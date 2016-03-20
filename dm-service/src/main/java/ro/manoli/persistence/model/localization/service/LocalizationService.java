package ro.manoli.persistence.model.localization.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.manoli.persistence.model.localization.Country;

/**
 * 
 * @author Mihail
 *
 */
@Service
@Transactional
public interface LocalizationService {

	Country saveCountry(Country country);

	List<Country> findAllCountries();
}
