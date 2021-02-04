package restaurant.finance.service;

import java.util.Scanner;

public interface FinanceService {
	public void inputMoney(Scanner sc); //입금
	public void outputMoney(Scanner sc); //출금
	public void printAllFinanceRecords(); //입출금 기록 출력
	public void printTotalMoney(); //총금액 출력
}
