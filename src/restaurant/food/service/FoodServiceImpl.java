package restaurant.food.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import restaurant.food.dao.FoodDao;
import restaurant.food.dao.FoodDaoImpl;
import restaurant.food.vo.Food;
import restaurant.food.vo.Ingredient;
import restaurant.supplier.SupplyServiceImpl;
/**
 * 
 * @author Han
 *
 */
public class FoodServiceImpl implements FoodService {
	private FoodDao dao = new FoodDaoImpl(); //수정함
	private SupplyServiceImpl supply_service = new SupplyServiceImpl(); //수정함
	ArrayList<Ingredient> suppList = restaurant.supplier.dao.SupplyDaoImpl.getInstance().getIngredients();
	
	public FoodServiceImpl() {
	}
	
	
	public FoodDao getDao() {
		return dao;
	}

	/**
	 * @param scanner로 음식 이름/가격/재료 이름/재료 수량 입력받음
	 * void : 입력 받은 Food 객체를 추가함
	 */
	@Override
	public void addFood(Scanner sc) {
		ArrayList<Ingredient> ingredients;
		System.out.println("========= 요리 등록 =========");
		System.out.print("요리 이름 : ");
		String foodName = sc.next();
		System.out.print("가격 : ");
		int price = sc.nextInt();
		boolean flag = true;
		String ingName = null;
		int tempName;
		int ingCnt;
		Map<String, Integer> temp = new HashMap<>();
		while(flag) {
			System.out.print("1.재료 입력 2. 입력 종료");
			int num = sc.nextInt();
			switch (num) {
			case 1:
				//재료 리스트 보여주고 선택
				supply_service.getAllIng();
				System.out.println("재료 번호 : ");
				tempName = sc.nextInt();
				// 예외처리 : 배열의 길이를 넘어갈경우
				try { 
					if(tempName == suppList.get(tempName-1).getIdx());
				}catch (IndexOutOfBoundsException e) {
					System.out.println("없는 재료입니다. 다시 선택해주세요");
					break;
				}
				//유효성검증 : 냉장고에 있는 재료와 입력받은 재료가 같으면 수량을 받음
				if(tempName == suppList.get(tempName-1).getIdx()) {
					ingName = suppList.get(tempName-1).getName();
					System.out.println("필요 수량 : ");
					ingCnt = sc.nextInt();
					if(ingCnt > 0) {
						temp.put(ingName, ingCnt);
						System.out.println("재료를 입력하였습니다!");
					}else {
						System.out.println("수량이 없습니다!");
					}
				}else{
					System.out.println("없는 재료입니다. 다시 선택해주세요.");
				}
				break;
			case 2:
				flag = false;
				break;
			default:
				System.out.println("번호를 정확히 입력해주세요!");
				break;
			}
		}
		Food f = new Food(foodName, price, temp);
		//ArrayList로 하면 재료 개수가 등록이 안된다.
		//만약 개수를 등록하려면 Ingredient 필드에 카운트를 추가해야된다.
		//설계상 그건 별로인 것 같다.
		dao.insert(f);
	}

	/**
	 * 인덱스로 음식을 찾아 반환하는 메소드
	 * @param  : 찾고자 하는 음식의 idx
	 * @return : 입력된 idx와 일치하는 음식을 반환, 찾지 못할 경우 null 반환
	 */
	@Override
	public Food getFoodByIdx(int idx) {
		Food tempFood = dao.searchByIdx(idx);
		if(tempFood != null){
			return tempFood;
		}else {
			System.out.println("번호를 잘못 입력하였습니다.");
		}
		return null;
	}

	/**
	 * @return : 음식을 전부 반환, 없는 경우 null 반환
	 */
	@Override
	public ArrayList<Food> getAllFood() {
		ArrayList<Food> foods = dao.getAllFood();
		if(foods != null) {
			return dao.getAllFood();
		}else {
			System.out.println("등록된 요리가 하나도 없습니다.");
		}
		return null;
	}

	/**
	 * 인덱스로 음식을 찾아 출력하는 메소드
	 * @param  : 찾고자 하는 음식의 idx
	 * void : 입력된 idx와 일치하는 음식을 출력
	 */
	@Override
	public void printFoodByIdx(Scanner sc) {
		System.out.println("");
		int idx = sc.nextInt();
		Food tempFood = this.getFoodByIdx(idx);
		if(tempFood != null){
			System.out.println(tempFood);
		}else {
			System.out.println("해당하는 요리가 없습니다.");
		}
	}

	/**
	 * void : 모든 음식을 출력
	 */
	@Override
	public void printAllFood() {
		ArrayList<Food> foods = this.getAllFood();
		if(foods != null) {
			for(Food f : foods) {
				System.out.println(f);
			}
		}
	}

