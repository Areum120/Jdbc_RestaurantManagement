package restaurant.order.dao;

import java.util.ArrayList;

import restaurant.order.vo.Order;


public interface OrderDAO {
	void insert(Order o);
	void delete(int num);
	ArrayList<Order> getAllOrder();
	void orderSave();
	void start();
	void complete(int num);
}
