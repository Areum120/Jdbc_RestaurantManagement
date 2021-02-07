package restaurant.refrigerator.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import conn.DbConncect;
import restaurant.food.vo.Ingredient;

import javax.naming.InsufficientResourcesException;

public class RestaurantRefrigeratorDaoImpl implements RefrigeratorDao {

	private DbConncect db;


	private static RestaurantRefrigeratorDaoImpl RestaurantRefrigeratordaoImpl = new RestaurantRefrigeratorDaoImpl();
	
	private RestaurantRefrigeratorDaoImpl() {
		
		this.db = db.getInstance();
//		File rf = new File(FILE_PATH);
//		boolean isExists = rf.exists();
//		if(isExists)
	}
	

	@Override
	public void addIng(Ingredient ing) {
		// TODO Auto-generated method stub
		Connection conn = db.conn(); // db 연결
		String sql = "insert into restaurant_ingredients values(?, ?, ?, ?, sysdate+3)";
		
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ing.getIdx());
			pstmt.setString(2, ing.getName());
			pstmt.setInt(3, ing.getAmount());
			pstmt.setInt(4, ing.getPrice());
//			pstmt.setObject(5, ing.getDue());//날짜

			cnt = pstmt.executeUpdate();
			System.out.println(cnt + "줄 insert 됨");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {

				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
//		ingredients.add(ing);
////		식자재 입고(name, amount, price, due)
//		stop();

//	}
//	
//	public static ArrayList<Ingredient> getIng(){
//		return ingredients;
	}



	@Override
	public ArrayList<Ingredient> searchByName(String name) {
		/*
		 * select * from refrigerator
		 * where name = name
		 */
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
//      식자재 검색 및 확인
		
		ResultSet rs = null;
		String sql = "select idx name, amount, price, due from restaurant_ingredients where name=?";
		Connection conn = db.conn();

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				int idx = rs.getInt(1);
				String name1 = rs.getString(2);
//				int amount = rs.getInt(3);
//				int price = rs.getInt(4);
//				LocalDate due = rs.getObject(5, LocalDate.class);

				System.out.println("idx" + idx);
				System.out.println("name1" + name1);
//				Ingredient ing = new Ingredient(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDate(5));
//				ingredients.add(ing);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ingredients;
		
		
		
		
//		for (Ingredient ingredient : this.ingredients) {//안에 찾는 걸 스캔
//			if (name.equals(ingredient.getName())) {
//				ingredients.add(ingredient);
//			}
//		}
//		return ingredients;
	}

	@Override
	public void updateDue(String name, LocalDate Date) {
		// 유통기한 수정

	}

	@Override
	public void updateAmount(String name, int amount) {
		// TODO Auto-generated method stub
//		식자재 수량 수정, 입고+출고 (출고는 -)
	}


	

	@Override
	public ArrayList<Ingredient> selectAllIng() {
		// TODO Auto-generated method stub
//		식자재 목록 확인
		return new ArrayList<>();
	}



	public void deleteByIdx(int idx) {
		// TODO Auto-generated method stub
//		유통기한만료, 재료 소진시 식자재 삭제
		/*for (int i =0;  i<ingredients.size(); i++) {
			if(ingredients.get(i).getName().equals(idx))
				ingredients.remove(i);
			}//추가
			*/
		Ingredient in = searchByIdx(idx);
		if (in != null) {
			System.out.println("식자재 폐기 완료하였습니다.");
		} else {
			System.out.println("없는 번호입니다. 다시 확인해주세요");
		}
	}

	public Ingredient searchByIdx(int idx) {
		Ingredient in = selectAllIng().get(idx-1);
		if (in == null) {
			System.out.println("음식이 존재하지 않습니다.");
			return null;
		}else {
			return in;
		}
	}

	@Override
	public void deleteByName(String name) {
		// TODO Auto-generated method stub

	}


	public static void main(String[] args) {
		RefrigeratorDao refrigeratorDao = new RestaurantRefrigeratorDaoImpl();
//		Ingredient ingredient = new Ingredient("면사리", 10, 1000, LocalDate.now().plusDays(3));
//		ingredient.setIdx(7);
//		refrigeratorDao.addIng(ingredient);

		refrigeratorDao.searchByName("면사리");
	}
}
