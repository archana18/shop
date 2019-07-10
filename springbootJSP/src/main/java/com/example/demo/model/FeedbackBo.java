package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Entity
@Table(name = "feedback")
public class FeedbackBo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "emailId")
	private String emailId;
	@Column(name = "message")
	private String message;

	public FeedbackBo(int id, String emailId, String message) {
		super();
		this.id = id;
		this.emailId = emailId;
		this.message = message;
	}

	@Override
	public String toString() {
		return "FeedbackBo [id=" + id + ", emailId=" + emailId + ", message=" + message + "]";
	}

	public FeedbackBo() {
	}
}
