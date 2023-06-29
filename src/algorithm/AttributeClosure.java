package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AttributeClosure {
	public static void main(String[] args) {
		// Tập thuộc tính ban đầu
		Set<Character> attributes = new HashSet<>();
		attributes.add('A');
		attributes.add('B');
		attributes.add('C');
		attributes.add('C');
		attributes.add('E');

		// Tập phụ thuộc hàm ban đầu
		Map<Set<Character>, Set<Character>> functionalDependencies = new HashMap<>();
		Set<Character> lhs1 = new HashSet<>();
		lhs1.add('A');
		lhs1.add('B');
		Set<Character> rhs1 = new HashSet<>();
		rhs1.add('C');
		functionalDependencies.put(lhs1, rhs1);

		Set<Character> lhs2 = new HashSet<>();
		lhs2.add('A');
		lhs2.add('C');
		Set<Character> rhs2 = new HashSet<>();
		rhs2.add('B');
		functionalDependencies.put(lhs2, rhs2);

		Set<Character> lhs3 = new HashSet<>();
		lhs3.add('B');
		lhs3.add('C');
		Set<Character> rhs3 = new HashSet<>();
		rhs3.add('E');
		rhs3.add('D');
		functionalDependencies.put(lhs3, rhs3);

		Set<Character> inputAttributes = new HashSet<>();
		inputAttributes.add('C');
		inputAttributes.add('A');
		inputAttributes.add('D');
		inputAttributes.add('E');
		inputAttributes.add('B');

		// Tìm bao đóng
		Set<Character> closure = findAttributeClosure(inputAttributes, attributes, functionalDependencies);

		// In kết quả
		System.out.println("Bao đóng: " + closure);
	}

	/**
	 * Hàm tìm bao đóng của một tập thuộc tính đối với tập phụ thuộc hàm(Slide
	 * chương 6 - tr14)
	 * 
	 * @param inputAttributes        Các thuộc tính xuất phát
	 * @param attributes             Tập thuộc tính U
	 * @param functionalDependencies Tập các phụ thuộc hàm F trên U
	 * @return Bao đóng của tập thuộc tính U đối với tập phụ thuộc hàm F trên U
	 */
	public static Set<Character> findAttributeClosure(Set<Character> inputAttributes, Set<Character> attributes,
			Map<Set<Character>, Set<Character>> functionalDependencies) {
		// Bao đóng cần tìm
		Set<Character> closure = new HashSet<>(inputAttributes);
		boolean changed;

		do {
			// Xác định tại vòng lặp hiện tại, bao đóng có thay đổi hay không
			changed = false;

			// Duyệt qua tất cả các phụ thuộc hàm bằng vòng lặp for_each
			for (Map.Entry<Set<Character>, Set<Character>> entry : functionalDependencies.entrySet()) {
				// Tập thuộc tính bên trái của quan hệ
				Set<Character> lhs = entry.getKey();
				// Tập thuộc tính bên phải của quan hệ
				Set<Character> rhs = entry.getValue();

				// Kiểm tra xem lhs có thuộc closure không
				if (closure.containsAll(lhs)) {
					// Nếu rhs không thuộc closure, thì thêm vào closure và đánh dấu đã thay đổi
					if (!closure.containsAll(rhs)) {
						closure.addAll(rhs);
						changed = true;
					}
				}
			}

		} while (changed);
		// Trả về bao đóng tìm được
		return closure;
	}
}