	/**
	 * @param : scanner로 음식 번호/음식 가격 입력 받음
	 * void : 입력받은 번호의 음식의 가격을 변경
	 */
	@Override
	public void changePriceByIdx(Scanner sc) {
		System.out.println("========= 요리 가격 수정 =========");
		this.printAllFood();
		System.out.println("========= ========= =========");
		System.out.print("수정할 요리 번호: ");
		int num = sc.nextInt();
		if(num > getDao().getAllFood().size() || num <=0) {
			System.out.println("입력 번호를 다시 확인해주세요.");
		}else {
			try { 
				if(num != this.getFoodByIdx(num).getIdx());
			}catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("없는 번호 입니다. 다시 확인해주세요!!");
			}
			if(num == this.getFoodByIdx(num).getIdx()) {
				System.out.print("새 가격 : ");
				int price = sc.nextInt();
				if(price > 0) {
					dao.updatePrice(num, price);
				}
			}
		}
	}
	
	/**
	 * @param : scanner로 변경할 음식 번호/변경할 재료 이름 입력 받음
	 * void : 입력받은 음식 재료의 수량을 변경
	 */
	@Override
	public void changeFoodNameByIdx(Scanner sc) {
		System.out.println("========= 요리 이름 변경 =========");
		this.printAllFood();
		System.out.println("========= ========= =========");
		System.out.print("이름 변경할 요리 번호: ");
		int num = sc.nextInt();
		if(num > getDao().getAllFood().size() || num <=0) {
			System.out.println("입력 번호를 다시 확인해주세요.");
		}else {
			try { 
				if(num == this.getFoodByIdx(num).getIdx());
			}catch (IndexOutOfBoundsException e) {
				System.out.println("없는 번호 입니다. 다시 확인해주세요!");
			}
			if(num == this.getFoodByIdx(num).getIdx()) {
				System.out.println("새로운 요리 이름: ");
				String name = sc.next();
				if(name != null) {
					dao.updateName(num, name);
				}
			}else {
				System.out.println("번호를 잘못 입력하였습니다.");
			}
		}
		
	}

	/**
	 * @param : scanner로 재료 이름/재료 수량 입력 받음
	 * @return : 입력받은 재료를 map으로 반환
	 */
	@Override
	public Map<String, Integer> addIng(Scanner sc) {
		int tempName, ingCnt;
		String ingName = null;
		Map<String, Integer> temp = new HashMap<>();
		System.out.println("========= ========= =========");
		supply_service.getAllIng(); //공급처 재료 리스트 보여줌
		System.out.println("재료 번호 : ");
		tempName = sc.nextInt();
		if(tempName > suppList.size() || tempName <=0) {
			System.out.println("입력 번호를 다시 확인해주세요.");
			return null;
		}else {
			//유효성검증
			if(tempName == suppList.get(tempName-1).getIdx()) {
				ingName = suppList.get(tempName-1).getName();
				System.out.println("필요 수량 : ");
				ingCnt = sc.nextInt();
				if(ingCnt > 0) {
					temp.put(ingName, ingCnt);
					System.out.println("재료를 입력하였습니다!");
				}else {
					System.out.println("수량이 없습니다!");
				}
			}else{
				System.out.println("없는 재료입니다. 다시 선택해주세요.");
			}
			return temp;
		}
	}
	
	/**
	 * @param : scanner로 재료 추가할 음식 번호 입력 받음
	 * void : 입력받은 재료를 추가
	 */
	@Override
	public void addIngByIdx(Scanner sc) {
		Map<String, Integer> temp = null;
		System.out.println("========= 요리 재료 추가 =========");
		this.printAllFood();
		System.out.println("========= ========= =========");
		System.out.print("재료 추가할 요리 번호: ");
		int num = sc.nextInt();
		if(num > getDao().getAllFood().size() || num <=0) {
			System.out.println("입력 번호를 다시 확인해주세요.");
		}else {
			if(num == dao.searchByIdx(num).getIdx()) {
				temp = this.addIng(sc);
				if(temp == null) { //재료 리스트 밖의 값이면
					//입력안함
				}else {
					dao.insertIng(num, temp);
				}
			}else {
				System.out.println("번호를 잘못 입력하였습니다.");
			}
		}
	}

	/**
	 * @param : scanner로 삭제할 음식 번호/삭제할 재료 번호 입력 받음
	 * void : 입력받은 음식의 재료를 삭제
	 */
	@Override
	public void delIngByIdx(Scanner sc) {
		System.out.println("========= 요리 재료 삭제 =========");
		this.printAllFood();
		System.out.println("========= ========= =========");
		System.out.print("재료 삭제할 요리 번호: ");
		int foodNum = sc.nextInt();
		if(foodNum > getDao().getAllFood().size() || foodNum <=0) {
			System.out.println("입력 번호를 다시 확인해주세요.");
		}else {
			Food f = this.getFoodByIdx(foodNum);
			System.out.println("========= ========= =========");
			for(Map.Entry<String, Integer> a :  f.getIngredient().entrySet()){
	            System.out.println("재료: "+ a.getKey() + " 수량: " + a.getValue());
	        }
			System.out.println("========= ========= =========");
			System.out.print("삭제할 재료 이름: ");
			String ingName = sc.next();
			String tempName = null;
			for(String key : f.getIngredient().keySet()) {
				if(key.equals(ingName)) { //입력받은 재료랑 음식 재료 리스트랑 같으면
					tempName = ingName;
				}
			}
			dao.deleteIng(foodNum, tempName);
		}
	}
	
	
	/**
	 * @param : scanner로 삭제할 음식 번호 입력 받음
	 * void : 입력받은 음식을 삭제
	 */
	@Override
	public void delFoodByIdx(Scanner sc) {
		System.out.println("========= 요리 삭제 =========");
		this.printAllFood();
		System.out.println("========= ========= =========");
		System.out.print("삭제할 요리 번호: ");
		int num = sc.nextInt();
		if(num > getDao().getAllFood().size() || num <=0) {
			System.out.println("입력 번호를 다시 확인해주세요.");
		}else {
			if(num == this.getFoodByIdx(num).getIdx()) {
				dao.deleteByIdx(num);
				
			}
		}
	}
}
