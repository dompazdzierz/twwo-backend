package pl.pwr.dissertation.logic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ChangeVersion<T> {

    DefaultRevisionEntity revision;
    List<Change<T>> changeList;
}
