package ds2;

import java.time.LocalDateTime;

public class Reservation {
	
	public enum Payment {cash, card, online_card, bank_order;
		
		public static Payment byInt(int x) {
	        switch(x) {
	        case 0:
	            return cash;
	        case 1:
	            return card;
	        case 2:
	            return online_card;
	        case 3:
	            return bank_order;
	        }
	        return null;
	    }	
	}
	
	private int id;
	private int User_id;
	private LocalDateTime RT_Datetime;
	private Vehicle vehicle;
	private String task;
	private boolean paid;
	
	private String taskDescription;
	private double discount;
	private double bill;
	private Payment payment;
	
	private Complaint complaint;
	private Review review;
	
	public int getId() {
		return this.id;
	}
	
	public int getUserId() {
		return this.User_id;
	}
	
	public LocalDateTime getRT_Datetime() {
		return this.RT_Datetime;
	}
	
	public Vehicle getVehicle() {
		return this.vehicle;
	}
	
	public String getTask() {
		return this.task;
	}
	
	public boolean getPaid() {
		return this.paid;
	}
	
	public String getTaskDescription() {
		return this.taskDescription;
	}
	
	public double getDiscount() {
		return this.discount;
	}
	
	public double getBill() {
		return this.bill;
	}
	
	public Payment getPayment() {
		return this.payment;
	}
	
	public Complaint getComplaint() {
		return this.complaint;
	}
	
	public Review getReview() {
		return this.review;
	}
	
	public Reservation() {};

	private Reservation (ReservationBuilder builder) {
		this.id = builder.id;
		this.User_id = builder.User_id;
		this.RT_Datetime = builder.RT_Datetime;
		this.vehicle = builder.vehicle;
		this.task = builder.task;
		this.paid = builder.paid;
		this.taskDescription = builder.taskDescription;
		this.discount = builder.discount;
		this.bill = builder.bill;
		this.payment = builder.payment;
		this.complaint = builder.complaint;
		this.review = builder.review;
	}
	
	public static class ReservationBuilder {
		private int id;
		private int User_id;
		private LocalDateTime RT_Datetime;
		private Vehicle vehicle;
		private String task;
		private boolean paid;
		
		private String taskDescription;
		private double discount;
		private double bill;
		private Payment payment;
		private Complaint complaint;
		private Review review;
		
		public ReservationBuilder(int id, int User_id, LocalDateTime RT_Datetime,
				Vehicle vehicle, String task, boolean paid) {
			this.id = id;
			this.User_id = User_id;
			this.RT_Datetime = RT_Datetime;
			this.vehicle = vehicle;
			this.task = task;
			this.paid = paid;
		}
		
		public ReservationBuilder taskDescription(String taskDescription) {
			this.taskDescription = taskDescription;
			return this;
		}
		
		public ReservationBuilder discount(double discount) {
			this.discount = discount;
			return this;
		}
		
		public ReservationBuilder bill(double bill) {
			this.bill = bill;
			return this;
		}
		
		public ReservationBuilder payment(Payment payment) {
			this.payment = payment;
			return this;
		}
		
		public ReservationBuilder complaint(Complaint complaint) {
			this.complaint = complaint;
			return this;
		}
		
		public ReservationBuilder review(Review review) {
			this.review = review;
			return this;
		}
		
		public Reservation build() {
			Reservation reservation = new Reservation(this);
			return reservation;
		}
	}
}
