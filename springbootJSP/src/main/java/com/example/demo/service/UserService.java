package com.example.demo.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.demo.model.FeedbackBo;
import com.example.demo.model.UserRegister;


@Service
public interface UserService {
	public void addPerson(UserRegister p);

	public void updatePerson(UserRegister p);

	public List<UserRegister> listUsers();

	public UserRegister getPersonByEmail(UserRegister userRegister, HttpSession session);

	public UserRegister getUserById(UserRegister userRegister);

	public FeedbackBo addFeedback(FeedbackBo feedbackBo);
}
