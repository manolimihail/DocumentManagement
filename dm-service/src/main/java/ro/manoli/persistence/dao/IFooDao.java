package ro.manoli.persistence.dao;

import java.util.List;

import ro.manoli.persistence.model.Foo;

/**
 * 
 * @author Mihail
 *
 */
public interface IFooDao {

    Foo findOne(long id);

    List<Foo> findAll();

    void create(Foo entity);

    Foo update(Foo entity);

    void delete(Foo entity);

    void deleteById(long entityId);

}
