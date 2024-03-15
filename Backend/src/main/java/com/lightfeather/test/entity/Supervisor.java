package com.lightfeather.test.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Supervisor implements Comparable<Supervisor> {
    private String firstName;
    private String lastName;
    private String jurisdiction;

    @Override
    public int compareTo(Supervisor o) {
        int compareJurisdiction = this.jurisdiction.compareTo(o.getJurisdiction());
        if (compareJurisdiction == 0) {
            int compareLastName = this.lastName.compareTo(o.getLastName());
            if (compareLastName == 0) {
                return this.firstName.compareTo(o.getFirstName());
            }
            return compareLastName;
        }
        return compareJurisdiction;
    }
}