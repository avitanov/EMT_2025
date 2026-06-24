package com.example.emt_advanced.repository;

import com.example.emt_advanced.model.FriziderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


@Repository
public interface FriziderProductRepository extends JpaRepository<FriziderProduct, Long> {
    @Query("""
        SELECT p
         FROM FriziderProduct p, FriziderProduct c
        WHERE c.id = :chosenId
          AND p.priceMkd BETWEEN c.priceMkd - :priceWindow AND c.priceMkd + :priceWindow
          AND p.id <> :chosenId
       """)
    List<FriziderProduct> findProductsWithinPriceWindowOfChosen(@Param("chosenId") Long chosenId,
                                                                 @Param("priceWindow") Integer priceWindow);
}
