package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        int[][] count = new int[first.length() + 1][second.length() + 1];
        StringBuilder sequence = new StringBuilder();
        for (int i = first.length(); i > -1; i--) {
            for (int j = second.length(); j > -1; j--) {
                if (i == first.length() || j == second.length()) count[i][j] = 0;
                else if (first.charAt(i) == second.charAt(j)) count[i][j] = 1 + count[i + 1][j + 1];
                else count[i][j] = Math.max(count[i + 1][j], count[i][j + 1]);
            }
        }
        int i = 0;
        int j = 0;
        while (i < first.length() && j < second.length()) {
            if (first.charAt(i) == second.charAt(j)) {
                sequence.append(first.charAt(i));
                i++;
                j++;
            } else if (count[i + 1][j] >= count[i][j + 1]) i++;
            else j++;
        }
        return sequence.toString();
    }
    //сложность: O(mn) ресурсоемкость: O(mn)

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        Collections.reverse(list);
        if (list.size() == 0) return new ArrayList<Integer>();
        int[] pos = new int[list.size() + 1];
        int[] prev = new int[list.size()];
        int length = 0;
        for (int i = 0; i < list.size(); i++) {
            int l = 1;
            int r = length;
            while (l <= r) {
                int m = (l + r) / 2;
                if (list.get(pos[m]) < list.get(i)) r = m - 1;
                else l = m + 1;
            }
            prev[i] = pos[l - 1];
            pos[l] = i;
            if (l > length) {
                length = l;
            }
        }
        List<Integer> out = new ArrayList<>();
        int p = pos[length];
        for (int i = 0; i<=length-1; i++) {
            out.add(list.get(p));
            p = prev[p];
        }
        return out;
    }
    //сложность: O(n log n) ресурсоемкость: O(n)

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
