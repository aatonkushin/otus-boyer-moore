import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("./Капитанская_дочка.txt");
        String text = String.join("", Files.readAllLines(path));

        System.out.println("Кол-во символов в тексте: " + text.length());
        int alphabetLength = getMaxChar(text);
        System.out.println("Максимальный код UTF-8= " + alphabetLength);

        String pattern = "безвинных";
        System.out.println(pattern);
        for (int t = 100; t <= 10000; t *= 10) {
            test(text, pattern, t, alphabetLength);
        }

        pattern = "казак";
        System.out.println(pattern);
        for (int t = 100; t <= 10000; t *= 10) {
            test(text, pattern, t, alphabetLength);
        }
    }

    private static int getMaxChar(String text) {
        int max = 0;

        for (int i = 0; i < text.length(); i++) {
            if (max < (int) text.charAt(i)) {
                max = text.charAt(i) + 1;
            }
        }

        return max;
    }

    private static void test(String text, String pattern, int t, int alphabetLength) {
        System.out.println("T= " + t);
        Stopwatch sw = new Stopwatch();

        //------------------------------------------
        FullScan fullScan = new FullScan();
        singleTest(fullScan, sw, "Полный перебор", text, pattern, t);
        //------------------------------------------

        BoyerMoorHorspool boyerMoorHorspool = new BoyerMoorHorspool(0x10FFFF);
        singleTest(boyerMoorHorspool, sw, "Алгоритм Бойера–Мура–Хорспула", text, pattern, t);

        //------------------------------------------
        BoyerMoore boyerMoore = new BoyerMoore(0x10FFFF);
        singleTest(boyerMoore, sw, "Алгоритм Бойера–Мура", text, pattern, t);

        //------------------------------------------
        boyerMoorHorspool = new BoyerMoorHorspool(alphabetLength);
        singleTest(boyerMoorHorspool, sw, "Алгоритм Бойера–Мура–Хорспула (оптимизирован)", text, pattern, t);

        //------------------------------------------
        boyerMoore = new BoyerMoore(alphabetLength);
        singleTest(boyerMoore, sw, "Алгоритм Бойера–Мура (оптимизирован)", text, pattern, t);

        //------------------------------------------
        Kmp kmp = new Kmp();
        singleTest(kmp, sw, "Алгоритм Кнута-Морриса-Пратта", text, pattern, t);

        //------------------------------------------
        Kmp2 kmp2 = new Kmp2();
        singleTest(kmp2, sw, "Алгоритм Кнута-Морриса-Пратта (объединение строк)", text, pattern, t);
    }

    private static void singleTest(Find f, Stopwatch sw, String title, String text, String pattern, int t) {
        int pos = -1;

        System.out.print(title + ": ");
        sw.start();

        for (int i = 0; i < t; i++) {
            pos = f.find(text, pattern);
        }

        sw.stop();

        System.out.println("позиция: " + pos + ", " + sw);
    }
}

