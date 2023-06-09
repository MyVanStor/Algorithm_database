package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MiniumKey {
	public static void main(String[] args) {
		// Tập thuộc tính ban đầu
		Set<Character> attributes = new HashSet<>();
		attributes.add('A');
		attributes.add('B');
		attributes.add('C');
		attributes.add('D');
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
		rhs3.add('D');
		rhs3.add('E');
		functionalDependencies.put(lhs3, rhs3);

		// Tìm khóa tối thiểu
		Set<Character> minimumKey = findMinimumKey(attributes, functionalDependencies);

		// In kết quả
		System.out.println("Khóa tối thiểu: " + minimumKey);
	}

	/**
	 * Phương thức tìm khóa tối thiểu
	 * @param attributes             Tập thuộc tính U
	 * @param functionalDependencies Tập các phụ thuộc hàm F trên U
	 * @return Tập khóa tối thiểu
	 */
	public static Set<Character> findMinimumKey(Set<Character> attributes,
			Map<Set<Character>, Set<Character>> functionalDependencies) {
		// Tập khóa tối thiểu cần tìm
		Set<Character> minimumKey = new HashSet<>(attributes);

		// Duyệt qua từng attribute của tập thuộc tính U
		for (Character character : attributes) {
			Set<Character> newKey = new HashSet<>(minimumKey);
			// Loại bỏ attribute hiện tại đang xét ra khỏi tập khóa tối thiểu
			newKey.remove(character);

			// Kiểm tra điều kiện tương đường
			Set<Character> check = AttributeClosure.findAttributeClosure(newKey, attributes, functionalDependencies);

			// Nếu bao đóng khác U thì không thay đổi tập khóa tối thiểu
			if (!check.containsAll(attributes)) {
				newKey.add(character);
			}
			minimumKey = newKey;
		}
		// Trả về kết quả của hàm
		return minimumKey;
	}

}
