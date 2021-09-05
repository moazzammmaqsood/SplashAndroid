package com.example.splash.Api.modal.vendor;


public class SummaryDaily {
	
 	int id;
	
 	int houses;
	
 	int bottlesdelivered;
	
 	int revenue;
	
  	int bottlesrecieved;
	
 	int payment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBottlesdelivered() {
		return bottlesdelivered;
	}

	public void setBottlesdelivered(int bottlesdelivered) {
		this.bottlesdelivered = bottlesdelivered;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public int getBottlesrecieved() {
		return bottlesrecieved;
	}

	public void setBottlesrecieved(int bottlesrecieved) {
		this.bottlesrecieved = bottlesrecieved;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
	}

	
	
	public int getHouses() {
		return houses;
	}

	public void setHouses(int houses) {
		this.houses = houses;
	}




	public SummaryDaily(int id, int houses, int bottlesdelivered, int revenue, int bottlesrecieved, int payment) {
		super();
		this.id = id;
		this.houses = houses;
		this.bottlesdelivered = bottlesdelivered;
		this.revenue = revenue;
		this.bottlesrecieved = bottlesrecieved;
		this.payment = payment;
	}

	public SummaryDaily() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SummaryDaily [id=" + id + ", houses=" + houses + ", bottlesdelivered=" + bottlesdelivered + ", revenue="
				+ revenue + ", bottlesrecieved=" + bottlesrecieved + ", payment=" + payment + "]";
	}
	

	

	
	
	

}
