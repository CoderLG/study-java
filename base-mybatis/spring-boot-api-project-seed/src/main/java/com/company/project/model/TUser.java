package com.company.project.model;

import javax.persistence.*;

@Table(name = "t_user")
public class TUser {
    @Id
    @Column(name = "t_id")
    private Integer tId;

    @Column(name = "t_name")
    private String tName;

    /**
     * @return t_id
     */
    public Integer gettId() {
        return tId;
    }

    /**
     * @param tId
     */
    public void settId(Integer tId) {
        this.tId = tId;
    }

    /**
     * @return t_name
     */
    public String gettName() {
        return tName;
    }

    /**
     * @param tName
     */
    public void settName(String tName) {
        this.tName = tName;
    }
}