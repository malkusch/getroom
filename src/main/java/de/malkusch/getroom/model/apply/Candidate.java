package de.malkusch.getroom.model.apply;

import static java.util.Objects.requireNonNull;

public final class Candidate {

    public Candidate(Gender gender, FirstName firstName, LastName lastName, Email email, Phone phone) {
        this.firstName = requireNonNull(firstName);
        this.lastName = requireNonNull(lastName);
        this.gender = requireNonNull(gender);
        this.email = requireNonNull(email);
        this.phone = requireNonNull(phone);
    }

    private final FirstName firstName;

    public FirstName firstName() {
        return firstName;
    }

    private final LastName lastName;

    public LastName lastName() {
        return lastName;
    }

    private final Gender gender;

    public Gender gender() {
        return gender;
    }

    private final Email email;

    public Email email() {
        return email;
    }

    private final Phone phone;

    public Phone phone() {
        return phone;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s) <%s> %s", firstName, lastName, gender, email, phone);
    }

}
