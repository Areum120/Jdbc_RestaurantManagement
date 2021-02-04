package restaurant.finance.dao;

import restaurant.finance.vo.*;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * 
 * @author kyoungju
 * 입금출금을 기록하여 재무 finance를 관리하는 dao
 *
 */
public interface FinanceDao {
	public void input(int amount,String message);  //입금 + 기록
	public void output(int amount,String message); //출금 + 기록
	public ArrayList<Finance> getAllfinanceRecords(); //입출금 기록 확인
	public void start();//파일 초기화
	public void save();//파일 저장
}
