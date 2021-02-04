package restaurant;

import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("<<<<<<<<<<<<<<< 식당 관리 프로그램  >>>>>>>>>>>>>>>");
		Menu menu = new Menu();
		menu.run(sc);
	}

}
