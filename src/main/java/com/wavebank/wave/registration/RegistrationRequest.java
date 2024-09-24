package com.wavebank.wave.registration;

//https://www.baeldung.com/java-record-keyword
//record is better than class in this case because it handles immutable data
public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role) {

}
