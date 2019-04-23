package com.haoche.yltms.system.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "serial_number", schema = "ylt", catalog = "")
public class SerialNumber {
    private String id;
    private String serialName;
    private String serialNo;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SERIAL_NAME")
    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    @Basic
    @Column(name = "SERIAL_NO")
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerialNumber that = (SerialNumber) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(serialName, that.serialName) &&
                Objects.equals(serialNo, that.serialNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialName, serialNo);
    }
}
