package prob03;

import java.util.Scanner;

public class Sol03 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while(true) {
			System.out.print("수를 입력 하세요 : ");
			int num = scanner.nextInt();

			int sum = 0;
			if(num % 2 != 0) { // 홀수
				for(int i = 1; i <= num; i += 2) {
					sum += i;
				}
			} else { // 짝수
				for(int i = 0; i <= num; i += 2) {
					sum += i;
				}
			}

			System.out.println("결과값: " + sum);
		}

    }
}
