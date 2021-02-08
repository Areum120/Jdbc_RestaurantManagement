package restaurant.refrigerator.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import conn.DbConnect;//오타수정
import restaurant.food.vo.Ingredient;

public class RestaurantRefrigeratorDaoImpl implements RefrigeratorDao {

	private DbConnect db;

	private static RestaurantRefrigeratorDaoImpl RestaurantRefrigeratordaoImpl = new RestaurantRefrigeratorDaoImpl();
	
	public static RestaurantRefrigeratorDaoImpl getInstance() {
		return RestaurantRefrigeratordaoImpl;
	}//getinstance
	
	private RestaurantRefrigeratorDaoImpl() {
		
		this.db = db.getInstance();

	}
	

	@Override
	public void addIng(Ingredient ing) {
		// TODO Auto-generated method stub
		Connection conn = db.conn(); // db 연결
		String sql = "insert into restaurant_ingredients values(?, ?, ?, ?, sysdate+3)";
		
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
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
			
			
			//냉장고에 값 저장
			ingredients.add(ing);
			
			
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

	}



	@Override
	public ArrayList<Ingredient> searchByName(String name) {

		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
//      식자재 검색 및 확인
		
		ResultSet rs = null;
		String sql = "select idx, name, amount, price, due from restaurant_ingredients where name=?";
		Connection conn = db.conn();
		Ingredient ing = null;//검색결과 값 담기
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int idx = rs.getInt(1);
				String name1 = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				LocalDate due = rs.getDate(5).toLocalDate();//convertSqldate

				System.out.println("idx" + idx);
				System.out.println("name" + name1);
				System.out.println("amount"+amount);
				System.out.println("price"+price);
				System.out.println("due"+due);
				
				ingredients.add(ing);//ArrayList에 넣기
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
		
		
	}
	
	
	public ArrayList<Ingredient> searchByIdx(int idx) {
		
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		ResultSet rs = null;
		String sql = "select idx, name, amount, price, due from restaurant_ingredients where idx=?";
		Connection conn = db.conn();
		Ingredient ing = null;//검색결과 값 담기
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int idx1 = rs.getInt(1);
				String name = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				LocalDate due = rs.getDate(5).toLocalDate();//convertSqldate

				System.out.println("idx" + idx1);
				System.out.println("name" + name);
				System.out.println("amount"+amount);
				System.out.println("price"+price);
				System.out.println("due"+due);
				
				ingredients.add(ing);//ArrayList에 넣기
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
	}

	

	@Override
	public ArrayList<Ingredient> selectAllIng() {
		// TODO Auto-generated method stub
//		식자재 목록 확인
		
		ArrayList<Ingredient> list = new ArrayList<Ingredient>();
		ResultSet rs = null;
		String sql = "select*from restaurant_ingredients order by idx";
		Connection conn = db.conn();
		Ingredient ing = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				
				int idx1 = rs.getInt(1);
				String name = rs.getString(2);
				int amount = rs.getInt(3);
				int price = rs.getInt(4);
				LocalDate due = rs.getDate(5).toLocalDate();
				
				System.out.println("idx"+idx1+"name"+name+"amount"+amount+"price"+price+"due"+due);
				list.add(ing);
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
		return list;
	}
	
	
	@Override
	public void updateDue(String name, LocalDate Date) {
		// 유통기한 수정
	
		
		Connection conn = db.conn(); // db 연결
		String sql = "update restaurant_ingredients set due=sysdate+3 where due<sysdate";

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
			
			int r = pstmt.executeUpdate();
			System.out.println(r+"개 update 완료");
			pstmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("update 실패");

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

	@Override
	public void updateAmount(String name, int amount) {
		// TODO Auto-generated method stub
//		식자재 수량 수정, 입고+출고 (출고는 -)
		Connection conn = db.conn(); // db 연결
		String sql = "update restaurant_ingredients set amount=amount+? where name=?";
		
	
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, amount);
			pstmt.setString(2, name);
			
			System.out.println(name+amount+"개 update 완료");
			int r = pstmt.executeUpdate();
			System.out.println(r+"개 update 완료");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("update 실패");

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

	public void deleteByIdx(int idx) {
		// TODO Auto-generated method stub
//		유통기한만료, 재료 소진시 식자재 삭제
		/*for (int i =0;  i<ingredients.size(); i++) {
			if(ingredients.get(i).getName().equals(idx))
				ingredients.remove(i);
			}//추가
			*/
		Connection conn = db.conn(); // db 연결
		String sql = "delete restaurant_ingredients where idx=?";
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idx);
			System.out.println(idx+"가 삭제되었습니다.");
			int r = pstmt.executeUpdate();
			System.out.println(r + "줄 delete 됨");
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
	}


	@Override
	public void deleteByName(String name) {
		// TODO Auto-generated method stub
		//냉장고에선 사용하지 않는 기능
	}


	public static void main(String[] args) {//test용
		RefrigeratorDao refrigeratorDao = new RestaurantRefrigeratorDaoImpl();
//		RestaurantRefrigeratorDaoImpl RDaoImpl = new RestaurantRefrigeratorDaoImpl();
		
		//insert확인용
//		Ingredient ingredient = new Ingredient("계란", 10, 200, LocalDate.now().plusDays(3));
//		ingredient.setIdx(8);
//		refrigeratorDao.addIng(ingredient);
		
		//serchbyname 확인용
//		refrigeratorDao.searchByName("면사리");
		//setchbyidx 확인용
//		RDaoImpl.searchByIdx(2);
		//selectAll 확인용
//		RDaoImpl.selectAllIng();
		//updatedue 확인용
//		refrigeratorDao.updateDue("", LocalDate.now());
//		updateamount 확인용
//		refrigeratorDao.updateAmount("김", 0);
//		delete 확인용
		refrigeratorDao.deleteByIdx(2);
	}
}
