package restaurant.refrigerator;

import java.time.LocalDate;
import java.util.Scanner;

public interface RefrigeratorService {

	
	public void BuyIng(Scanner sc);//공급처 재료, totalmoney차감
	public void getByName(Scanner sc); //dao search 호출, void 반환 수정
	public void editDue(String name, LocalDate Date);//dao updateDAte 호출
	public void deleteIng(Scanner sc);
	public void inIng(Scanner sc);
	public void outIng(Scanner sc);
	public void getAllIng();
	public void PrintAllIng(); //출력 추가
}
