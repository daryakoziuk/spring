package com.dmdev.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "car"})
@ToString(exclude = "car")
@Builder
@Entity
@Table(name = "car_characteristic")
public class CarCharacteristic implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", unique = true)
    private Car car;

    private Integer engineVolume;

    @Enumerated(EnumType.STRING)
    private TypeTransmission transmission;

    @Enumerated(EnumType.STRING)
    private TypeFuel type;

    private LocalDate dateRelease;

    public void setCar(Car car) {
        car.setCarCharacteristic(this);
        this.car = car;
    }
}
