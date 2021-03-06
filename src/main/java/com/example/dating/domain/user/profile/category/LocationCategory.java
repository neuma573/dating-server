package com.example.dating.domain.user.profile.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Getter
public enum LocationCategory {
    SEOUL(0, "서울"),
    BUSAN(1, "부산"),
    DAEGU(2, "대구"),
    INCHUN(3, "인천"),
    GWANGJU(4, "광주"),
    DAEJUN(5, "대구"),
    ULSAN(6, "울산"),
    SEJONG(7, "세종"),
    GYEONGGI(8, "경기"),
    GANGWAON(9, "강원"),
    CHUNGBUK(10, "충북"),
    CHUNGNAM(11, "충남"),
    JUNBUK(12, "전북"),
    JUNNAM(13, "전남"),
    GYEONGNAM(15, "경남"),
    GYEONGBUK(16, "경북"),
    JEJU(17, "제주");

    private static final Map<String, LocationCategory> stringToEnum =
            Stream.of(values()).collect(toMap(Objects::toString, e -> e));

    private int code;
    private String description;

    LocationCategory(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonCreator
    public static LocationCategory fromText(String text){
        return stringToEnum.get(text);
    }

}
