package com.pablosanchezegido.petcity.models;

public class PetCityException extends Exception {

    private String message;

    public PetCityException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
