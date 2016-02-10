package hospitalmgmt.dao;

import hospitalmgmt.beans.MTPRegisterDetails;
import hospitalmgmt.utils.HibernateUtil;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class MTPRegisterDetailsDAO {

	@SuppressWarnings("unchecked")
	public List<MTPRegisterDetails> findByMonth(String month, String year) throws Exception {
		Session session = null;
		List<MTPRegisterDetails> mtpRegisterDetails = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery("from MTPRegisterDetails mrd where date_format(mrd.mtpRegister.operationDate, '%b') = :month and date_format(mrd.mtpRegister.operationDate, '%Y') = :year order by mrd.mtpRegister.operationDate asc");
			query.setParameter("month", month);
			query.setParameter("year", year);

			mtpRegisterDetails = query.list();	
		} catch (Exception e) {
			throw e;
		} finally {
			if(session != null)
				session.close();
		}
		
		return mtpRegisterDetails;
	}
	
	public void save(MTPRegisterDetails mtpRegisterDetails) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.beginTransaction();
			session.save(mtpRegisterDetails);
			session.getTransaction().commit();
		} catch(Exception e) {
			throw e;
		} finally {
			if(session != null)
			session.close();
		}
	}
}
