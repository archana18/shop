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
@Table(name = "OrderDetails")
public class OrderDetails {
	@Id
	@Column(name = "OrderId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int orderId;
	@Column(name = "amount")
	private double amount;
	@Column(name = "date")
	private String date;
	@Column(name = "userId")
	private int userId;

	public OrderDetails(int orderId, double amount, String date, int userId) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.date = date;
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "OrderDetails [orderId=" + orderId + ", amount=" + amount + ", date=" + date + ", userId=" + userId
				+ "]";
	}

	public OrderDetails() {
	}

}
