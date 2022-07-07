public class BoyerMoorHorspool implements Find {

    int[] shift;
    //    int[] shift = new int[0x10FFFF];
    //    int[] shift = new int[8212 + 1];

    public BoyerMoorHorspool(int alphabetLength) {
        shift = new int[alphabetLength];
    }

    /**
     * Поиск алгоритмом Бойера–Мура–Хорспула
     *
     * @param text    текст
     * @param pattern искомый шаблон
     * @return позиция шаблона в тексте
     */
    public int find(String text, String pattern) {
        int[] shift = createShift(pattern); // таблица смещений
        int t = 0;                          // позиция в тексте

        int last = pattern.length() - 1;

        while (t < text.length() - last) {
            int p = last;   // позиция в шаблоне (поиск с конца шаблона)

            while (p >= 0 && text.charAt(t + p) == pattern.charAt(p)) {
                p--;
            }

            if (p == -1) {
                return t;
            }

            t += shift[text.charAt(t + last)];  // смещение на количество символов, указанных в ячейке
//            System.out.println(text.charAt(t + last) + ", "+ shift[text.charAt(t + last)] +", t= " + t);
        }

        return -1;
    }

    /**
     * Создаёт таблицу смещений для заданного шаблона
     *
     * @param pattern шаблон
     * @return массив со смещенияим
     */
    @SuppressWarnings("ExplicitArrayFilling")
    private int[] createShift(String pattern) {
        for (int i = 0; i < shift.length; i++) {
            shift[i] = pattern.length();
        }

        for (int p = 0; p < pattern.length() - 1; p++) {
            shift[pattern.charAt(p)] = pattern.length() - p - 1;

//            System.out.println(pattern.charAt(p) + ": " + shift[pattern.charAt(p)]);
        }

        return shift;
    }
}
