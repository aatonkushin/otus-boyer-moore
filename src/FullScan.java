public class FullScan implements Find{
    /**
     * Полный перебор O(T*P)
     *
     * @param text    текст
     * @param pattern искомый шаблон
     * @return позиция шаблона в тексте
     */
    public int find(String text, String pattern) {

        int t = 0;  // позиция в тексте

        // перечисляем все символы в тексте
        int max = text.length() - pattern.length();
        while (t <= max) {
            int p = 0;  // позиция в шаблоне

            // перечисляем все символы в шаблоне и сравниваем с символом в тексте
            while (p < pattern.length() && text.charAt(t + p) == pattern.charAt(p)) {
                p++;
            }

            // Если позиция в шаблоне равна длине шаблона, то подстрока найдена
            if (p == pattern.length()) {
                return t;   // возвращаем начальный индекс найденной подстроки
            }

            t++;    // смещаемся на один символ в тексте и продолжаем поиск
        }

        return -1;
    }
}
