package ro.manoli.persistence.service;

import java.util.List;

import ro.manoli.persistence.dao.IFooDao;
import ro.manoli.persistence.model.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Mihail
 *
 */
@Service
@Transactional
public class FooService {

    @Autowired
    private IFooDao dao;

    public FooService() {
        super();
    }

    // API

    public void create(final Foo entity) {
        dao.create(entity);
    }

    public Foo findOne(final long id) {
        return dao.findOne(id);
    }

    public List<Foo> findAll() {
        return dao.findAll();
    }
}
