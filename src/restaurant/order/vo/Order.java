package restaurant.order.vo;

import java.io.Serializable;

import restaurant.food.vo.Food;

public class Order implements Serializable {

	
	private static int cnt;
	private Food food;
	private int amount;
	private int num;
	
	public Order() {}
	
	public Order(Food food, int amount) {
		
		this.num=++cnt;
		this.food = food;
		this.amount = amount;
	}
	
	
	public static int getCnt() {
		return cnt;
	}

	public static void setCnt(int cnt) {
		Order.cnt = cnt;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {

		return " [ 주문번호  " + num + " | 요리 : " + food.getFoodName() + " | 수량 :" + amount + "]";
	}
	
	
	}
	
