package restaurant.order.service;


import java.util.ArrayList;
import java.util.Iterator;

import restaurant.finance.dao.FinanceDaoImpl;
import restaurant.finance.vo.Finance;
import restaurant.food.dao.FoodDao;
import restaurant.food.dao.FoodDaoImpl;
import restaurant.food.vo.Food;
import restaurant.food.vo.Ingredient;
import restaurant.order.dao.OrderDAO;
import restaurant.order.dao.OrderDAOImpl;
import restaurant.order.vo.Order;
import restaurant.refrigerator.dao.RefrigeratorDao;

/**
 * 
 * @author SeongJin Park
 *
 */
public class OrderServiceImpl implements OrderService{
	
	
	private int foodIdx;
	private int foodAmount;
	private Food food;
	private OrderDAO dao; // 주문DAO
	private Order o;
	private RefrigeratorDao RfDao; // 냉장고 DAO
	private FoodDao foodDao; // 요리DAO
	private FinanceDaoImpl financeDao; // 입출금DAO
	
	public OrderServiceImpl() {
		
		dao=new OrderDAOImpl();
		foodDao=new FoodDaoImpl();
		financeDao = restaurant.finance.dao.FinanceDaoImpl.getInstance();
		RfDao = restaurant.refrigerator.dao.RestaurantRefrigeratorDaoImpl.getInstance(); 
	}
	
	/*
	 *@param : foodIdx 주문음식의 index
 	 * 
	 */
	@Override
	public boolean checkIngr(int foodIdx) {
		boolean flag=false; 
			if(foodDao.searchByIdx(foodIdx)!=null) {
				this.foodIdx=foodIdx;
				flag=true;
			} else {
				System.out.println("메뉴확인 필요.");
			} 
			return flag;
	}
	public boolean checkOrder(int foodAmount) {
		boolean flag=false; 
		boolean flag2=true;
		Food food = foodDao.searchByIdx(foodIdx); // 요리의 재료검색
		Iterator<String> iter = food.getIngredient().keySet().iterator();
			while(iter.hasNext() && flag2){
				String ingredient = iter.next(); // 재료 이름
				int ingAmount = food.getIngredient().get(ingredient); // 재료 수량
				if(RfDao.searchByName(ingredient).size()!=0) { // 재료 이름으로 찾기
					ArrayList<Ingredient> ing = RfDao.searchByName(ingredient);
					for(Ingredient rsIng : ing) {
						if(rsIng.getName().equals(ingredient)) {
							if(rsIng.getAmount()<(ingAmount*foodAmount)) {
								System.out.println("재료가 부족합니다.");
								flag=false;
								flag2=false;
								this.foodIdx=0;
								break;
							} else {
								flag=true;
							}
						}
					}
					
				} else {
					flag=false;
					flag2=false;
					this.foodIdx=0;
				}
			}
			return flag;
	}
	/*
	 *@param : food 음식정보
	 *		   foodAmount 주문음식의 수량
 	 * 
	 */
	@Override
	public void addOrder(int foodAmount) {		
			
			Food food = foodDao.searchByIdx(foodIdx); // 요리의 재료검색
			this.o = new Order(food, foodAmount); // 주문생성
			System.out.println("주문메뉴 : " + food.getFoodName() + "  "+foodAmount+"인분");
			
			
			dao.insert(o);
			
	}
		
		
	
	@Override
	public ArrayList<Order> getAllOrder() {
		
		return dao.getAllOrder();
	}

	@Override
	public void finishOrder(int num) {
		boolean flag=false;
			ArrayList<Order> o = dao.getAllOrder();
			for(Order rs: o) {
				if(rs.getNum()==num) {
					this.foodIdx=rs.getFood().getIdx(); // 요리 idx
					this.foodAmount = rs.getAmount(); // 주문 수량
					flag=true;
					break;
				}
			}
		if(flag) {
			food = foodDao.searchByIdx(foodIdx); // foodIdx로 search
			financeDao.output((food.getPrice()*foodAmount), food.getFoodName()+" 환불처리");
			System.out.println(food.getFoodName()+""+foodAmount+"인분   "+ food.getPrice()*foodAmount+"원 환불처리");
			System.out.println("총 : " + Finance.getTOTAL_MONEY()+"원");
			dao.delete(num);
		} else {
			System.out.println("주문번호를 확인해주세요");
		}
	}

	@Override
	public void printAllOrder() {
		
		for(Order o : getAllOrder()) {
			System.out.println(o);
		}
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		dao.start();
	}

	@Override
	public void orderSave() {
		// TODO Auto-generated method stub
		dao.orderSave();
		this.foodIdx=0;
		this.foodAmount=0;
	}

	@Override
	public void completeOrder(int num) {
		boolean flag=false;
		ArrayList<Order> o = dao.getAllOrder();
		for(Order rs: o) {
			if(rs.getNum()==num) {
				this.foodIdx=rs.getFood().getIdx(); // 요리 idx
				this.foodAmount = rs.getAmount(); // 주문 수량
				flag=true;
				break;
			}
		}
		if(flag) {
			Food food = foodDao.searchByIdx(foodIdx); // 요리의 재료검색
			Iterator<String> iter = food.getIngredient().keySet().iterator();
				while(iter.hasNext()){
					String ingredient = iter.next(); // 재료 이름
					int ingAmount = food.getIngredient().get(ingredient); // 재료 수량
					if(RfDao.searchByName(ingredient).size()!=0) {
		
						ArrayList<Ingredient> ing = RfDao.searchByName(ingredient); //냉장고
						for(Ingredient rsIng : ing) {
								if(rsIng.getName().equals(ingredient)) {
								int allAmount=(ingAmount*foodAmount);
								RfDao.updateAmount(rsIng.getName(),-allAmount);
								}
							}
						}
					}
			food = foodDao.searchByIdx(foodIdx); // foodIdx로 search
			financeDao.input((food.getPrice()*foodAmount), food.getFoodName()); // 매출증가
			System.out.println("주문메뉴 : " + food.getFoodName() + "  "+foodAmount+"인분");
			System.out.println("수익 : " + food.getPrice()*foodAmount+"원");
			System.out.println("총 : " + Finance.getTOTAL_MONEY()+"원");
			
			
			dao.complete(num);		
		} else {
			System.out.println("주문번호를 확인해주세요");
		}
	}

}
	

