package com.climaware.persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;  


public final class SystemDataAccess {

    private SystemDataAccess() {}

	public static List getWithParams(String qlString, Object[][] values)
	{
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
	public static List getAll(String qlString)
    {
	    EntityManager em = EMF.get().createEntityManager();
   		em.getTransaction().begin();
	    List lResult= em.createQuery(qlString).getResultList();
		em.getTransaction().commit();
	    em.close();
	    return lResult;
    }
	public static Object get(Class entityClass, Object primaryKey)
    {
        EntityManager em = EMF.get().createEntityManager();
		Object oResult = em.find(entityClass, primaryKey);
		em.close();
		return oResult;
    }
    public static Object add(Object p)
    {
    	EntityManager em = EMF.get().createEntityManager();
		em.getTransaction().begin();
		em.persist(p);
		em.flush();
	   
		em.getTransaction().commit();
		em.refresh(p);
		em.close();
		return p;
    }
    public static Object set(Class entityClass, Object primaryKey, Object objectToMerge)
    {
	    EntityManager em = EMF.get().createEntityManager();
   		Object pOrig = em.find(entityClass, primaryKey);
   		if (pOrig != null)
   		{
	   		em.getTransaction().begin();
			em.merge(objectToMerge); 
			em.flush();
			em.refresh(objectToMerge);
			em.getTransaction().commit();
			em.close();
		}
		return objectToMerge;
	}
    public static void delete(Class entityClass, Object primaryKey)
    {
		EntityManager em = EMF.get().createEntityManager();
		Object pOrig = em.find(entityClass, primaryKey);
		//if the item is found, it may be deleted.
		if (pOrig != null)
		{
			em.getTransaction().begin();
			em.remove(pOrig); 
			em.getTransaction().commit();
			em.close();
		}
	}
}
