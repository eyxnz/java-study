package prob05;

public class Sol05 {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();

		for(int i = 1; i <= 100; i++) {
			String num = String.valueOf(i);

			int cnt = 0;
			for(int j = 0; j < num.length(); j++) {
				int n = num.charAt(j) - '0';
				if(n == 3 || n == 6 || n == 9) {
					cnt++;
				}
			}

			if(cnt == 0) {
				continue;
			}

			sb.append(num).append(" ");
			for(int j = 0; j < cnt; j++) {
				sb.append("짝");
			}
			sb.append("\n");
		}

		System.out.println(sb);
	}
}
