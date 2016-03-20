package ro.manoli.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ro.manoli.persistence.model.localization.service.LocalizationService;
import ro.manoli.persistence.model.localization.service.impl.LocalizationServiceImpl;

/**
 * Base class for spring configs.
 * 
 * @author Mihail
 *
 */
@Configuration
@EnableJpaRepositories(basePackages = {"ro.manoli.**.repository"})
@EnableTransactionManagement
public class ApplicationConfig {
	
	@Bean
	public LocalizationService localizationService() {
		return new LocalizationServiceImpl();
	}
}
