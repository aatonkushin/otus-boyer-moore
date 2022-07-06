public class BoyerMoore {
    private int[] table = new int[0x10FFFF];    // размер алфавита = размеру UTF-8

    /**
     * Функция для вычисления таблицы сдвигов плохих символов
     * @param x
     * @return
     */
    private int[] preBmBc(String x) {
        for (int i = 0; i < table.length; i++) {
            table[i] = x.length();
        }

        for (int i = 0; i < x.length() - 1; i++) {
            table[x.charAt(i)] = x.length() - i - 1;
        }

        return table;
    }
}
