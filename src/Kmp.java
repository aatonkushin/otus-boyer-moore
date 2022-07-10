/**
 * Реализация алгоритма Кнута-Морриса-Пратта
 * сначала строим таблицу Пи, затем смотрим совпадения, равные длинне шаблона
 */
public class Kmp implements Find {

    @Override
    public int find(String text, String pattern) {
        int[] pi = createPi(pattern);

        int j = 0; // количество совпавших символов, оно же индекс сравниваемого символа в шаблоне.

        for (int i = 0; i < text.length(); ++i) {
            while ((j > 0) && (text.charAt(i) != pattern.charAt(j))) {
                j = pi[j - 1];
            }

            if (text.charAt(i) == pattern.charAt(j)) // есть совпадение очередного символа
                j++;              // увеличиваем длину совпавшего фрагмента на 1

            if (j == pattern.length()) {
                return i -pattern.length() + 1; // шаблон найден
            }
        }

        return -1;
    }

    private int[] createPi(String pattern) {
        int[] pi = new int[pattern.length() + 1];
        pi[0] = 0;

        for (int q = 1; q < pattern.length(); q++) {
            int len = pi[q];

            while (len > 0 && pattern.charAt(len) != pattern.charAt(q)) {
                len = pi[len];
            }

            if (pattern.charAt(len) == pattern.charAt(q)) {
                len++;
            }

            pi[q + 1] = len;
        }

        return pi;
    }
}
