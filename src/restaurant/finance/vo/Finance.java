package restaurant.finance.vo;

import java.io.Serializable;
import java.time.LocalDate;

public class Finance implements Serializable{
	
	private static int TOTAL_MONEY;
	private LocalDate date;
	private int amount;
	private String message;
	
	/**
	 * date의 시간으로는 현재시간을 넘겨준다
	 * @param amount 입,출금 되는 금액
	 * @param message 입,출금 메세지
	 */
	public Finance(int amount, String message) {
		this.date = LocalDate.now();
		this.amount = amount;
		this.message = message;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static int getTOTAL_MONEY() {
		return TOTAL_MONEY;
	}
	public static void setTOTAL_MONEY(int tOTAL_MONEY) {
		TOTAL_MONEY = tOTAL_MONEY;
	}
	@Override
	public String toString() {
		return "Finance [date=" + date + ", amount=" + amount +"원, 메세지=" + message + "]";
	}
	
}
