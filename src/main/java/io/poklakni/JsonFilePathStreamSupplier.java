package io.poklakni;

import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Data
public class JsonFilePathStreamSupplier implements Supplier<Stream<Path>> {

    @NonNull
    private Path sourceDirPath;

    @SneakyThrows
    @Override
    public Stream<Path> get() {
        return Files.walk(sourceDirPath)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json"));
    }
}
