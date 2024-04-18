package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.data.flpop.Flpop;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FloatingPopulation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrative_dong_id")
    private AdministrativeDistrict administrativeDistrict;

    private String standardYearQuarterCode;
    private int totFlpopCo;
    private int mlFlpopCo;
    private int fmlFlpopCo;
    private int agrde10FlpopCo;
    private int agrde20FlpopCo;
    private int agrde30FlpopCo;
    private int agrde40FlpopCo;
    private int agrde50FlpopCo;
    private int agrde60AboveFlpopCo;
    private int tmzon0006FlpopCo;
    private int tmzon0611FlpopCo;
    private int tmzon1114FlpopCo;
    private int tmzon1417FlpopCo;
    private int tmzon1721FlpopCo;
    private int tmzon2124FlpopCo;
    private int monFlpopCo;
    private int tuesFlpopCo;
    private int wedFlpopCo;
    private int thurFlpopCo;
    private int friFlpopCo;
    private int satFlpopCo;
    private int sunFlpopCo;

    @Builder
    public FloatingPopulation(AdministrativeDistrict dong, Flpop flpop){
        this.administrativeDistrict = dong;
        this.standardYearQuarterCode = flpop.getStandardYearQuarterCode();
        this.totFlpopCo = flpop.getTotFlpopCo();
        this.mlFlpopCo = flpop.getMlFlpopCo();
        this.fmlFlpopCo = flpop.getFmlFlpopCo();
        this.agrde10FlpopCo = flpop.getAgrde10FlpopCo();
        this.agrde20FlpopCo = flpop.getAgrde20FlpopCo();
        this.agrde30FlpopCo = flpop.getAgrde30FlpopCo();
        this.agrde40FlpopCo = flpop.getAgrde40FlpopCo();
        this.agrde50FlpopCo = flpop.getAgrde50FlpopCo();
        this.agrde60AboveFlpopCo = flpop.getAgrde60AboveFlpopCo();
        this.tmzon0006FlpopCo = flpop.getTmzon0006FlpopCo();
        this.tmzon0611FlpopCo = flpop.getTmzon0611FlpopCo();
        this.tmzon1114FlpopCo = flpop.getTmzon1114FlpopCo();
        this.tmzon1417FlpopCo = flpop.getTmzon1417FlpopCo();
        this.tmzon1721FlpopCo = flpop.getTmzon1721FlpopCo();
        this.tmzon2124FlpopCo = flpop.getTmzon2124FlpopCo();
        this.monFlpopCo = flpop.getMonFlpopCo();
        this.tuesFlpopCo = flpop.getTuesFlpopCo();
        this.wedFlpopCo = flpop.getWedFlpopCo();
        this.thurFlpopCo = flpop.getThurFlpopCo();
        this.friFlpopCo = flpop.getFriFlpopCo();
        this.satFlpopCo = flpop.getSatFlpopCo();
        this.sunFlpopCo = flpop.getSunFlpopCo();

    }

}