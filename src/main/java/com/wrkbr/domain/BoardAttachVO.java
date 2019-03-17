package com.wrkbr.domain;

import lombok.Data;

// When insert rows into attach table, use this class.
@Data
public class BoardAttachVO {

    private Long bno;
    private String uploadFolder;
    private String fileName;
    private String uuid;
    private boolean fileType;

}
