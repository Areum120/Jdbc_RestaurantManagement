package restaurant.refrigerator.dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import board.conn.DbConnect;
import restaurant.food.vo.Ingredient;

public class RestaurantRefrigeratorDaoImpl implements RefrigeratorDao {
	
	private DbConnect db;

	public static final String FILE_PATH = "src/restaurant/files/restaurant_ingredients.dat";
	private static final String MONEY_FILE_PATH = "src/restaurant/files/total_money.dat";	
	
	
	private static ArrayList<Ingredient> ingredients; //수정
	
	private static RestaurantRefrigeratorDaoImpl RestaurantRefrigeratordaoImpl = new RestaurantRefrigeratorDaoImpl();
	
	private RestaurantRefrigeratorDaoImpl() {
		
		db = DbConnect.getInstance();
		ingredients = new ArrayList<Ingredient>(); 
//		File rf = new File(FILE_PATH);
//		boolean isExists = rf.exists();
//		if(isExists)
//			start();
	}
	
//	public static RestaurantRefrigeratorDaoImpl getInstance() {
//		return RestaurantRefrigeratordaoImpl;
//	}
	
	
//	public void start() {
//		try {
//			FileInputStream fi = new FileInputStream(FILE_PATH);
//			ObjectInputStream oi = new ObjectInputStream(fi);
//			//unchecked warning 뜸
//			ingredients = (ArrayList<Ingredient>) oi.readObject();
//			oi.close();
//			fi.close();
//		} catch (FileNotFoundException e) {
//			System.out.println("restaurant.refrigerator DaoImpl start() Error: 초기화 파일을 불러오지 못했습니다.");
//			e.printStackTrace();
//		} catch (IOException e) {
//			System.out.println("restaurant.refrigerator DaoImpl start() Error: 초기화 파일을 불러오지 못했습니다.");
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

	@Override
	public void addIng(Ingredient ing) {
		// TODO Auto-generated method stub
		Connection conn = db.conn(); // db 연결
		String sql = "insert into board values(seq_sup.nextval,1,'김',10,100,sysdate+3)";
		
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ing.getIdx());
			pstmt.setString(2, ing.getName());
			pstmt.setInt(3, ing.getAmount());
			pstmt.setInt(4, ing.getPrice());
			pstmt.setDate(5, LocalDate ());//날짜

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
		String sql = "select * from board order by idx";
		Connection conn = db.conn();

		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Ingredient ing = new Ingredient(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDate(5));
				ingredients.add(ing);
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
		for (Ingredient ingredient : this.ingredients) {
			if (name.equals(ingredient.getName())) {
				ingredient.setDue(Date);
				
//				1일이 지날수록 유통기한 변경은?
//				default가 2100-01-01
			}
		}
		stop();
		
	}

	@Override
	public void updateAmount(String name, int amount) {
		// TODO Auto-generated method stub
//		식자재 수량 수정, 입고+출고 (출고는 -)
		for (Ingredient ingredient : this.ingredients) {
			if (name.equals(ingredient.getName())) {
				ingredient.setAmount((ingredient.getAmount()+amount));//ame으로 찾아서 amount 수정
			} 
/*			식자재 amount 1회 구매량 갯수 
			김:30. 단무지:10, 쌀:50, 햄:10, 계란:20, 면사리:20, 어묵:10, 대파:10, 쑥갓:5, 유부:10
			떡 : 20, 치즈:5, 돼지고기:10, 밀가루:10, 빵가루:10, 김치:10
*/			
		}
		stop();
	}


	

	@Override
	public ArrayList<Ingredient> selectAllIng() {
		// TODO Auto-generated method stub
//		식자재 목록 확인
		return ingredients;
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
			if (in != null ) {
				ingredients.remove(in);
				System.out.println("식자재 폐기 완료하였습니다.");
				for(int j=idx-1; j<ingredients.size(); j++) {
					if(j==0) {
						for(int k=0; k<ingredients.size(); k++) {
							ingredients.get(k).setIdx(k+1);
						}
						break;
					}else {
						ingredients.get(j).setIdx(j+1);
					}//추가
				}	
			}else {
				System.out.println("없는 번호입니다. 다시 확인해주세요");
			}
				stop();
			}



	
public void stop() {
	try {
		FileOutputStream fo = new FileOutputStream(FILE_PATH);
		ObjectOutputStream oo = new ObjectOutputStream(fo);
		oo.writeObject(ingredients);
		oo.close();
		fo.close();
		}
	catch (IOException e) {
		System.out.println("restaurant.refrigerator DaoImpl stop() Error: 파일을 저장하지 못했습니다.");
		e.printStackTrace();
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


}
	
	

