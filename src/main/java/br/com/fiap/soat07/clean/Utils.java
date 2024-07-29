package br.com.fiap.soat07.clean;

import br.com.fiap.soat07.clean.core.domain.entity.Produto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

    public static <T> boolean hasProdutoDuplicates(Collection<Produto> list) {
//        Set<T> seen = new HashSet<>();
//        return list.stream().anyMatch(e -> !seen.add(e));

        return list.stream().map(Produto::getTipoProduto)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .values()
                .stream().anyMatch(x -> x > 1);
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

}
