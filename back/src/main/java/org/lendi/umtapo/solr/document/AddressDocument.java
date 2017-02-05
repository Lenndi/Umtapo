package org.lendi.umtapo.solr.document;

import org.apache.solr.client.solrj.beans.Field;

public class AddressDocument {

    @Field
    private String addressId;

    @Field
    private String address1;

    @Field
    private String address2;

    @Field
    private String zip;

    @Field
    private String city;

    @Field
    private String phone;

    @Field
    private String email;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String id) {
        this.addressId = id;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
