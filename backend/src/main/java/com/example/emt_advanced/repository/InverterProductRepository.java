package com.example.emt_advanced.repository;

import com.example.emt_advanced.model.FriziderProduct;
import com.example.emt_advanced.model.InverterProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InverterProductRepository extends JpaRepository<InverterProduct, Long> {
    @Query("""
        SELECT p
         FROM InverterProduct p, InverterProduct c
        WHERE c.id = :chosenId
          AND p.priceMkd BETWEEN c.priceMkd - :priceWindow AND c.priceMkd + :priceWindow
          AND p.id <> :chosenId
       """)
    List<InverterProduct> findProductsWithinPriceWindowOfChosen(@Param("chosenId") Long chosenId,
                                                                 @Param("priceWindow") Integer priceWindow);
}
