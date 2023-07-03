package br.com.jpa.model;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idt;
    private String zipcode;
    private String street;
    @OneToOne(mappedBy = "address")
    private Person person;

    public Address() {
    }

    public Address(String zipcode, String street) {
        this.zipcode = zipcode;
        this.street = street;

    }

    public long getIdt() {
        return idt;
    }

    public void setIdt(long idt) {
        this.idt = idt;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idt=" + idt +
                ", zipcode='" + zipcode + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
