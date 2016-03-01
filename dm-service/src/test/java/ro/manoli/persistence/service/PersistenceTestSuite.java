package ro.manoli.persistence.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author Mihail
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({// @formatter:off
    FooPaginationPersistenceIntegrationTest.class,
    FooServicePersistenceIntegrationTest.class, 
    FooServiceSortingTests.class,
    FooServiceSortingWitNullsManualTest.class
})
public class PersistenceTestSuite {
}
