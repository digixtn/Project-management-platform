package com.digix.projectmanagement.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue
    private int fileId;
    private String fileName;
    private String fileType;
    private int fileSize;

    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;

    @ManyToOne
    @JoinColumn(name = "uploaded_by_user_id")
    private User uploadedByUserId;
}
