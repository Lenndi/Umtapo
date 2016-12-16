package org.lendi.umtapo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * OpeningHours entity.
 * <p>
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets day.
     *
     * @return the day
     */
    public Integer getDay() {
        return day;
    }

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * Gets enable.
     *
     * @return the enable
     */
    public Boolean getEnable() {
        return isEnable;
    }

    /**
     * Sets enable.
     *
     * @param enable the enable
     */
    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    /**
     * Gets morning opening.
     *
     * @return the morning opening
     */
    public Date getMorningOpening() {
        return morningOpening;
    }

    /**
     * Sets morning opening.
     *
     * @param morningOpening the morning opening
     */
    public void setMorningOpening(Date morningOpening) {
        this.morningOpening = morningOpening;
    }

    /**
     * Gets morning closing.
     *
     * @return the morning closing
     */
    public Date getMorningClosing() {
        return morningClosing;
    }

    /**
     * Sets morning closing.
     *
     * @param morningClosing the morning closing
     */
    public void setMorningClosing(Date morningClosing) {
        this.morningClosing = morningClosing;
    }

    /**
     * Gets afternoon opening.
     *
     * @return the afternoon opening
     */
    public Date getAfternoonOpening() {
        return afternoonOpening;
    }

    /**
     * Sets afternoon opening.
     *
     * @param afternoonOpening the afternoon opening
     */
    public void setAfternoonOpening(Date afternoonOpening) {
        this.afternoonOpening = afternoonOpening;
    }

    /**
     * Gets afternoon closing.
     *
     * @return the afternoon closing
     */
    public Date getAfternoonClosing() {
        return afternoonClosing;
    }

    /**
     * Sets afternoon closing.
     *
     * @param afternoonClosing the afternoon closing
     */
    public void setAfternoonClosing(Date afternoonClosing) {
        this.afternoonClosing = afternoonClosing;
    }
}
