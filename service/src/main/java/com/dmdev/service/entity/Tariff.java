package com.dmdev.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "requests")
@EqualsAndHashCode(of = {"type", "price"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tariff implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tariff_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TariffType type;

    @Column(scale = 2, nullable = false)
    private BigDecimal price;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Request> requests = new ArrayList<>();

    public BigDecimal getPrice() {
        return price.setScale(2, RoundingMode.HALF_UP);
    }
}
