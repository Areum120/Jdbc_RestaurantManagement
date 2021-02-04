package restaurant.food.dao;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import restaurant.food.vo.Food;
import restaurant.food.vo.Ingredient;
import restaurant.order.vo.Order;

/**
 * 
 * @author Han
 *
 */
public class FoodDaoImpl implements FoodDao {
	private static ArrayList<Food> foods; // 수정함
	public static final String FOOD_FILE_PATH = "src/restaurant/files/foods.dat";

	public FoodDaoImpl() {
		foods = new ArrayList<>();
		//init();
		start();
	}
	
	/**
	 * @return : 모든 음식을 반환
	 */
	@Override
	public ArrayList<Food> getAllFood() {
		return foods;
	}

	public static ArrayList<Food> getFoods() {
		return foods;
	}

	/**
	 * void : FOOD_FILE_PATH를 읽어 foods에 저장
	 */
	public void start() {
		try {
			FileInputStream fis = new FileInputStream(FOOD_FILE_PATH);
			ObjectInputStream ons = new ObjectInputStream(fis);
			foods = (ArrayList<Food>) ons.readObject();
			ons.close();
			fis.close();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param food void : Food 객체를 추가함
	 */
	@Override
	public void insert(Food food) {
		foods.add(food);
		System.out.println("요리 추가 완료!");
	}

	/**
	 * 음식 인덱스로 음식을 찾아 반환하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 idx
	 * @return : 입력된 idx와 일치하는 음식을 반환, 찾지 못할 경우 null 반환
	 */
	@Override
	public Food searchByIdx(int idx) {
		Food f = getAllFood().get(idx-1);
		if (f == null) {
			System.out.println("요리가 존재하지 않습니다.");
			return null;
		} else {
			return f; // 인덱스에 있는 객체 리턴
		}
	}

	/**
	 * 음식 이름으로 음식을 찾아 반환하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 name
	 * @return : 입력된 name과 일치하는 음식을 반환, 찾지 못할 경우 null 반환
	 */
	@Override
	public Food searchByName(String name) {
		Food f = new Food();
		f.setFoodName(name);
		// foods에 f가 존재하면 f의 인덱스를 리턴, 없으면 -1 리턴
		int index = foods.indexOf(f);
		if (index < 0) { // f가 없으면(-1이면)
			return null;
		} else {
			return foods.get(index); // 인덱스에 있는 객체 리턴
		}
	}

	/**
	 * 음식 인덱스로 음식을 찾아 삭제하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 idx void : 찾은 음식을 삭제, 없으면 sysout출력
	 */
	@Override
	public void deleteByIdx(int idx) {
		Food f = searchByIdx(idx);
		if (f != null) {
			foods.remove(f);
			System.out.println("요리 삭제 완료!");
			for(int i=idx-1; i<foods.size(); i++) { //삭제시 삭제한 리스트 이후 idx를 -1씩 감소시켜줌
				if(i == 0) {
					for(int j=0; j<foods.size(); j++) {
						foods.get(j).setIdx(j+1);
					}
					break;
				}else {
					foods.get(i).setIdx(i+1);
				}
			}
		} else {
			System.out.println("없는 번호 입니다. 다시 확인해주세요!");
		}
	}

	/**
	 * 음식 이름으로 음식을 찾아 삭제하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 name void : 찾은 음식을 삭제, 없으면 sysout출력
	 */
	@Override
	public void deleteByName(String name) {
		Food f = searchByName(name);
		if (f != null) {
			foods.remove(f);
			System.out.println("요리 삭제 완료!");
		} else {
			System.out.println("없는 번호 입니다. 다시 확인해주세요!");
		}
	}

	/**
	 * 음식 인덱스로 음식을 찾아 재료를 추가하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 idx, 추가할 ingredients void : 찾은 음식의 재료를 추가, 없으면 sysout출력
	 */
	@Override
	public void insertIng(int idx, Map<String, Integer> ingredient) {
		Food f = searchByIdx(idx);
		if (f != null) {
			f.getIngredient().putAll(ingredient);
			System.out.println("요리 재료 추가 완료!");
		} else {
			System.out.println("없는 번호 입니다. 다시 확인해주세요!");
		}
	}

	/**
	 * 음식 인덱스로 음식을 찾아 재료를 삭제하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 idx, 삭제할 ingredients void : 찾은 음식의 재료를 수정, 없으면 sysout출력
	 */
	@Override
	public void deleteIng(int idx, String ingName) {
		Food f = searchByIdx(idx);
		if (f != null && ingName != null) {
			f.getIngredient().remove(ingName);
			System.out.println("요리 재료 삭제 완료!");
		}else {
			System.out.println("재료명을 잘못 입력하였습니다.");
		}
	}

	/**
	 * 음식 인덱스로 음식을 찾아 재료의 수량을 변경하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 idx, 변경할 재료의 key, 변경할 value void : 찾은 음식의 재료의 수량을 수정, 없으면
	 *        sysout출력
	 */
	@Override
	public void changeIngCnt(int idx, String key, int value) {
		Food f = searchByIdx(idx);
		if (f != null) {
			if (value > 0) { // 수량이 양수이면
				f.getIngredient().replace(key, value);
				System.out.println("요리 재료 수량 변경 완료!");
			} else {
				System.out.println("수량이 0 이하입니다!");
			}
		} else {
			System.out.println("없는 번호 입니다. 다시 확인해주세요!");
		}
	}

	/**
	 * 음식 인덱스로 음식을 찾아 가격을 수정하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 idx, 변경할 price void : 찾은 음식의 가격을 수정, 없으면 sysout출력
	 */
	@Override
	public void updatePrice(int num, int price) {
		// 예외처리!
//		int temp = getFoods().get(num-1).getIdx();
//		if(temp == num-1) {
//			getFoods().get(num-1).setPrice(price);
//		}else {
//			System.out.println("없는 번호 입니다. 다시 확인해주세요! 다오부분!");
//		}
		if (getAllFood().get(num-1) != null) {
			getAllFood().get(num-1).setPrice(price);
		} else {
			System.out.println("없는 번호입니다. 다시 확인해주세요.");
		}

	}

	/**
	 * 음식 인덱스로 음식을 찾아 음식 이름을 수정하는 메소드
	 * 
	 * @param : 찾고자 하는 음식의 idx, 변경할 name void : 찾은 음식의 이름을 수정, 없으면 sysout출력
	 */
	@Override
	public void updateName(int idx, String name) {
		Food f = searchByIdx(idx);
		if (f != null) {
			f.setFoodName(name);
		} else {
			System.out.println("없는 번호 입니다. 다시 확인해주세요!");
		}
	}

	/**
	 * void : foods의 객체를 FOOD_FILE_PATH에 저장
	 */
	public void stop() {
		try {
			FileOutputStream fos = new FileOutputStream(FOOD_FILE_PATH); // true : 기존자료에 이어서 쓰기 옵션
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(foods);
			oos.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("DaoImpl stop() Error: 파일을 저장하지 못했습니다.");
			e.printStackTrace();
		}
	}

	/**
	 * 첫 실행시만 파일에 초기화. 이후 주석처리
	 */
	public void init() {
		Map<String, Integer> il1 = new HashMap<>();
		il1.put("김", 1);
		il1.put("단무지", 1);
		il1.put("쌀", 3);
		il1.put("햄", 1);
		il1.put("계란", 1);
		Map<String, Integer> il2 = new HashMap<>();
		il2.put("면사리", 1);
		il2.put("어묵", 2);
		il2.put("대파", 2);
		il2.put("쑥갓", 1);
		il2.put("유부", 2);
		Map<String, Integer> il3 = new HashMap<>();
		il3.put("면사리", 2);
		il3.put("어묵", 5);
		il3.put("대파", 3);
		il3.put("떡", 3);
		il3.put("치즈", 2);
		Map<String, Integer> il4 = new HashMap<>();
		il4.put("돼지고기", 2);
		il4.put("계란", 1);
		il4.put("밀가루", 3);
		il4.put("빵가루", 3);
		Map<String, Integer> il5 = new HashMap<>();
		il5.put("김치", 4);
		il5.put("쌀", 5);
		il5.put("계란", 2);
		il5.put("햄", 2);
		il5.put("대파", 1);
		foods.add(new Food("김밥", 3000, il1));
		foods.add(new Food("우동", 3500, il2));
		foods.add(new Food("라볶이", 5500, il3));
		foods.add(new Food("돈까스", 8000, il4));
		foods.add(new Food("김치볶음밥", 7000, il5));

		try {
			FileOutputStream fos = new FileOutputStream(FOOD_FILE_PATH);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(foods);
			oos.close();
			fos.close();
		} catch (IOException e) {
			System.out.println("restaurant.supplier DaoImpl init() Error: 초기화 파일을 불러오지 못했습니다.");
			e.printStackTrace();
		}

	}

}
