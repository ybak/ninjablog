package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Article;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseDao<T> {

    @Inject
    protected Provider<EntityManager> emProvider;

    public BaseDao() {
    }

    protected EntityManager em() {
        return emProvider.get();
    }

    protected TypedQuery<T> createQuery(String qlString, Class<T> clazz) {
        return em().createQuery(qlString, clazz);
    }

    protected T first(TypedQuery<T> query) {
        List<T> list = query.getResultList();
        return list.size() > 0 ? list.get(0) : null;
    }

    protected List<T> subResult(TypedQuery<T> q, int start, int max) {
        return q.setFirstResult(start).setMaxResults(max).getResultList();
    }

}