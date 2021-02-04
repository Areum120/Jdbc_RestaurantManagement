package restaurant.food.service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import restaurant.food.vo.Food;

public interface FoodService {
	void addFood(Scanner sc);
	Food getFoodByIdx(int idx);
	ArrayList<Food> getAllFood();
	void printFoodByIdx(Scanner sc);
	void printAllFood();
	void changePriceByIdx(Scanner sc);
	Map<String, Integer> addIng(Scanner sc);
	void addIngByIdx(Scanner sc);
	void delIngByIdx(Scanner sc);
	void changeFoodNameByIdx(Scanner sc);
	void delFoodByIdx(Scanner sc);
}
