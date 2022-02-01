package pl.pwr.dissertation.persistance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.pwr.dissertation.logic.domain.TopicIndexExtendedProjection;
import pl.pwr.dissertation.logic.domain.TopicIndexProjection;
import pl.pwr.dissertation.logic.domain.search.TopicSearchQuery;
import pl.pwr.dissertation.persistance.entity.TopicEntity;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer>, TopicAuditRepositoryCustom {

//    @Query("""
//                SELECT distinct t from TopicEntity t
//                    left join t.promoter p
//                    left join t.tags tags
//                where ( :#{#q.term} is null         or t.term = :#{#q.term})
//                and  ( :#{#q.faculty} is null       or t.faculty = :#{#q.faculty})
//                and  ( :#{#q.field} is null         or t.field = :#{#q.field})
//                and  ( :#{#q.level} is null         or t.level = :#{#q.level})
//                and  ( :#{#q.topicStatus} is null   or t.term = :#{#q.topicStatus})
//                and  ( :#{#q.promoter} is null      or p.firstName like :#{#q.promoter}  or p.lastName like :#{#q.promoter} )
//                and  ( :#{#q.promoterId} is null    or p.id = :#{#q.promoterId} )
//                and  ( :#{#q.tag} is null           or :#{#q.tag} in tags )
//            """)
//    Page<TopicEntity> findAllWithQuery(TopicSearchQuery q, Pageable pageable);


    @Query("""
                SELECT distinct
                new pl.pwr.dissertation.logic.domain.TopicIndexProjection(
                t.id,
                t.name,
                t.description,
                t.descriptionEng,
                t.level,
                t.field,
                t.faculty,
                t.status,
                count(app),
                (count(t.student.id) = 0),
                p
                )
                from TopicEntity t
                    left join t.promoter p
                    left join t.tags tags
                    left join t.applications app
                where ( :#{#q.term} is null         or t.term = :#{#q.term})
                and  ( :#{#q.faculty} is null       or t.faculty = :#{#q.faculty})
                and  ( :#{#q.field} is null         or t.field = :#{#q.field})
                and  ( :#{#q.level} is null         or t.level = :#{#q.level})
                and  ( :#{#q.topicStatus} is null   or t.status = :#{#q.topicStatus})
                and  ( :#{#q.promoter} is null      or p.firstName like :#{#q.promoter}%  or p.lastName like :#{#q.promoter}% )
                and  ( :#{#q.promoterId} is null    or p.id = :#{#q.promoterId} )
                and  ( :#{#q.tag} is null           or :#{#q.tag} in tags )
                and  ( :#{#q.free} is null          or (:#{#q.free} = true and t.student is null) or (:#{#q.free} = false and t.student is not null)  )
                and  ( :#{#q.studentId} is null     or t.student.id = :#{#q.studentId} or app.student.id = :#{#q.studentId})
                group by t
            """)
    Page<TopicIndexProjection> findAllIndexWithQuery(TopicSearchQuery q, Pageable pageable);


    @Query("""
                SELECT distinct
                new pl.pwr.dissertation.logic.domain.TopicIndexExtendedProjection(
                t.id,
                t.name,
                t.description,
                t.descriptionEng,
                t.level,
                t.field,
                t.faculty,
                t.status,
                count(app),
                (count(t.student.id) = 0),
                p,
                :studentId = app.id,
                t.student.id
                )
                from TopicEntity t
                    left join t.promoter p
                    left join t.tags tags
                    left join t.applications app
                where ( :#{#q.term} is null         or t.term = :#{#q.term})
                and  ( :#{#q.faculty} is null       or t.faculty = :#{#q.faculty})
                and  ( :#{#q.field} is null         or t.field = :#{#q.field})
                and  ( :#{#q.level} is null         or t.level = :#{#q.level})
                and  ( :#{#q.topicStatus} is null   or t.status = :#{#q.topicStatus})
                and  ( :#{#q.promoter} is null      or p.firstName like :#{#q.promoter}%  or p.lastName like :#{#q.promoter}% )
                and  ( :#{#q.promoterId} is null    or p.id = :#{#q.promoterId} )
                and  ( :#{#q.tag} is null           or :#{#q.tag} in tags )
                and  ( :#{#q.free} is null          or (:#{#q.free} = true and t.student is null) or (:#{#q.free} = false and t.student is not null)  )
                and  ( :#{#q.studentId} is null     or t.student.id = :#{#q.studentId} or app.student.id = :#{#q.studentId})
                group by t
            """)
    Page<TopicIndexExtendedProjection> findAllIndexWithQueryPerStudent(TopicSearchQuery q, int studentId, Pageable pageable);

}

