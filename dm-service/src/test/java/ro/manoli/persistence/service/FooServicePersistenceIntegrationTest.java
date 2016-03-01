package ro.manoli.persistence.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ro.manoli.config.PersistenceConfig;
import ro.manoli.persistence.model.Foo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooServicePersistenceIntegrationTest {

    @Autowired
    private FooService service;

    // tests

    @Test
    public final void whenContextIsBootstrapped_thenNoExceptions() {
        //
    }

    @Test
    public final void whenEntityIsCreated_thenNoExceptions() {
        service.create(new Foo(randomAlphabetic(6), randomAlphabetic(5)));
    }

//    @Test(expected = DataIntegrityViolationException.class)
//    public final void whenInvalidEntityIsCreated_thenDataException() {
//        service.create(new Foo());
//    }

    @Test(expected = DataIntegrityViolationException.class)
    public final void whenEntityWithLongNameIsCreated_thenDataException() {
        service.create(new Foo(randomAlphabetic(2048), randomAlphabetic(5)));
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public final void whenSameEntityIsCreatedTwice_thenDataException() {
        final Foo entity = new Foo(randomAlphabetic(8), randomAlphabetic(5));
        service.create(entity);
        service.create(entity);
    }

//    @Test(expected = DataAccessException.class)
//    public final void temp_whenInvalidEntityIsCreated_thenDataException() {
//        service.create(new Foo());
//    }

    @Test
    public final void whenEntityIsCreated_thenFound() {
        final Foo fooEntity = new Foo("abc", "cde");
        service.create(fooEntity);
        final Foo found = service.findOne(fooEntity.getId());
        Assert.assertNotNull(found);
    }

}
