package oraclepro;

import java.util.List;
import java.util.Scanner;

public class PhoneApp {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		PhoneDao phoneDao = new PhoneDao();
		boolean flag = false;
		List<PersonVo> PersonList;

		String name;
		String hp;
		String company;

		while (true) {

			System.out.println("*******************************");
			System.out.println("*      전화번호 관리 프로그램      *");
			System.out.println("*******************************");
			System.out.println("");

			while (true) {

				System.out.println("1.리스트 2.등록 3.수정 4.삭제 5.검색 6.종료");
				System.out.println("-------------------------------------");
				System.out.print(">메뉴번호: ");
				int num = sc.nextInt();

				switch (num) {

				case 1:
					System.out.println("<1. 리스트>");
					PersonList = phoneDao.PersonList();
					printList(PersonList);
					break;

				case 2:
					System.out.println("<2. 등록>");
					System.out.print(">이름: ");
					name = sc.next();
					System.out.print(">휴대전화: ");
					hp = sc.next();
					System.out.print(">회사전화: ");
					company = sc.next();

					PersonVo iPersonVo = new PersonVo(name, hp, company);
					int iCount = phoneDao.personInsert(iPersonVo);
					if (iCount > 0) {
						System.out.println("[등록되었습니다.]");
					} else {
						System.out.println("[관리자에게 문의하세요(" + iCount + ")]");
					}

					System.out.println("");

					break;

				case 3:
					System.out.println("<3. 수정>");
					System.out.print(">번호: ");
					int lNum = sc.nextInt();
					System.out.print(">이름: ");
					name = sc.next();
					System.out.print(">휴대전화: ");
					hp = sc.next();
					System.out.print(">회사전화: ");
					company = sc.next();

					int uCount = phoneDao.personUpdate(name, hp, company, lNum);

					System.out.println("");

					break;

				case 4:
					System.out.println("<4. 삭제>");
					System.out.print(">번호: ");
					int search = sc.nextInt();
					int dCount = phoneDao.personDelete(search);
					System.out.println("[삭제되었습니다]");
					System.out.println("");

					break;

				case 5:
					System.out.println("<5. 검색>");
					System.out.print("검색어: ");
					String word = sc.next();
					printList(phoneDao.personSearch(word));
					System.out.println("");

					break;

				case 6:
					System.out.println("<6. 종료>");
					flag = true;
					break;

				default:
					System.out.println("[다시 입력해주세요]");
					System.out.println("");
					break;

				}
				if (flag == true) {
					break;
				}
			}
			System.out.println("");
			System.out.println("*******************************");
			System.out.println("*           감사합니다	      *");
			System.out.println("*******************************");
			System.out.println("");
			break;
		}

		sc.close();

	}

	public static void printList(List<PersonVo> personList) {
		for (int i = 0; i < personList.size(); i++) {

			PersonVo personVo = personList.get(i);
			System.out.println(personVo.getPerson_id() + "," + personVo.getName() + "," + personVo.getHp() + ","
					+ personVo.getCompany());
		}
		System.out.println("");
	}
}
