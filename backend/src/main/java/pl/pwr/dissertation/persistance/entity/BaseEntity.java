package pl.pwr.dissertation.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    public BaseEntity(int id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    protected int id;

    @JoinColumn(updatable = false)
    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @CreatedBy
    private UserEntity createdBy;

    @LastModifiedBy
    @ManyToOne
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private UserEntity modifiedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private long createdDate;

    @LastModifiedDate
    private long modifiedDate;

}
