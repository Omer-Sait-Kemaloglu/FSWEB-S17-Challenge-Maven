package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    private String name;
    private int credit;
    private Grade grade;

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }



}
