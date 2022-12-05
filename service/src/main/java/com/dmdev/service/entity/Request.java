package com.dmdev.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.time.LocalDateTime;

@Data
@ToString(exclude = {"car", "user", "tariff"})
@EqualsAndHashCode(of = {"dateRequest", "dateReturn"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "withUser", attributeNodes = {@NamedAttributeNode("user")})
@Entity
public class Request implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateRequest;

    @Column(nullable = false)
    private LocalDateTime dateReturn;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
        tariff.getRequests().add(this);
    }

    public void setCar(Car car) {
        this.car = car;
        car.getRequests().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.getRequests().add(this);
    }
}
