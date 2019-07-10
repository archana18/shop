package com.example.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Entity
@Table(name = "userDetails", uniqueConstraints = { @UniqueConstraint(columnNames = "userId") })
@Setter
@Getter
@ToString
public class UserData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private Integer userId;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "emailId")
	private String emailId;

	@Column(name = "gender")
	private String gender;

	@Column(name = "contactNumber")
	private String contactNumber;

	@Column(name = "password")
	private String password;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn(name = "userId")
	private Address address;

	public UserData(String firstName, String lastName, String emailId, String gender, String contactNumber,
			String password, Address address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.gender = gender;
		this.contactNumber = contactNumber;
		this.password = password;
		this.address = address;
	}

	public UserData() {

	}

}
