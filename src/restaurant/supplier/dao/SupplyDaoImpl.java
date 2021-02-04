package restaurant.supplier.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import restaurant.food.vo.Ingredient;
import restaurant.refrigerator.dao.RefrigeratorDao;
/**
 * 
 * @author kyoungju
 *
 */
public class SupplyDaoImpl implements RefrigeratorDao{
	
	public static final String FILE_PATH = "src/restaurant/files/supply_ingredients.dat";
	private ArrayList<Ingredient> ingredients;
	private static SupplyDaoImpl daoImpl = new SupplyDaoImpl();
	
	/**
	 * Dao 생성자
	 * 파일에 객체정보를 넣어주기 위해 init()을 호출
	 * 생성시 파일에서 리스트를 받아옴
	 */
	private SupplyDaoImpl() {
		ingredients = new ArrayList<Ingredient>(); 
		File rf = new File(FILE_PATH);
		boolean isExists = rf.exists();
		if(!isExists)
			init();
		start();
		
	}
	
	public static SupplyDaoImpl getInstance() {
		return daoImpl;
	}
	
	/**
	 * 
	 * @return 멤버변수로 저장된 ingredients 반환
	 */


	public ArrayList<Ingredient> getIngredients() { //selectAllIng()랑 중복됩니다. printAllIng로 출력메소드를 만드는게 어떨지?
		return ingredients;
	}
	


	/**
	 * 실행 시 파일에서 식자재 리스트 받아와서 ingredients에 초기화
	 */
	public void start() {
		try {
			FileInputStream fi = new FileInputStream(FILE_PATH);
			ObjectInputStream oi = new ObjectInputStream(fi);
			//unchecked warning 뜸
			ingredients = (ArrayList<Ingredient>) oi.readObject();
			oi.close();
			fi.close();
		} catch (FileNotFoundException e) {
			System.out.println("restaurant.supplier DaoImpl start() Error: 초기화 파일을 불러오지 못했습니다.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("restaurant.supplier DaoImpl start() Error: 초기화 파일을 불러오지 못했습니다.");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ingredients에 ingredient 객체 하나 추가
	 */
	@Override
	public void addIng(Ingredient ing) {
		ingredients.add(ing);
	}

	/**
	 * @return 이름으로 찾은 결과 리스트를 반환
	 */
	@Override
	public ArrayList<Ingredient> searchByName(String name) {
		ArrayList<Ingredient> sl = new ArrayList<>();
		for(int i = 0 ; i< ingredients.size(); i++) {
			if(ingredients.get(i).getName().equals(name))
				sl.add(ingredients.get(i));
		}
		return sl;
	}

	/**
	 * 식자재의 날짜를 바꿔주는 메서드
	 * 이름이 같은 객체는 전부 바꾼다
	 */
	@Override
	public void updateDue(String name, LocalDate date) {
		for(int i = 0; i < ingredients.size(); i++) {
			if(ingredients.get(i).getName().equals(name)){
				ingredients.get(i).setDue(date);
			}
		}
	}

	/**
	 * 식자재의 재고량을 바꿔주는 메서드
	 * 이름이 같은 객체는 전부 바꾼다
	 */
	@Override
	public void updateAmount(String name, int newAmount) {
		int oriAmount = 0;
		for(int i = 0; i < ingredients.size(); i++) {
			if(ingredients.get(i).getName().equals(name)){
				oriAmount = ingredients.get(i).getAmount();
				ingredients.get(i).setAmount(oriAmount+newAmount);
			}
		}
	}

	/**
	 * 식자재의 이름을 받아 삭제하는 메서드
	 */
	@Override
	public void deleteByName(String name) {
		for(int i = 0 ; i< ingredients.size(); i++) {
			if(ingredients.get(i).getName().equals(name))
				ingredients.remove(i);
		}
	}
	
	/**
	 * @return 식자재 목록을 반환
	 */
	@Override
	public ArrayList<Ingredient> selectAllIng() {
		return ingredients;
	}
	
	/**
	 * 첫 실행시만 파일에 초기화. 이후 주석처리
	 */

	public void init() { 
		
		ArrayList<Ingredient> il = new ArrayList<Ingredient>();
		il.add(new Ingredient("김", 9999999, 100, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("단무지", 9999999, 50, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("쌀", 9999999, 200, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("햄", 9999999, 500, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("계란", 9999999, 200, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("면사리", 9999999, 200, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("어묵", 9999999, 250, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("대파", 9999999, 50, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("쑥갓", 9999999, 80, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("유부", 9999999, 100, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("떡", 9999999, 300, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("치즈", 9999999, 400, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("돼지고기", 9999999, 800, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("밀가루", 9999999, 150, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("빵가루", 9999999, 150, LocalDate.of(2100, 1, 1)));
		il.add(new Ingredient("김치", 9999999, 70, LocalDate.of(2100, 1, 1)));
		
		try {
			FileOutputStream fo = new FileOutputStream(FILE_PATH);
			ObjectOutputStream oo = new ObjectOutputStream(fo);	
			oo.writeObject(il);
			oo.close();
			fo.close();
			}
		catch (IOException e) {
			System.out.println("restaurant.supplier DaoImpl init() Error: 초기화 파일을 불러오지 못했습니다.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 호출시 파일에 식사재 리스트 저장
	 */
	public void save() {
		try {	
			FileOutputStream fo = new FileOutputStream(FILE_PATH);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(ingredients);
			oo.close();
			fo.close();
			}
		catch (IOException e) {
			System.out.println("restaurant.supplier DaoImpl stop() Error: 파일을 저장하지 못했습니다.");
			e.printStackTrace();
		}
	}

	@Override
	public void deleteByIdx(int idx) {
		// TODO Auto-generated method stub
		
	}




}
