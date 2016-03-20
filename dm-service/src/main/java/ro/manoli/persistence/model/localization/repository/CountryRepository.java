package ro.manoli.persistence.model.localization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ro.manoli.persistence.model.localization.Country;

/**
 * Repo used for country queries.
 * 
 * @author Mihail
 *
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
	
	Country findByName(String name);
}
