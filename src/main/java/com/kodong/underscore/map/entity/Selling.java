package com.kodong.underscore.map.entity;

import com.kodong.underscore.map.data.selng.Selng;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Selling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "administrative_dong_id")
    private AdministrativeDistrict administrativeDistrict;

    @ManyToOne
    @JoinColumn(name = "service_industry_id")
    private ServiceIndustry serviceIndustry;


    private String standardYearQuarterCode;
    private long thsmonSelngAmt;
    private long thsmonSelngCo;
    private long mdwkSelngAmt;
    private long wkendSelngAmt;
    private long monSelngAmt;
    private long tuesSelngAmt;
    private long wedSelngAmt;
    private long thurSelngAmt;
    private long friSelngAmt;
    private long satSelngAmt;
    private long sunSelngAmt;
    private long tmzon0006SelngAmt;
    private long tmzon0611SelngAmt;
    private long tmzon1114SelngAmt;
    private long tmzon1417SelngAmt;
    private long tmzon1721SelngAmt;
    private long tmzon2124SelngAmt;
    private long mlSelngAmt;
    private long fmlSelngAmt;
    private long agrde10SelngAmt;
    private long agrde20SelngAmt;
    private long agrde30SelngAmt;
    private long agrde40SelngAmt;
    private long agrde50SelngAmt;
    private long agrde60AboveSelngAmt;
    private long mdwkSelngCo;
    private long wkendSelngCo;
    private long monSelngCo;
    private long tuesSelngCo;
    private long wedSelngCo;
    private long thurSelngCo;
    private long friSelngCo;
    private long satSelngCo;
    private long sunSelngCo;
    private long tmzon0006SelngCo;
    private long tmzon0611SelngCo;
    private long tmzon1114SelngCo;
    private long tmzon1417SelngCo;
    private long tmzon1721SelngCo;
    private long tmzon2124SelngCo;
    private long mlSelngCo;
    private long fmlSelngCo;
    private long agrde10SelngCo;
    private long agrde20SelngCo;
    private long agrde30SelngCo;
    private long agrde40SelngCo;
    private long agrde50SelngCo;
    private long agrde60AboveSelngCo;

    @Builder
    public Selling(AdministrativeDistrict dong, ServiceIndustry serviceIndustry, Selng selling){
        this.administrativeDistrict = dong;
        this.serviceIndustry = serviceIndustry;
        this.standardYearQuarterCode = selling.getStandardYearQuarterCode();
        this.thsmonSelngAmt = selling.getThsmonSelngAmt();
        this.thsmonSelngCo = selling.getThsmonSelngCo();
        this.mdwkSelngAmt = selling.getMdwkSelngAmt();
        this.wkendSelngAmt = selling.getWkendSelngAmt();
        this.monSelngAmt = selling.getMonSelngAmt();
        this.tuesSelngAmt = selling.getTuesSelngAmt();
        this.wedSelngAmt = selling.getWedSelngAmt();
        this.thurSelngAmt = selling.getThurSelngAmt();
        this.friSelngAmt = selling.getFriSelngAmt();
        this.satSelngAmt = selling.getSatSelngAmt();
        this.sunSelngAmt = selling.getSunSelngAmt();
        this.tmzon0006SelngAmt = selling.getTmzon0006SelngAmt();
        this.tmzon0611SelngAmt = selling.getTmzon0611SelngAmt();
        this.tmzon1114SelngAmt = selling.getTmzon1114SelngAmt();
        this.tmzon1417SelngAmt = selling.getTmzon1417SelngAmt();
        this.tmzon1721SelngAmt = selling.getTmzon1721SelngAmt();
        this.tmzon2124SelngAmt = selling.getTmzon2124SelngAmt();
        this.mlSelngAmt = selling.getMlSelngAmt();
        this.fmlSelngAmt = selling.getFmlSelngAmt();
        this.agrde10SelngAmt = selling.getAgrde10SelngAmt();
        this.agrde20SelngAmt = selling.getAgrde20SelngAmt();
        this.agrde30SelngAmt = selling.getAgrde30SelngAmt();
        this.agrde40SelngAmt = selling.getAgrde40SelngAmt();
        this.agrde50SelngAmt = selling.getAgrde50SelngAmt();
        this.agrde60AboveSelngAmt = selling.getAgrde60AboveSelngAmt();
        this.mdwkSelngCo = selling.getMdwkSelngCo();
        this.wkendSelngCo = selling.getWkendSelngCo();
        this.monSelngCo = selling.getMonSelngCo();
        this.tuesSelngCo = selling.getTuesSelngCo();
        this.wedSelngCo = selling.getWedSelngCo();
        this.thurSelngCo = selling.getThurSelngCo();
        this.friSelngCo = selling.getFriSelngCo();
        this.satSelngCo = selling.getSatSelngCo();
        this.sunSelngCo = selling.getSunSelngCo();
        this.tmzon0006SelngCo = selling.getTmzon0006SelngCo();
        this.tmzon0611SelngCo = selling.getTmzon0611SelngCo();
        this.tmzon1114SelngCo = selling.getTmzon1114SelngCo();
        this.tmzon1417SelngCo = selling.getTmzon1417SelngCo();
        this.tmzon1721SelngCo = selling.getTmzon1721SelngCo();
        this.tmzon2124SelngCo = selling.getTmzon2124SelngCo();
        this.mlSelngCo = selling.getMlSelngCo();
        this.fmlSelngCo = selling.getFmlSelngCo();
        this.agrde10SelngCo = selling.getAgrde10SelngCo();
        this.agrde20SelngCo = selling.getAgrde20SelngCo();
        this.agrde30SelngCo = selling.getAgrde30SelngCo();
        this.agrde40SelngCo = selling.getAgrde40SelngCo();
        this.agrde50SelngCo = selling.getAgrde50SelngCo();
        this.agrde60AboveSelngCo = selling.getAgrde60AboveSelngCo();
    }
}
