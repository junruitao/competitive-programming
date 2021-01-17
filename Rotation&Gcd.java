public class Solution {
	public static void main(String args[]) {

		Solution solution = new Solution();
		int[] m = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		boolean ans = true;
		solution.rotate(m, 2);

		System.out.println(ans);
	}

	int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	int n;
	int a[];

	public void rotate(int[] nums, int k) {
		n = nums.length;
		a = nums;
		if (n <= 1 || k < 1)
			return;
		int m = gcd(n, k);
		// System.out.println(n + " " + k);

		for (int i = 0; i < m; i++)
			rotate(i, k);
	}

	private void rotate(int start, int k) {
		int tmp = start;
		int v = a[start];

		while (true) {
			// System.out.println(start);
			start += n - k;
			start %= n;
			a[(start + k) % n] = a[start];
			if (start == tmp) { //
				a[(start + k) % n] = v;
				break;
			}
		}
		// System.out.println();

	}
}