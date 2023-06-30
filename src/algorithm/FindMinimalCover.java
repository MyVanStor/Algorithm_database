package algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FindMinimalCover {
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
		
		// Tập phụ thuộc hàm ban đầu
		Map<Set<Character>, Set<Character>> functionalDependencies = new HashMap<>();
		Set<Character> lhs11 = new HashSet<>();
		lhs11.add('A');
		Set<Character> rhs11 = new HashSet<>();
		rhs11.add('B');
		functionalDependencies.put(lhs11, rhs11);

		Set<Character> lhs12 = new HashSet<>();
		lhs12.add('A');
		lhs12.add('B');
		lhs12.add('C');
		lhs12.add('D');
		Set<Character> rhs12 = new HashSet<>();
		rhs12.add('E');
		functionalDependencies.put(lhs12, rhs12);

		Set<Character> lhs13 = new HashSet<>();
		lhs13.add('E');
		lhs13.add('F');
		Set<Character> rhs13 = new HashSet<>();
		rhs13.add('G');
		functionalDependencies.put(lhs13, rhs13);

		Set<Character> lhs14 = new HashSet<>();
		lhs14.add('A');
		lhs14.add('C');
		lhs14.add('D');
		lhs14.add('F');
		Set<Character> rhs14 = new HashSet<>();
		rhs14.add('E');
		rhs14.add('G');
		functionalDependencies.put(lhs14, rhs14);
		System.out.println("F0 = " + functionalDependencies);
		System.out.println("Key = " +findMinimalCover(attributes, functionalDependencies));
	}

	public static Map<Set<Character>, Set<Character>> findMinimalCover(Set<Character> attributes,
			Map<Set<Character>, Set<Character>> functionalDependencies) {

		Map<Set<Character>, Set<Character>> answer = new HashMap<>();

		Set<Set<Character>> keySets = functionalDependencies.keySet();
		// Bước 1: Biến đổi F về dạng F1 = {Li -> Aj}
		Map<Set<Character>, Set<Character>> F1 = new HashMap<>();
		for (Set<Character> key : keySets) {
			Set<Character> valueKey = functionalDependencies.get(key);
			for (Character character : valueKey) {
				Set<Character> value = new HashSet<>();
				value.add(character);
				F1.put(key, value);
			}
		}
		System.out.println("F1 = " +F1);
		// Bước 2: Loại bỏ thuộc tính thừa trong vế trái của các phụ thuộc hàm
		Map<Set<Character>, Set<Character>> F2 = new HashMap<>();
		for (Set<Character> key : keySets) {
			Set<Character> valueKey = F1.get(key);
			if (key.size() > 1) {
				for (Character character : key) {
					Set<Character> keyF2 = new HashSet<>();
					keyF2.add(character);
					Set<Character> valueF2 = new HashSet<>(valueKey);
					if (F1.containsKey(keyF2)) {
						if (!F1.containsValue(valueF2)) {
							F2.put(keyF2, valueF2);
						}
					} else {
						F2.put(keyF2, valueF2);
					}
				}
			} else {
				F2.put(key, valueKey);
			}
		}
		System.out.println("F2 = " + F2);
		
		// Bước 3: Loại bỏ phụ thuộc hàm dư thừa
		answer = FindNonRedundant.findNonRedundant(attributes, F2);
		
		return answer;
	}
}