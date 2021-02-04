package restaurant.food.vo;
import java.io.Serializable;
import java.time.LocalDate;

import restaurant.refrigerator.dao.RestaurantRefrigeratorDaoImpl;
//날짜 정보 사용
/*
* LocalDate 클래스는 public 생성자를 제공하지 않기 때문에 객체를 생성할 때는 now()나, 
* of(), parse()와 같은 정적 메소드를 사용하도록 되어 있습니다. 
* 기본 포멧인 yyyy-MM-dd 형태의 문자열을 parse() 메소드에 넘길 수 있습니다.
*/

public class Ingredient implements Serializable{
	
	private static final long serialVersionUID = 4896343328864682183L;
	private static int cnt = 1;
	private int idx;
	private String name;
    private int amount;
    private int price;
    private LocalDate due;

   
	public Ingredient(String name, int amount, int price, LocalDate due) {
//		this.idx = cnt++; 변경
		this.idx = RestaurantRefrigeratorDaoImpl.getIng().size()+1;//인덱스 추가
		this.name = name;
		this.amount = amount;
		this.price = price;
		this.due = due;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public LocalDate getDue() {
		return due;
	}
	public void setDue(LocalDate due) {
		this.due = due;
	}
	
	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	@Override
	public String toString() {
		//return "Ingredient [idx=" + idx + ", name=" + name + ", amount=" + amount + ", price=" + price + ", due=" + due+ "]";
		return " [ 식자재  " + idx + " | 이름 : " + name + " | 수량 :" + amount + " | 가격 :" + price + "원 | 유통기한:" + due + " ]";
	}

	
	
}