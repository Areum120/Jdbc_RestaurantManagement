package restaurant.supplier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import restaurant.Main;
import restaurant.finance.dao.FinanceDaoImpl;
import restaurant.finance.vo.Finance;
import restaurant.food.vo.Ingredient;
import restaurant.refrigerator.RefrigeratorServiceImpl;
import restaurant.refrigerator.dao.RestaurantRefrigeratorDaoImpl;
import restaurant.supplier.dao.SupplyDaoImpl;

public class SupplyServiceImpl implements SupplyService {

	private SupplyDaoImpl sdao;
	private RestaurantRefrigeratorDaoImpl rRDao;
	private FinanceDaoImpl fDao;
	private RefrigeratorServiceImpl refService;

	public SupplyServiceImpl() {
		sdao = restaurant.supplier.dao.SupplyDaoImpl.getInstance();
		refService = new RefrigeratorServiceImpl();
		rRDao = restaurant.refrigerator.dao.RestaurantRefrigeratorDaoImpl.getInstance();
		fDao = restaurant.finance.dao.FinanceDaoImpl.getInstance();
	}

	@Override
	public void getByName(Scanner sc) {
		System.out.println("찾을 식자재 이름을 입력하세요");
		String name = sc.next();
		ArrayList<Ingredient> list = sdao.searchByName(name);
		if (list.size() == 0) {
			System.out.println("식자재가 없습니다.");
		} else {
			System.out.println("===============찾은 식자재 목록===============");
			for (Ingredient i : list)
				System.out.println(i);
		}

	}

	@Override
	public void refundIng(Scanner sc) {
		System.out.println("반품할 식자재 이름을 입력하세요");
		String name = sc.next();
		System.out.println("반품할 수량을 입력하세요");
		int amount = sc.nextInt();
		sdao.updateAmount(name, amount);
		System.out.println(amount + "개 입력 되었습니다");

	}

	// 식자재 구매
	@Override
	public void buyIng(Scanner sc) {

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
		int price = sdao.searchByName(name).get(0).getPrice();

		if (Finance.getTOTAL_MONEY() - price * amount >= 0) {
			Finance.setTOTAL_MONEY(Finance.getTOTAL_MONEY() - price);
			System.out.println(amount + "개 구매 되었습니다");
			if (rIng.size() == 0) {// 냉장고에 재고가 없으면 새로 추가
				LocalDate expiryDate = LocalDate.now().plusDays(3); // 유통기한 3일
				fDao.input(-amount, name + "구매");
				System.out.println("냉장고 재고 확인:" + name + amount + "개 입니다.");
				rRDao.addIng(new Ingredient(name, amount, sdao.searchByName(name).get(0).getPrice(), expiryDate));
			} else {
				LocalDate expiryDate = LocalDate.now().plusDays(3); // 유통기한 3일
				fDao.input(-amount, name + "구매");
				rRDao.updateAmount(name, amount);
				sdao.updateAmount(name, -amount);
				rRDao.updateDue(name, expiryDate);
				System.out.println("냉장고 재고 확인:" + name + rRDao.searchByName(name).get(0).getAmount() + "개 입니다.");
			}
		} else {// 냉장고에 재고가 있으면 개수 추가
			System.out.println("잔액이 부족하여 구매 불가");
				
			}
		save();
		}




	@Override
	public void getAllIng() {
		ArrayList<Ingredient> list = sdao.selectAllIng();
		if (list == null) {
			System.out.println("식자재가 없습니다.");
		} else {
			for (Ingredient i : list)
				System.out.println(i);
		}

	}

	/*
	 * 유효성체크
	 */
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	// 파일에 데이터 저장
	public void save() {
		sdao.save();
	}
}
