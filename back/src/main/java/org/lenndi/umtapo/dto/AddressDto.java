package org.lenndi.umtapo.dto;

import com.fasterxml.jackson.annotation.JsonView;
import org.lenndi.umtapo.util.JsonViewResolver;

/**
 * Address entity.
 * <p>
 * Created by axel on 29/11/16.
 */
public class AddressDto {

    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String phone;
    @JsonView(JsonViewResolver.BorrowerSearchView.class)
    private String email;


    /**
     * Gets address 1.
     *
     * @return the address 1
     */
    public String getAddress1() {
        return address1;
    }


    /**
     * Sets address 1.
     *
     * @param address1 the address 1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }


    /**
     * Gets address 2.
     *
     * @return the address 2
     */
    public String getAddress2() {
        return address2;
    }


    /**
     * Sets address 2.
     *
     * @param address2 the address 2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }


    /**
     * Gets zip.
     *
     * @return the zip
     */
    public String getZip() {
        return zip;
    }


    /**
     * Sets zip.
     *
     * @param zip the zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }


    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }


    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }


    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }


    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }


    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
