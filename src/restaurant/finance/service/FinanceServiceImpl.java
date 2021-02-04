package restaurant.finance.service;

import java.util.Scanner;

import restaurant.finance.dao.FinanceDaoImpl;
import restaurant.finance.vo.Finance;

public class FinanceServiceImpl implements FinanceService{
	private FinanceDaoImpl daoImpl;

	public FinanceServiceImpl() {
		this.daoImpl = FinanceDaoImpl.getInstance(); //싱글톤 객체를 받아온다
		}

	/**
	 * 유효한 금액과 메세지를 받아 입금하는 메서드
	 */
	@Override
	public void inputMoney(Scanner sc) {
		System.out.println("입금 할 금액을 입력하세요 (0 이상 정수)");
		String amount = sc.next();
		
		//공백입력, 정수아닌 값 확인
		while(amount.length()==0 && isPositiveInteger(amount)==false) { 
			System.out.println("유효한 금액을 다시 입력해주세요");
			amount = sc.next();
		}
		
		System.out.println("입금 메세지를 입력하세요 (생략 시 기타로 입력)");
		sc.nextLine(); // 입력버퍼 비우기
		String message = sc.nextLine();
		if(message.length()==0) message = "기타"; //공백 입력시 기타
		
		daoImpl.input(Integer.parseInt(amount), message);
		System.out.println("입력되었습니다");
	}

	/**
	 * 유효한 금액과 메세지를 받아 출금하는 메서드
	 */
	@Override
	public void outputMoney(Scanner sc) {
		System.out.println("출금 할 금액을 입력하세요 (0 이상 정수)");
		String amount = sc.next();
		
		//공백입력, 정수아닌 값 확인
		while(amount.length()==0 && isPositiveInteger(amount)==false) { 
			System.out.println("유효한 금액을 다시 입력해주세요");
			amount = sc.next();
		}
		//잔액부족
		while(Finance.getTOTAL_MONEY() - Integer.parseInt(amount) <0) {
			System.out.println("잔액 부족입니다. 다시 입력해주세요");
			amount = sc.next();
		}
		
		System.out.println("출금 메세지를 입력하세요 (생략 시 기타로 입력)");
		sc.nextLine(); // 입력버퍼 비우기
		String message = sc.nextLine();
		if(message.length()==0) message = "기타"; //공백 입력시 기타
		daoImpl.output(Integer.parseInt(amount), message);
		System.out.println("입력되었습니다");
	}

	/**
	 * 입출금 기록을 모두 출력하는 메서드
	 */
	@Override
	public void printAllFinanceRecords() {
		System.out.println("=====================입출금 기록 출력=======================");
		for(Finance f : daoImpl.getAllfinanceRecords()) {
			System.out.println(f);
		}
	}
	
	/**
	 * 현재 금액을 출력하는 메서드
	 */
	@Override
	public void printTotalMoney() {
		System.out.println("현재 총 금액 : "+Finance.getTOTAL_MONEY()+"원");
	}

	/**
	 * 정수판별 메서드
	 * @param s 문자열로 정수를 입력받기
	 * @return NumberFormatException이 발생할 시 정수가 아님 false 리턴
	 */
	public boolean isInteger(String s) {
		try{
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
		
	}
	
	/**
	 * 양의 정수판별 메서드
	 * @param s 문자열로 정수를 입력받기
	 * @return NumberFormatException이 발생할 시 정수가 아님 false 리턴
	 */
	public boolean isPositiveInteger(String s) {
		try{
			if(Integer.parseInt(s)>=0) {
				return false;
			}
			return true;
		}catch(NumberFormatException e) {
			return false;
		}
		
	}
	
	
	
	
}
