package ru.tn.nnjack.TelegramBotWeatherService.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="geographical_objects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeographicalObject {
    @Id
    @Column(name="id")
    private  int id;

    @Column(name = "country")
    private String country;

    @Column(name="name_ru")
    private String nameRu;

    @Column(name="name_en")
    private String nameEn;

    @Column(name="district_ru")
    private String districtRu;

    @Column(name="district_en")
    private String districtEn;

    @Column(name="sub_district_ru")
    private String subDistrictRu;

    @Column(name="sub_district_en")
    private String subDistrictEn;


    @Column(name="kind_ru")
    private String kindRu;

    @Column(name="kind_en")
    private String kindEn;

    @ManyToMany(mappedBy = "geographicalObjects")
    private Set<User> users;


    public String toStringShort(String langCode) {
        String countryStr = checkNull(country);
        String kindStr = checkNull("ru".equalsIgnoreCase(langCode) ? kindRu : kindEn);
        String nameStr = checkNull("ru".equalsIgnoreCase(langCode) ? nameRu : nameEn);

        return joinNonEmpty(", ", countryStr, kindStr, nameStr);
    }

    public String toStringLong(String langCode) {
        String countryStr = checkNull(country);
        String kindStr = checkNull("ru".equalsIgnoreCase(langCode) ? kindRu : kindEn);
        String nameStr = checkNull("ru".equalsIgnoreCase(langCode) ? nameRu : nameEn);
        String districtStr = checkNull("ru".equalsIgnoreCase(langCode) ? districtRu : districtEn);
        String subDistrictStr = checkNull("ru".equalsIgnoreCase(langCode) ? subDistrictRu : subDistrictEn);

        return joinNonEmpty(", ", countryStr, kindStr, nameStr, districtStr, subDistrictStr);
    }

    private String checkNull(String value) {
        return value != null ? value : "";
    }

    private String joinNonEmpty(String delimiter, String... parts) {
        return Arrays.stream(parts)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.joining(delimiter));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeographicalObject that = (GeographicalObject) o;
        return id == that.id && Objects.equals(country, that.country) && Objects.equals(nameRu, that.nameRu) && Objects.equals(nameEn, that.nameEn) && Objects.equals(districtRu, that.districtRu) && Objects.equals(districtEn, that.districtEn) && Objects.equals(subDistrictRu, that.subDistrictRu) && Objects.equals(subDistrictEn, that.subDistrictEn) && Objects.equals(kindRu, that.kindRu) && Objects.equals(kindEn, that.kindEn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, nameRu, nameEn, districtRu, districtEn, subDistrictRu, subDistrictEn, kindRu, kindEn);
    }
}
