package ds2;

import java.time.LocalDate;

public class Reply {
	private int res_id;
	private String reply;
	private LocalDate replyDate;
	
	public Reply () {}
	
	public Reply (String reply, LocalDate replyDate, int res_id) {
		this.res_id = res_id;
		this.reply = reply;
		this.replyDate = replyDate;
	}
	
	public int getRes_id() {
		return this.res_id;
	}
	
	public String getReply() {
		return this.reply;
	}
	
	public LocalDate getReplyDate() {
		return this.replyDate;
	}
}
