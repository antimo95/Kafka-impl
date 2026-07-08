package com.example.magazzino.mapper;

import com.example.magazzino.dto.StockRequest;
import com.example.magazzino.dto.StockResponse;
import com.example.magazzino.entity.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// Esempio: con MapStruct non serve implementare il corpo del metodo.
// In fase di build (annotationProcessor) viene generata la classe
// "StockMapperMapStructExampleImpl" che fa il mapping campo-per-campo
// in base ai nomi (e alle @Mapping esplicite dove i nomi differiscono).
@Mapper(componentModel = "spring")
public interface StockMapperMapStructExample {

    StockResponse toResponse(Stock stock);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "prodotto", target = "prodotto")
    @Mapping(source = "quantita", target = "quantitaDisponibile")
    Stock toEntity(StockRequest request);
}
