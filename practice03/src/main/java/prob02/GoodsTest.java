package prob02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class GoodsTest {
	private static final int COUNT_GOODS = 3;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		Goods[] goods = new Goods[COUNT_GOODS];

		// 상품 입력
		for(int i = 0; i < COUNT_GOODS; i++) {
			st = new StringTokenizer(br.readLine());
			goods[i] = new Goods(st.nextToken(), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}

		// 상품 출력
		for(int i = 0; i < COUNT_GOODS; i++) {
			goods[i].printInfo();
		}
	}
}
