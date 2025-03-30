package com.example.Aisian.Cuisine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CheckoutRequestDTO {

    @JsonProperty("address")
    private String address;

    @JsonProperty("address_note") // ✅ map với client
    private String addressNote;

    @JsonProperty("phone_number") // ✅ map với client
    private String phoneNumber;

    @JsonProperty("voucher_code") // ✅ map với client
    private String voucherCode;

    @JsonProperty("items") // ✅ map với client, phải thêm thuộc tính này
    private List<CheckoutItemDTO> items;

    // Getters và Setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNote() {
        return addressNote;
    }

    public void setAddressNote(String addressNote) {
        this.addressNote = addressNote;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public List<CheckoutItemDTO> getItems() { // ✅ Thêm getter cho items
        return items;
    }

    public void setItems(List<CheckoutItemDTO> items) { // ✅ Thêm setter cho items
        this.items = items;
    }
}
