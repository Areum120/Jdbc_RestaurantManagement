package restaurant.refrigerator;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import restaurant.finance.dao.FinanceDaoImpl;
import restaurant.finance.vo.Finance;
import restaurant.food.vo.Ingredient;
import restaurant.refrigerator.dao.RestaurantRefrigeratorDaoImpl;
import restaurant.supplier.dao.SupplyDaoImpl;

public class RefrigeratorServiceImpl implements RefrigeratorService {

	
		private RestaurantRefrigeratorDaoImpl rRDao;
		private SupplyDaoImpl sRDao;
		private ArrayList<Ingredient> supplyIngredients;
		private FinanceDaoImpl fDao;
		
			
	public RefrigeratorServiceImpl() {
		
		rRDao = restaurant.refrigerator.dao.RestaurantRefrigeratorDaoImpl.getInstance(); //싱글톤 객체를 받아온다
		this.supplyIngredients = supplyIngredients;
		sRDao = restaurant.supplier.dao.SupplyDaoImpl.getInstance();
		fDao = restaurant.finance.dao.FinanceDaoImpl.getInstance();
		}


	/*
	 공급처 냉장고에서 식자재 이름, 가격 구매하는 식으로
	
	rRDao.addIng(sRDao.selectAllIng());//공급처에서 식자재 가져오기
	
	 */
	@Override
	public void BuyIng(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("구매할 식자재 이름을 입력하세요");
		String name = sc.next();
		while (isInteger(name) || name.length() == 0) {
			System.out.println("유효한 이름을 다시 입력해주세요");
			name = sc.next();
		}
		System.out.println("구매 수량을 입력하세요");
		int amount = (sc.nextInt());

		ArrayList<Ingredient> rIng = rRDao.searchByName(name);
		// int price = rRDao.selectAllIng().get(0).getPrice();
		int price = sRDao.searchByName(name).get(0).getPrice();

		if (Finance.getTOTAL_MONEY() - price * amount >= 0) {
			Finance.setTOTAL_MONEY(Finance.getTOTAL_MONEY() - price);
			System.out.println(amount + "개 구매 되었습니다");
			if (rIng.size() == 0) {// 냉장고에 재고가 없으면 새로 추가
				LocalDate expiryDate = LocalDate.now().plusDays(3); // 유통기한 3일
				fDao.input(-amount, name + "구매");
				System.out.println("냉장고 재고 확인:" + name + amount + "개 입니다.");
				rRDao.addIng(new Ingredient(name, amount, sRDao.searchByName(name).get(0).getPrice(), expiryDate));
			} else {
				LocalDate expiryDate = LocalDate.now().plusDays(3); // 유통기한 3일
				fDao.input(-amount, name + "구매");
				rRDao.updateAmount(name, amount);
				sRDao.updateAmount(name, -amount);
				rRDao.updateDue(name, expiryDate);
				System.out.println("냉장고 재고 확인:" + name + rRDao.searchByName(name).get(0).getAmount() + "개 입니다.");
			}
		} else {// 냉장고에 재고가 있으면 개수 추가
			System.out.println("잔액이 부족하여 구매 불가");
				
			}
		
	}
		/*
		 * SupplyService의 buyIng를 써서 공급처개수 차감, 금액관련 처리
		 * if(저장소에 이미 존재하는 식자재가 없을경우)
		 * 리스트 = daoimple serchByName(name) 
		 * 식당의 저장소에 이름으로 받아준 것 넘겨줌(냉장고에 사온거 넣어줌)
		 * else(저장소에 이미 존재하는 식자재 o)
		 * updateAmount로 개수만 올려줌
		 * 
		 */



