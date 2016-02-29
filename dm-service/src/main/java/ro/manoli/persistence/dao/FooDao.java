package ro.manoli.persistence.dao;

import ro.manoli.persistence.model.Foo;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Mihail
 *
 */
@Repository
public class FooDao extends AbstractJpaDAO<Foo> implements IFooDao {

    public FooDao() {
        super();
        setClazz(Foo.class);
    }
    // API
}
