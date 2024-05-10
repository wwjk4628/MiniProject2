package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import oracle.net.aso.l;

public class PhoneBook {

	public static void main(String[] args) {
		PhoneBookDAO dao = new PhoneBookDAOImpl();
		String a = "";
		create(dao);
		start();
		Scanner sc = new Scanner(System.in);
		while (!a.equals("5")) {
		a = view(dao, sc);
		}
		sc.close();
		drop(dao);

	}

	private static String view(PhoneBookDAO dao, Scanner sc) {
		
		
		System.out.print(">메뉴번호: ");
		String key = sc.nextLine();
		switch (key) { 
			case "1": 
				allList(dao);
				menu();
				break;
			case "2" :
				insert(dao, sc);
				menu();
				break;
			case "3" :
				deletList(dao, sc);
				menu();
				break;
			case "4" :
				searchList(dao, sc);
				menu();
				break;
			case "5" :
				menu();
				break;
			default : System.out.println("올바른 값 입력!");
		}
		return key;
	}
	private static void deletList(PhoneBookDAO dao, Scanner sc) {
		
		
		System.out.print("ID: ");
		Long id = sc.nextLong();
		sc.nextLine();
		boolean success = dao.delete(id);
		System.out.println("삭제 " + success);
	}
	
	private static void searchList(PhoneBookDAO dao, Scanner sc) {
		
		
		System.out.print("이름: ");
		String name = sc.nextLine();
		List<PhoneBookVO> list = new ArrayList<>();
		list = dao.search(name);
		for(PhoneBookVO vo : list) {
			System.out.printf("%d\t%s\t%s\t%s\n",
								vo.getId(),
								vo.getName(),
								vo.getHp(),
								vo.getTel());
		}
	}
	
	private static void insert(PhoneBookDAO dao, Scanner sc) {
		
		
		System.out.print("이름: ");
		String name = sc.nextLine();
		System.out.print("폰: ");
		String hp = sc.nextLine();
		System.out.print("집: ");
		String tel = sc.nextLine();
		PhoneBookVO vo = new PhoneBookVO(name, hp, tel);
		
		boolean success = dao.insert(vo);
		System.out.println(success);
	}
	private static void allList(PhoneBookDAO dao) {
		List<PhoneBookVO> list = new ArrayList<>();
		
		list = dao.getList();
		for(PhoneBookVO vo : list) {
			System.out.printf("%d\t%s\t%s\t%s\n",
								vo.getId(),
								vo.getName(),
								vo.getHp(),
								vo.getTel());
		}
	}

	private static void create(PhoneBookDAO dao) {
		
		boolean success = dao.createSeq();
		System.out.println("시퀀스 " + (success ? "성공": "실패"));
		success = dao.createTable();
		System.out.println("테이블 " + (success ? "성공": "이미있음"));
	}
	
	private static void drop(PhoneBookDAO dao) {
		
		boolean success = dao.seqDrop();
		System.out.println("시퀀스 삭제 " + (success ? "성공": "실패"));
		success = dao.tableDrop();
		System.out.println("테이블 삭제 " + (success ? "성공": "실패"));
		
	}
	private static void start() {
		System.out.println("******************************");
		System.out.println("*      전화번호 관리 프로그램      *");
		System.out.println("******************************");
		System.out.println("1.리스트 2.등록 3.삭제 4.검색 5.종료");
		System.out.println("******************************");
		System.out.println("------------------------------");
	}
	
	private static void menu() {
		System.out.println("1.리스트 2.등록 3.삭제 4.검색 5.종료");
		System.out.println("******************************");
		System.out.println("------------------------------");
	}
}
