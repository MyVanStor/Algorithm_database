package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FindNonRedundant {
	public static void main(String[] args) {
		// Tập thuộc tính ban đầu
		Set<Character> attributes = new HashSet<>();
		attributes.add('A');
		attributes.add('B');
		attributes.add('C');
		attributes.add('D');
		attributes.add('E');
		attributes.add('F');
		attributes.add('G');
		attributes.add('H');
		attributes.add('I');
		attributes.add('J');

		// Tập phụ thuộc hàm ban đầu
		Map<Set<Character>, Set<Character>> functionalDependencies1 = new HashMap<>();
		Set<Character> lhs11 = new HashSet<>();
		lhs11.add('A');
		Set<Character> rhs11 = new HashSet<>();
		rhs11.add('C');
		rhs11.add('B');
		rhs11.add('D');
		functionalDependencies1.put(lhs11, rhs11);

		Set<Character> lhs12 = new HashSet<>();
		lhs12.add('D');
		lhs12.add('C');
		Set<Character> rhs12 = new HashSet<>();
		rhs12.add('B');
		functionalDependencies1.put(lhs12, rhs12);

		Set<Character> lhs13 = new HashSet<>();
		lhs13.add('E');
		lhs13.add('F');
		Set<Character> rhs13 = new HashSet<>();
		rhs13.add('G');
		rhs13.add('H');
		functionalDependencies1.put(lhs13, rhs13);

		Set<Character> lhs14 = new HashSet<>();
		lhs14.add('E');
		Set<Character> rhs14 = new HashSet<>();
		rhs14.add('G');
		rhs14.add('I');
		rhs14.add('J');
		functionalDependencies1.put(lhs14, rhs14);

		Set<Character> lhs15 = new HashSet<>();
		lhs15.add('I');
		Set<Character> rhs15 = new HashSet<>();
		rhs15.add('J');
		functionalDependencies1.put(lhs15, rhs15);

		System.out.println(findNonRedundant(attributes, functionalDependencies1));
	}

	/**
	 * Phương thức tìm tập phụ thuộc hàm không dư thừa
	 * @param attributes				Tập thuộc tính U
	 * @param functionalDependencies	Tập phụ thuộc hàm F0
	 * @return 							Tập phụ thuộc hàm không dư thừa
	 */
	public static Map<Set<Character>, Set<Character>> findNonRedundant(Set<Character> attributes,
			Map<Set<Character>, Set<Character>> functionalDependencies) {
		// Tập phụ thuộc hàm không dư thừa cần tìm
		Map<Set<Character>, Set<Character>> answer = new HashMap<>(functionalDependencies);
		// Chứa các phụ thuộc hàm dư thừa
		Set<Set<Character>> valueRemove = new HashSet<>();

		Set<Set<Character>> keySets = answer.keySet();

		// Xét từng phụ thuộc hàm trong tập phụ thuộc hàm F0
		for (Set<Character> key : keySets) {
			Map<Set<Character>, Set<Character>> value = new HashMap<>(functionalDependencies);
			
			Set<Character> valueKey = value.get(key);
			
			// Loại bỏ phụ thuộc hàm đang xét ra khỏi F0 được F
			value.remove(key);

			// Tìm bao đóng của phụ thuộc hàm đang xét trên F
			Set<Character> attributeClosure = AttributeClosure.findAttributeClosure(key, attributes, value);
			// Nếu bao đóng chứa các thuộc tính đích tức nó dư thừa
			if (attributeClosure.containsAll(valueKey)) {
				// Thêm phụ thuộc hàm cần loại bỏ vào valueRemove
				valueRemove.add(key);
			}
		}
		// Loại bỏ các phụ thuộc hàm dư thừa
		for (Set<Character> remove : valueRemove) {
			answer.remove(remove);
		}

		return answer;
	}
}
