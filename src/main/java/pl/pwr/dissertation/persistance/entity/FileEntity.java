package pl.pwr.dissertation.persistance.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class FileEntity extends BaseEntity {


    private String downloadName;

    private String path;

    private Long size;

    private String extension;

    private String mime;


}
