package ca.nait.dmit.dmit2015.yjang.assignment04.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

/** @author: Yun Hyeok Jang
 @version: 01/28/2021*
 */
@Entity
@Table(name ="yjang6EdmontonPropertyAssessment")
@Getter @Setter
public class EdmontonPropertyAssessment implements Serializable {
//accountNumber, suite, houseNumber, streetNumber, garage, neighbourhoodId, neighbourhood, ward, assessedValue. Latitude, longitude, assessmentClass1

    @Id
    @Column(name="account_number", unique = true)
    private String accountNumber;

    @Column
    private String suite;
    @Column(name="house_number")
    private String houseNumber;
    @Column(name="street_name")
    private String streetName;
    @Column
    private Boolean garage;
    @Column(name="neighbourhood_Id")
    private Integer neighbourhoodId;
    @Column
    private String neighbourhood;
    @Column
    private String ward;
    @Column(name="assessed_value")
    private Integer assessedValue;
    @Column
    private double latitude;
    @Column
    private double longitude;
    @Column(name="assessement_class1")
    private String assessmentClass1;

    @Column(name="point_location")
    @jakarta.json.bind.annotation.JsonbTransient
    private org.locationtech.jts.geom.Point pointLocation;

    @Column
    private LocalDateTime createdDateTime;

    @PrePersist
    private void beforePersist() {
        createdDateTime = LocalDateTime.now();
    }


}
