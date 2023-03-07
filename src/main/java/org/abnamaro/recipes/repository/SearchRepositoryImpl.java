package org.abnamaro.recipes.repository;


import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Transactional
public class SearchRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements SearchRepository<T, ID> {

    private final EntityManager entityManager;

    public SearchRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
    }

    public SearchRepositoryImpl(
            JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> searchByContainsText(String text, String[] fields) {

        SearchResult<T> result = getSearchContainsTextResult(text, fields);

        return result.hits();
    }

    @Override
    public List<T> searchByNotContainsText(String text, String[] excludeFields, String[] fields) {

        SearchResult<T> result = getSearchNotContainsTextResult(text, excludeFields, fields);

        return result.hits();
    }

    private SearchResult<T> getSearchContainsTextResult(String text, String[] fields) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<T> result =
                searchSession
                        .search(getDomainClass())
                        .where(f -> f.bool()
                                .must(f.match().fields(fields).matching(text)))
                        .fetchAll();

        return result;
    }

    private SearchResult<T> getSearchNotContainsTextResult(String text, String[] excludeFields, String[] fields) {
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<T> result =
                searchSession
                        .search(getDomainClass())
                        .where(f -> f.bool()
                                .mustNot(f.match().fields(excludeFields).matching(text))
                                .must(f.match().fields(fields).matching(text)))
                        .fetchAll();
        return result;
    }

}
