package restaurant;


import java.time.LocalDate;
import java.util.Scanner;

import restaurant.finance.service.FinanceServiceImpl;
import restaurant.food.service.FoodServiceImpl;
import restaurant.order.service.OrderServiceImpl;
import restaurant.refrigerator.RefrigeratorServiceImpl;
import restaurant.supplier.SupplyServiceImpl;


public class Menu {

	private static final LocalDate LocalDate = null;
	private FinanceServiceImpl finance_service;
	private SupplyServiceImpl supply_service;
	private FoodServiceImpl food_service;
	private OrderServiceImpl order_service;
	private RefrigeratorServiceImpl refrigerator_Service;
	
	public Menu() {
		finance_service = new FinanceServiceImpl();
		supply_service = new SupplyServiceImpl();
		food_service = new FoodServiceImpl();
		order_service = new OrderServiceImpl();
		refrigerator_Service = new RefrigeratorServiceImpl();
	}
	
	// 메인 메뉴
	public void run(Scanner sc) {
		boolean flag = true;
		while (flag) {
			System.out.println("============= [메인 메뉴] ============");
			System.out.println("[1 요리 관리] [2 냉장고 관리] [3 주문 관리]  [4 공급처 관리]  [5 매출 관리] [6 프로그램종료]");
			int m = sc.nextInt();
			switch (m) {
			case 1:
				run_f(sc);
				break;
			case 2:
				run_r(sc);
				break;
			case 3:
				run_o(sc);
				break;
			case 4:
				run_s(sc);	
				break;
			case 5:
				run_fi(sc);	
				break;
			case 6:
				flag = false;
				System.out.println("프로그램을 종료합니다.");
				break;
			}
		}
	}
	
	public void run_fi(Scanner sc) {
		boolean flag = true;
		while(flag) {
			System.out.println("============= [매출 관리] ============");
			System.out.println("1.현재 총 보유금액 출력 2.입출금내역 출력 3.입금하기 4.출금하기 5.뒤로가기");
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				finance_service.printTotalMoney();
				break;
			case 2:
				finance_service.printAllFinanceRecords();
				break;
			case 3:
				finance_service.inputMoney(sc);
				break;
			case 4:
				finance_service.outputMoney(sc);
				break;
			case 5:
				flag = false;
				break;
			default:
				System.out.println("번호를 정확히 입력하세요.");
				break;
			}
		}
	}
	
	public void run_f(Scanner sc2) {
		boolean flag = true;
		while(flag) {
			System.out.println("============= [요리 관리] ============");
			System.out.println("1.요리 추가하기 2.요리 전체 보기 3.요리 가격 변경하기 4.요리 이름 변경하기 5.요리 재료 추가하기 6.요리 재료 삭제하기 7.요리 삭제 하기 8.뒤로가기");
			Scanner sc = new Scanner(System.in);
			int menu = sc.nextInt();
			switch(menu) {
			case 1:
				food_service.addFood(sc);
				break;
			case 2:
				food_service.printAllFood();
				break;
			case 3:
				food_service.changePriceByIdx(sc);
				break;
			case 4:
				food_service.changeFoodNameByIdx(sc);
				break;
			case 5:
				food_service.addIngByIdx(sc);
				break;
			case 6:
				food_service.delIngByIdx(sc);
				break;
			case 7:
				food_service.delFoodByIdx(sc);
				break;
			case 8:
				food_service.getDao().stop(); //프로그램 종료시 저장
				flag = false;
				break;
			default:
				System.out.println("번호를 정확히 입력하세요.");
				break;
			}
		}
	}
	
	public void run_o(Scanner sc) {
		boolean flag = true;

		while(flag) {
			order_service.start();
			System.out.println("============= [주문 관리] ============");
			System.out.println("1.주문추가 2.주문목록 3.주문취소 4.주문완료 5.뒤로가기");
			int m= sc.nextInt();
			
			switch(m) {
				case 1:	
						System.out.println("주문음식선택");
					    food_service.printAllFood();
						int foodIdx = sc.nextInt();
						
						if(order_service.checkIngr(foodIdx)) { // 메뉴 체크
							
							System.out.println("주문수량");
							int foodAmount=sc.nextInt();
							if(order_service.checkOrder(foodAmount)) { //수량 체크
								order_service.addOrder(foodAmount);
							} else {
								System.out.println("재고확인 필요 ");
							}
							order_service.orderSave();
						}
						break;
				
				case 2:
						order_service.printAllOrder();
						break;
						
				case 3: 
						if(order_service.getAllOrder().size()!=0) {
							System.out.println("취소주문 선택");
							order_service.printAllOrder();
							int calcelOrder= sc.nextInt();
						order_service.finishOrder(calcelOrder);
						} else {
							System.out.println("주문이 없습니다.");
						}
						break;
				case 4:
						
						if(order_service.getAllOrder().size()!=0){
						
							System.out.println("주문완료 선택");
							order_service.printAllOrder();
							int completeOrder= sc.nextInt();
						order_service.completeOrder(completeOrder);
						} else {
							System.out.println("주문이 없습니다.");
						} 
						break;
				case 5:
						flag=false;
						break;
			}
		}
	}
	
	public void run_r(Scanner sc) {
		boolean flag = true;
		
		while (flag) {
			System.out.println("============= [냉장고 관리] ============");
			System.out.println("1.식자재 입고 2.유통기한 확인 3.식자재 정보 검색 4.식자재 폐기 5.식자재 목록 6.뒤로가기");
			int menu = sc.nextInt();

			switch(menu) {
				case 1:
					supply_service.getAllIng();
					refrigerator_Service.BuyIng(sc);
					break;
				case 2: 
					refrigerator_Service.editDue(toString(), LocalDate);
				case 3:
					refrigerator_Service.getByName(sc);
					break;
				case 4:
					refrigerator_Service.getAllIng();
					refrigerator_Service.deleteIng(sc);
					break;
				case 5:
					refrigerator_Service.getAllIng();
					break;
				case 6:
					restaurant.refrigerator.dao.RestaurantRefrigeratorDaoImpl.getInstance().stop();
					flag=false;
					break;
			}

		}
	}
	
	public void run_s(Scanner sc) {
		boolean flag = true;
		
		while(flag) {
			System.out.println("============= [공급처 관리] ============");
			System.out.println("1.식자재 정보 검색 2.식자재 구매 3.식자재 목록 4.뒤로가기");
			int menu = sc.nextInt();
			
			switch(menu) {
			case 1:
				supply_service.getByName(sc);
				break;
			case 2:
				supply_service.getAllIng();
				supply_service.buyIng(sc);
				break;
			case 3:
				supply_service.getAllIng();
				break;
			case 4:
				restaurant.supplier.dao.SupplyDaoImpl.getInstance().save();
				flag=false;
				break;
	
			}
		}
	}
	

}
