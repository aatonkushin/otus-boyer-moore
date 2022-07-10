/**
 * Реализация алгоритма Кнута-Морриса-Пратта
 * Объединяем шаблон и текст через символ, который заведомо отсутствует в шаблоне,
 * затем строим таблицу Пи и сразу ищем совпадение, равное длине шаблона
 */
public class Kmp2 implements Find {
    @Override
    public int find(String text, String pattern) {
        String str = pattern + "@" + text;

        int[] pi = new int[str.length() + 1];
        pi[0] = 0;

        for (int q = 1; q < str.length(); q++) {
            int len = pi[q];

            while (len > 0 && str.charAt(len) != str.charAt(q)) {
                len = pi[len];
            }

            if (str.charAt(len) == str.charAt(q)) {
                len++;

                if (len == pattern.length()) {
//                    System.out.println(q - 2 * pattern.length());
                    return q - 2 * pattern.length();
                }
            }

            pi[q + 1] = len;
        }

        return -1;
    }
}
