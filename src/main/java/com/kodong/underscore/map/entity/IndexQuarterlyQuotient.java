package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.data.ixqq.Ixqq;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndexQuarterlyQuotient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrative_dong_id")
    private AdministrativeDistrict administrativeDistrict;

    private String standardYearQuarterCode;
    private String trdarChngeIx;
    private String trdarChngeIxNm;
    private int oprSaleMtAvrg;
    private int clsSaleMtAvrg;
    private int suOprSaleMtAvrg;
    private int suClsSaleMtAvrg;

    @Builder
    public IndexQuarterlyQuotient(AdministrativeDistrict dong, Ixqq indexQ){
        this.administrativeDistrict = dong;
        this.standardYearQuarterCode = indexQ.getStandardYearQuarterCode();
        this.trdarChngeIx = indexQ.getTrdarChngeIx();
        this.trdarChngeIxNm = indexQ.getTrdarChngeIxNm();
        this.oprSaleMtAvrg = indexQ.getOprSaleMtAvrg();
        this.clsSaleMtAvrg = indexQ.getClsSaleMtAvrg();
        this.suOprSaleMtAvrg = indexQ.getSuOprSaleMtAvrg();
        this.suClsSaleMtAvrg = indexQ.getSuClsSaleMtAvrg();
    }
}
