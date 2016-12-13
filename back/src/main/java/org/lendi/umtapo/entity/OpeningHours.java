package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * OpeningHours entity.
 *
 * Created by axel on 29/11/16.
 */
@Entity
public class OpeningHours {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer day;
    private Boolean isEnable;
    private Date morningOpening;
    private Date morningClosing;
    private Date afternoonOpening;
    private Date afternoonClosing;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public Date getMorningOpening() {
        return morningOpening;
    }

    public void setMorningOpening(Date morningOpening) {
        this.morningOpening = morningOpening;
    }

    public Date getMorningClosing() {
        return morningClosing;
    }

    public void setMorningClosing(Date morningClosing) {
        this.morningClosing = morningClosing;
    }

    public Date getAfternoonOpening() {
        return afternoonOpening;
    }

    public void setAfternoonOpening(Date afternoonOpening) {
        this.afternoonOpening = afternoonOpening;
    }

    public Date getAfternoonClosing() {
        return afternoonClosing;
    }

    public void setAfternoonClosing(Date afternoonClosing) {
        this.afternoonClosing = afternoonClosing;
    }
}
