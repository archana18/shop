package com.example.demo.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Address;
import com.example.demo.model.FeedbackBo;
import com.example.demo.model.UserData;
import com.example.demo.model.UserRegister;


@Repository("userdao")
public class UserDAOImpl {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	UserRegister userRegister = null;

	@SuppressWarnings("unchecked")
	public List<UserRegister> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<UserRegister> userList = session.createQuery("from user").list();
		for (UserRegister p : userList) {
			logger.info("User List::" + p);
		}
		return userList;
	}

	public void addPerson(UserRegister p) {
		Session session = this.sessionFactory.getCurrentSession();
		Address address = new Address(p.getStreet(), p.getCity(), p.getState(), p.getZipcode());
		UserData userData = new UserData(p.getFirstName(), p.getLastName(), p.getEmailId(), p.getGender(),
				p.getContactNumber(), p.getPassword(), address);
		logger.info("Dao implementation entered:addPerson" + userData + address);
		session.persist(userData);
		logger.info("Both successfully saved");

		session.persist(p);
		session.save(p);

		logger.info("User Details saved successfully, Person Details=" + p);
	}

	public void updatePerson(UserRegister p) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(p);
		logger.info("Person updated successfully, Person Details=" + p);
	}

	@SuppressWarnings("unchecked")
	public UserRegister getDetails(UserRegister userRegister, HttpSession httpsession) {

		UserRegister userData = null;
		try {
			logger.info("Dao implementation entered:getDetails");

			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(UserRegister.class);
			criteria.add(Restrictions.eq("emailId", userRegister.getEmailId()));
			criteria.add(Restrictions.eq("password", userRegister.getPassword()));
			List<UserRegister> userList = criteria.list();
			session.close();
			if (userList != null && !userList.isEmpty()) {
				logger.info("User data from DB:" + userList.get(0));

				httpsession.setAttribute("username", userList.get(0).getFirstName());
				httpsession.setAttribute("emailId", userList.get(0).getEmailId());
				logger.info("User data firstname:" + userList.get(0).getFirstName());
				return userList.get(0);
			} else {
				logger.info("User details not found in db");
				return userData;
			}
		}

		catch (Exception e) {
			logger.error("login validation not successfull");
			return userData;
		}
	}

	public UserRegister getUserById(UserRegister userRegister) {
		Session session = this.sessionFactory.getCurrentSession();

		logger.info("Dao implementation entered:getUserById");

		Address address = new Address(userRegister.getStreet(), userRegister.getCity(), userRegister.getState(),
				userRegister.getZipcode());
		UserData userData = new UserData(userRegister.getFirstName(), userRegister.getLastName(),
				userRegister.getEmailId(), userRegister.getGender(), userRegister.getContactNumber(),
				userRegister.getPassword(), address);
		session.merge(userData);
		session.merge(userRegister);
		logger.info("updated successfully");
		return userRegister;
	}

	public FeedbackBo saveFeedback(FeedbackBo feedbackBo) {
		logger.info("DAO feedback controller entered");
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(feedbackBo);
		return feedbackBo;
	}
}