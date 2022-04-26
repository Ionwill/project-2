package com.revature.modals;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="transactions")
public class CustomerTransaction {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int transactionID;
	
	@OneToOne
	@JoinColumn(nullable=false, name="ninja_id")
	private Ninja itemBought;
	
	@ManyToOne
	@JoinColumn(nullable = false, name="user_id")
	private Users user;


	public int getTransactionID() {
		return transactionID;
	}


	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}


	public Ninja getItemBought() {
		return itemBought;
	}


	public void setItemBought(Ninja itemBought) {
		this.itemBought = itemBought;
	}


	public Users getUser() {
		return user;
	}


	public void setUserID(Users user) {
		this.user = user;
	}


	@Override
	public int hashCode() {
		return Objects.hash(itemBought, transactionID, user);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerTransaction other = (CustomerTransaction) obj;
		return Objects.equals(itemBought, other.itemBought) && transactionID == other.transactionID
				&& Objects.equals(user, other.user);
	}


	@Override
	public String toString() {
		return "CustomerTransaction [transactionID=" + transactionID + ", itemBought=" + itemBought + ", user=" + user
				+ "]";
	}
	
	
}
