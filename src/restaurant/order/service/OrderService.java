package restaurant.order.service;

import java.util.ArrayList;

import restaurant.order.vo.Order;




public interface OrderService {
	boolean checkIngr(int foodIdx);
	void addOrder(int foodAmount);
	ArrayList<Order> getAllOrder();
	void printAllOrder();
	void finishOrder(int num);
	void start();
	void orderSave();
	void completeOrder(int num);
	
}
