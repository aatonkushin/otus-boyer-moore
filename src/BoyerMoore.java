import java.util.ArrayList;
import java.util.List;

public class BoyerMoore {

    /**
     * Функция для вычисления таблицы сдвигов плохих символов
     *
     * @param x - шаблон
     * @return
     */
    @SuppressWarnings("DuplicatedCode")
    private int[] preBmBc(String x) {
        int[] table = new int[0x10FFFF];    // размер алфавита = размеру UTF-8

        for (int i = 0; i < table.length; i++) {
            table[i] = x.length();
        }

        for (int i = 0; i < x.length() - 1; i++) {
            table[x.charAt(i)] = x.length() - i - 1;
        }

        return table;
    }

    /**
     * Функция, проверяющая, что подстрока x[p…m−1] является префиксом шаблона x
     *
     * @param x шаблон
     * @param p начало подстроки
     * @return true - если является
     */
    private boolean isPrefix(String x, int p) {
        int j = 0;
        for (int i = p; i < x.length(); i++) {
            if (x.charAt(i) != x.charAt(j)) {
                return false;
            }

            j++;
        }

        return true;
    }

    /**
     * Функция, возвращающая для позиции p длину максимальной подстроки, которая является суффиксом шаблона x
     *
     * @param x шаблон
     * @param p позиция
     * @return длина максимальной подстроки
     */
    private int suffixLength(String x, int p) {
        int len = 0;
        int i = p;
        int j = x.length() - 1;

        while (i >= 0 && x.charAt(i) == x.charAt(j)) {
            len++;
            i--;
            j--;
        }

        return len;
    }

    /**
     * Функция для вычисления сдвигов хороших суффиксов
     *
     * @param x шаблон
     * @return массив сдвигов
     */
    private int[] preBmGs(String x) {
        int m = x.length();
        int[] table = new int[m];
        int lastPrefixPosition = m;

        for (int i = m; i > 0; i--) {
            // Если подстрока x[i+1..m-1] является префиксом, то запомним её начало
            if (isPrefix(x, i)) {
                lastPrefixPosition = i;
            }
            table[m - i] = lastPrefixPosition + m - 1;
        }

        for (int i = 0; i < m - 1; i++) {
            int slen = suffixLength(x, i);
            table[slen] = m - 1 - i + slen;
        }

        return table;
    }

    public List<Integer> find(String y, String x) {
        int m = x.length();
        int n = y.length();

        // ответ, содержащий все вхождения подстроки в строку
        List<Integer> answer = new ArrayList<>();

        if (x.length() == 0) {
            // Искомая подстрока является пустой
            answer.add(-1);
            return answer;
        }

        // Предварительные вычисления
        int[] bmBc = preBmBc(x);    // сдвиги плохих символов
        int[] bmGs = preBmGs(x);

        for (int i = m - 1; i < n - 1; i++) {
            int j = m - 1;

            while (x.charAt(j) == y.charAt(i)) {
                if (j == 0) {
                    answer.add(i);  // Найдена подстрока в позиции i
                }

                i--;
                j--;
            }

            i += max(bmGs[m - 1 - j], bmBc[y.charAt(i)]);
        }

        if (answer.isEmpty()) {
            answer.add(-1);
        }

        return answer;
    }

    private int max(int x, int y) {
        return x > y ? x : y;
    }
}
