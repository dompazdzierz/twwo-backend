package pl.pwr.dissertation.logic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Change<T> {

    private String fieldName;
    private T oldValue;
    private T newValue;
    private Type type;


    public static enum Type {
        ADD, DELETE, MODIFY
    }

}
