package org.viroti.speechrecognition.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileReqPayload {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
