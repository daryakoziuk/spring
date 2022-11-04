package com.dmdev.service.dao;

import com.dmdev.service.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface TariffRepository extends JpaRepository<Tariff, Integer> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Tariff t set t.price=:price where t.id=:id")
    int updateTariff(Integer id, BigDecimal price);
}