	@Override
	public void editDue(String name, LocalDate Date) {
		rRDao.selectAllIng().stream().forEach(due2->{
			
			
			LocalDate due = due2.getDue();	
			
			LocalDate supplierDate = due2.getDue();//공급처식자재 유통기한을 가져온다
			
			LocalDate today = LocalDate.now();//금일날짜 
			LocalDate expiryDate = today.plusDays(3); //금일날짜에서 3일 후 종료
				//System.out.println(expiryDate);
				
			//LocalDate searchDate = rRDao.searchByName(name).get(0).getDue();//유통기한 검색 조회할때 가져온 유통기한, if문 이름 검색할 때 나오게 하기 
			
			due2.setDue(expiryDate); //inputDate 금일 사온 날짜로 설정

					if(expiryDate==supplierDate) {//유통기한 조회 시 기한이 종료된 날짜면
						System.out.println("유통기한 지난 식자재입니다..");
						int idx = 0;
						rRDao.deleteByIdx(idx);//식자재 삭제
						System.out.println("식자재 폐기처분하였습니다.");
						int amount = due2.getAmount();
						rRDao.updateAmount(name, amount);//식자재 재입고
						System.out.println(due2.getName()+"식자재"+due2.getAmount()+"개 재입고하였습니다.");
						due2.setDue(today);//금일 사온 날짜로 유통기한 업데이트
						System.out.println(due2.getName()+"남은 유통기한"+due+"입니다.");
					}else {
						System.out.println("식자재 "+due2.getName()+" 남은 유통기한 "+due2.getDue()+" 까지 입니다.");//종료되지 않은 날짜이면
					}
					
		

		/*기능 정의 
		* 사올 때 식자재 유통기한 설정, 유통기한 지난 후, 조회만 해서 , 유통기한 지난 재고 리스트입니다.하고 
		* boolean -> T/F , 삭제후, 다시 사올 때, now date로 3일, 예시로 해서 호출
		*/
		});
	}

	@Override
	public void getByName(Scanner sc) {
		// TODO Auto-generated method stub
			System.out.println("식자재 검색할 이름 입력");
			String name = sc.next();
			ArrayList<Ingredient> ingr = rRDao.searchByName(name);
			if(ingr.size() == 0) {
				System.out.println("식자재 없음");
		}
			else {
				System.out.println("식자재 목록");
			for(Ingredient i : ingr)
				System.out.println(i);
		}
	}



	@Override
	public void deleteIng(Scanner sc) {
		// TODO Auto-generated method stub
		System.out.println("폐기할 식자재 번호를 입력하세요");
		int idx = sc.nextInt();
		rRDao.deleteByIdx(idx);
		System.out.println(idx+"이 폐기되었습니다");
		
		
	}

	@Override
	public void inIng(Scanner sc) {
		sRDao.selectAllIng().stream().forEach(ing4->{
		// TODO Auto-generated method stub
		//주문에서 사용시 updateamount, 공급처에서 사올때
			System.out.println("공급처에서 사올 식자재를 입력해주세요");
			String name = sc.next();
			if(ing4 == null) {
				System.out.println("식자재 없음");
			}
			else {
				ing4.setName(name);//식자재넣기
				System.out.println("해당 식자재 필요한 수량을 입력해주세요 (식자재 1종당 최소 구매수량 30개 이상)");
				int amount = sc.nextInt();
					if(sRDao.searchByName(name).get(0).getAmount()>30) {
						ing4.setAmount(amount);
						System.out.println(ing4.getName()+ing4.getAmount()+"개 구매하였습니다.");
					}
					else {
						System.out.println("공급처 식자재 재고가 부족합니다. 구매불가.");
					}
		//식당 냉장고에 넣기 기능
		//식자재 30개 미만 입력시 구매 불가 기능 추가
		}
	});	
		
	}

	@Override
	public void outIng(Scanner sc) {
		// TODO Auto-generated method stub
		//food에서 주문할 때 식자재 소진
		System.out.println("음식 준비할 때 필요한 식자재 이름을 입력하세요");
		String name = sc.next();
		System.out.println("필요한 식자재 수량은 얼마입니까?");
		int amount = sc.nextInt();
		rRDao.updateAmount(name, amount);
		System.out.println(name+"이"+amount+"소진되었습니다.");
		
		
		
	}

	@Override
	public void getAllIng() {
		// TODO Auto-generated method stub
		ArrayList<Ingredient> ingr = rRDao.selectAllIng();
		if(ingr == null) {
			System.out.println("식자재가 없습니다.");
		}else {
			for(Ingredient i : ingr) 
				System.out.println(i);
		}
	}

	@Override
	public void PrintAllIng() {
		// TODO Auto-generated method stub
		System.out.println("식당 냉장고에 있는 모든 식자재 확인하기");
		ArrayList<Ingredient> ingr = rRDao.selectAllIng();
		System.out.println(ingr);

	}


	

	public void stop() {
		rRDao.stop();
		}
	

	/*
	 * 유효성체크
	 */
	public boolean isInteger(String s) {
	try{
		Integer.parseInt(s);
		return true;
	}catch(NumberFormatException e) {
		return false;
	}
	
}
	}





