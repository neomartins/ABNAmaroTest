package org.abnamaro.recipe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface SearchRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    List<T> searchByContainsText(String text, String[] fields);

    List<T> searchByNotContainsText(String text, String[] excludeFields, String[] fields);
}
