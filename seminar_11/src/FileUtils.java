import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    public static Optional<Object> average(Path p) {
        try (Stream<String> lines = Files.lines(p)) {
            return Optional.ofNullable(lines.map(Double::parseDouble)
                    .collect(Collectors.averagingDouble(Double::doubleValue)));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Double> evensAverage(Path p) {
        Predicate<Double> isEven = num -> num % 2 == 0;

        try (Stream<String> lines = Files.lines(p)) {
            return lines.map(Double::parseDouble)
                    .filter(isEven)
                    .collect(Collectors.averagingDouble(Double::doubleValue)).describeConstable();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Double> evenMultsOfThreeAverage(Path p) {
        Predicate<Double> isEvenMultOfThree = num -> num % 2 == 0 && num % 3 == 0;

        try (Stream<String> lines = Files.lines(p)) {
            return lines.map(Double::parseDouble)
                    .filter(isEvenMultOfThree)
                    .collect(Collectors.averagingDouble(Double::doubleValue)).describeConstable();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static String linesString(Path p, String delimiterWord) {
        try (Stream<String> lines = Files.lines(p)) {
            return lines.collect(Collectors.joining(delimiterWord));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Stream<String> palindromes(Path p) throws IOException {
        return Files.lines(p)
                .map(String::trim)
                .filter(FileUtils::isPalindrome);
    }

    private static boolean isPalindrome(String str) {
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
}
