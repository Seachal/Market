package com.dajukeji.hslz.domain.area;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gordo on 2016/12/15.
 */

public class CityInfo implements Serializable {

    private Integer id;
    private Integer areaCode;
    private String areaName;//区域名
    private String areaShortName;//简称
    private String parentCode;//父ID
    private Integer level;//级别
    private String areaId; // 地区ID
    private boolean leaf;//页
    private boolean isMunicipality;//是否为自治区
    private boolean isSpecial;//是否为特殊

    private List<CityInfo> citys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaShortName() {
        return areaShortName;
    }

    public void setAreaShortName(String areaShortName) {
        this.areaShortName = areaShortName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public boolean isMunicipality() {
        return isMunicipality;
    }

    public void setMunicipality(boolean municipality) {
        isMunicipality = municipality;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public void setSpecial(boolean special) {
        isSpecial = special;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<CityInfo> getCitys() {
        return citys;
    }

    public void setCitys(List<CityInfo> citys) {
        this.citys = citys;
    }


}
