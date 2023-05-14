package ds2;

import java.time.LocalDate;

public class Review {

	private int Reservation_id;
	private int User_id;
	private String userFirstName;
	private LocalDate reviewDate;
	private LocalDate lastEdit;
	private int reviewPts;
	private String reviewText;
	
	public Review() {}
	
	public int getReservation_id() {
		return this.Reservation_id;
	}
	
	public int getUser_id() {
		return this.User_id;
	}
	
	public String getUserFirstName() {
		return this.userFirstName;
	}
	
	public LocalDate getReviewDate() {
		return this.reviewDate;
	}
	
	public LocalDate getLastEdit() {
		return this.lastEdit;
	}
	
	public int getReviewPts() {
		return this.reviewPts;
	}
	
	public String getReviewText() {
		return this.reviewText;
	}
	
	private Review(ReviewBuilder builder) {
		this.Reservation_id = builder.Reservation_id;
		this.User_id = builder.User_id;
		this.userFirstName = builder.userFirstName;
		this.reviewDate = builder.reviewDate;
		this.lastEdit = builder.lastEdit;
		this.reviewPts = builder.reviewPts;
		this.reviewText = builder.reviewText;
	}
	
	public static class ReviewBuilder {
		private int Reservation_id;
		private int User_id;
		private String userFirstName;
		private LocalDate reviewDate;		
		private int reviewPts;
		
		private String reviewText;
		private LocalDate lastEdit;
		
		public ReviewBuilder(int Reservation_id, int User_id, String userFirstName, LocalDate reviewDate, int reviewPts) {
			this.Reservation_id = Reservation_id;
			this.User_id = User_id;
			this.userFirstName = userFirstName;
			this.reviewDate = reviewDate;
			this.reviewPts = reviewPts;
		}
		
		public ReviewBuilder reviewText(String reviewText) {
			this.reviewText = reviewText;
			return this;
		}
		
		public ReviewBuilder lastEdit(LocalDate lastEdit) {
			this.lastEdit = lastEdit;
			return this;
		}
		
		public Review build() {
			Review review = new Review(this);
			return review;
		}
	}
	
}
