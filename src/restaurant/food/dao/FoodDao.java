package restaurant.food.dao;

import java.util.ArrayList;
import java.util.Map;

import restaurant.food.vo.Food;
import restaurant.food.vo.Ingredient;

public interface FoodDao {
	void insert(Food food);
	void deleteByIdx(int idx);
	void deleteByName(String foodName);
	Food searchByIdx(int idx);
	Food searchByName(String name);
	void insertIng(int idx, Map<String, Integer> ingredient);
	void deleteIng(int idx, String ingName);
	void changeIngCnt(int idx, String key, int value);
	void updatePrice(int idx, int price);
	void updateName(int idx, String name);
	ArrayList<Food> getAllFood();
	void start();
	void stop();
}
