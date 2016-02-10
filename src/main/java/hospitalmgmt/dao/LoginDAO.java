package hospitalmgmt.dao;

import hospitalmgmt.beans.Login;
import hospitalmgmt.utils.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;

public class LoginDAO {

	public boolean validate(String userName, String password) throws Exception {
		boolean isValid = false;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from Login where username = :username and password = :password");
			query.setParameter("username", userName);
			query.setParameter("password", password);
			
			Login login = (Login) query.list().get(0);
			if(login != null) 
				isValid = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(session != null) 
				session.close();
		}
		return isValid;
	}
}
