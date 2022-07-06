import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BoyerMoore boyerMoore = new BoyerMoore();
        List<Integer> found =  boyerMoore.find("GCATCGCAGAGAGTATACAGTACG", "GCAGAGAG");

//        Path path = Paths.get("/home/anton/Капитанская_дочка.txt");
//
//        String text = String.join("", Files.readAllLines(path));
//
//        System.out.println("Кол-во символов в тексте: " + text.length());
//        System.out.println("Максимальный код UTF-8= " + getMaxChar(text));
//
//        Stopwatch sw = new Stopwatch();
//
//        String pattern = "шейка";
//
//        System.out.print("Полный перебор: ");
//        sw.start();
//        for (int i = 0; i < 1000; i++) {
//            findFullScan(text, pattern);
//        }
//        sw.stop();
//        System.out.println(sw.getElapsedTime());
//
//        System.out.print("Алгоритм Бойера–Мура–Хорспула: ");
//        sw.start();
//        for (int i = 0; i < 1000; i++) {
//            findBMH(text, pattern);
//        }
//        sw.stop();
//
//        System.out.println(sw.getElapsedTime());
    }

    /**
     * Поный перебор O(T*P)
     *
     * @param text    текст
     * @param pattern искомый шаблон
     * @return позиция шаблона в тексте
     */
    public static int findFullScan(String text, String pattern) {

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

    /**
     * Поиск алгоритмом Бойера–Мура–Хорспула
     *
     * @param text    текст
     * @param pattern искомый шаблон
     * @return позиция шаблона в тексте
     */
    public static int findBMH(String text, String pattern) {
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

//        static int[] shift = new int[0x10FFFF];
    static int[] shift = new int[8212 + 1];

    /**
     * Создаёт таблицу смещений для заданного шаблона
     *
     * @param pattern шаблон
     * @return массив со смещенияим
     */
    @SuppressWarnings("ExplicitArrayFilling")
    private static int[] createShift(String pattern) {

        for (int i = 0; i < shift.length; i++) {
            shift[i] = pattern.length();
        }

        for (int p = 0; p < pattern.length() - 1; p++) {
            shift[pattern.charAt(p)] = pattern.length() - p - 1;

//            System.out.println(pattern.charAt(p) + ": " + shift[pattern.charAt(p)]);
        }

        return shift;
    }

    public static int getMaxChar(String text) {
        int max = 0;

        for (int i = 0; i < text.length(); i++) {
            if (max < (int) text.charAt(i)) {
                max = text.charAt(i) + 1;
            }
        }

        return max;
    }
}

