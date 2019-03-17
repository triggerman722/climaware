package com.climaware.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;


public final class SystemDataAccess {

    private SystemDataAccess() {
    }

    public static List getWithParams(String qlString, Object[][] values) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery(qlString);

        for (Object[] o : values) {
            q.setParameter((String) o[0], o[1]);
        }

        List lResult = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return lResult;

    }

    public static Object getOneWithParams(String qlString, Object[][] values) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery(qlString);

        for (Object[] o : values) {
            q.setParameter((String) o[0], o[1]);
        }

        q.setFirstResult(0);
        q.setMaxResults(1);
        Object lResult = q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return lResult;
    }

    public static List getNativeWithParams(String qlString, Object[] values, Class entityClass) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery(qlString, entityClass);

        for (int i = 0; i < values.length; i++) {
            q.setParameter(i + 1, values[i]);
        }

        List lResult = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return lResult;

    }

    public static List getNativeWithParams(String qlString, Object[] values, String mappings) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery(qlString, mappings);

        for (int i = 0; i < values.length; i++) {
            q.setParameter(i + 1, values[i]);
        }

        List lResult = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return lResult;

    }

    public static List getNativeWithParams(String qlString, Object[] values) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery(qlString);

        for (int i = 0; i < values.length; i++) {
            q.setParameter(i + 1, values[i]);
        }

        List lResult = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return lResult;

    }

    public static int getNativeSingleWithParams(String qlString, Object[] values) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createNativeQuery(qlString);

        for (int i = 0; i < values.length; i++) {
            q.setParameter(i + 1, values[i]);
        }

        int lResult = q.executeUpdate();
        em.getTransaction().commit();
        em.close();
        return lResult;

    }
    public static List getAll(String qlString) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        List lResult = em.createQuery(qlString).getResultList();
        em.getTransaction().commit();
        em.close();
        return lResult;
    }


    public static List getAllPaged(String qlString, int offset, int pagesize) {

        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        List lResult = em.createQuery(qlString).setFirstResult(offset).setMaxResults(pagesize).getResultList();
        em.getTransaction().commit();
        em.close();
        return lResult;
    }
    public static Object get(Class entityClass, Object primaryKey) {
        EntityManager em = EMF.get().createEntityManager();
        Object oResult = em.find(entityClass, primaryKey);
        em.close();
        return oResult;
    }

    public static Object add(Object p) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        em.persist(p);
        em.flush();

        em.getTransaction().commit();
        em.refresh(p);
        em.close();
        return p;
    }

    public static void batchAdd(List objects) {
        EntityManager em = EMF.get().createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();

        for (int i = 0; i < objects.size(); i++) {
            em.persist(objects.get(i));
            if (i % 20 == 0) {
                em.flush();
                em.clear();
            }
        }
        entityTransaction.commit();
        em.close();
    }

    public static Object set(Class entityClass, Object primaryKey, Object objectToMerge) {
        EntityManager em = EMF.get().createEntityManager();
        Object pOrig = em.find(entityClass, primaryKey);
        if (pOrig != null) {
            em.getTransaction().begin();
            em.merge(objectToMerge);
            em.flush();
            em.refresh(objectToMerge);
            em.getTransaction().commit();
            em.close();
        }
        return objectToMerge;
    }

    public static void delete(Class entityClass, Object primaryKey) {
        EntityManager em = EMF.get().createEntityManager();
        Object pOrig = em.find(entityClass, primaryKey);
        //if the item is found, it may be deleted.
        if (pOrig != null) {
            em.getTransaction().begin();
            em.remove(pOrig);
            em.getTransaction().commit();
            em.close();
        }
    }

    public static int deleteAll(String qlString) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        int lResult = em.createQuery(qlString).executeUpdate();
        em.getTransaction().commit();
        em.close();
        return lResult;
    }

    public static int deleteWithParams(String qlString, Object[][] values) {
        EntityManager em = EMF.get().createEntityManager();
        em.getTransaction().begin();
        Query q = em.createQuery(qlString);

        for (Object[] o : values) {
            q.setParameter((String) o[0], o[1]);
        }

        int lResult = q.executeUpdate();
        em.getTransaction().commit();
        em.close();
        return lResult;

    }

}
