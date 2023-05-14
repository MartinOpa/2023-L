package ds2;

import java.time.LocalDate;

public class Complaint {
	
	private int Reservation_id;
	private String complaint;
	private boolean resolved;
	private LocalDate complaintDate;
	
	public Complaint() {}
	
	public Complaint(int res_id, String complaint, boolean resolved, LocalDate complaintDate) {
		this.Reservation_id = res_id;
		this.complaint = complaint;
		this.resolved = resolved;
		this.complaintDate = complaintDate;
	}
	
	public int getReservation_id() {
		return this.Reservation_id;
	}
	
	public String getComplaint() {
		return this.complaint;
	}
	
	public boolean getResolved() {
		return this.resolved;
	}
	
	public LocalDate getComplaintDate() {
		return this.complaintDate;
	}
	
}
