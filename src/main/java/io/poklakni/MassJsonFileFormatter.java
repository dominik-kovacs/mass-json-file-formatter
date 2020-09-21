package io.poklakni;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class MassJsonFileFormatter {

    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter source directory absolute path: ");

            Path sourceDirPath = Paths.get(scanner.nextLine());
            Supplier<Stream<Path>> pathStreamSupplier = new JsonFilePathStreamSupplier(sourceDirPath);

            System.out.printf("%d files will be formatted, continue? (Y/N) ", pathStreamSupplier.get().count());

            if (scanner.next().equalsIgnoreCase("y")) {
                pathStreamSupplier.get().forEach(
                        jsonFilePath -> {
                            System.out.printf("Formatting file %s --- ", jsonFilePath);
                            try {
                                JsonFileFormatter.format(jsonFilePath);
                                System.out.println("Success");
                            } catch (IOException e) {
                                System.out.printf("Error - %s.%n", e.getLocalizedMessage());
                            }
                        }
                );
                System.out.println("Formatting finished, bye");
            }
        }
    }
}
