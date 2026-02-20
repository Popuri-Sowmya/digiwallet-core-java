package com.example.digiwallet.model;
import java.math.BigDecimal;
import java.util.Objects;

public class User {
	private int id;
	private String name;
	private String email;
	private BigDecimal balance;
	
	public User() {
		
	}

	public User(int id, String name, String email, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (email == null) {
            if (other.email != null) {
                return false;
            }
        } else if (!email.equals(other.email)) {
            return false;
        }
        return true;
    }
	
	@Override
	public int hashCode() {
	    return Objects.hash(email);
	}
}
