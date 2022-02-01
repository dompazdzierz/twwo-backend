package pl.pwr.dissertation.logic.domain;

import lombok.Data;

@Data
public class FileWrapper {

    private byte[] content;
    private String mime;
    private String fileName;
    private Long size;

}
