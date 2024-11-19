package prob02;

public class Sol02 {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < 10; i++) {
			sb.append(i).append(" ");
		}

		for(int i = 10; i <= 18; i++) {
			sb.append(i).append(" ");
			System.out.println(sb);
		}
	}
}
