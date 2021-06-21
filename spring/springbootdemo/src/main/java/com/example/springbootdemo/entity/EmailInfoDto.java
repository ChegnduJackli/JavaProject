package com.example.springbootdemo.entity;

import lombok.Data;

import java.io.InputStream;
import java.util.List;

@Data
public class EmailInfoDto {
    private String Subject;
    private String SendDate;
    private Boolean ReplySign;
    private String From;
    private String EmailCC;
    private String MessageId;
    private String Content;
    private String BodyText;
    private Boolean IsNew;
    private Boolean hasAttach;
    private InputStream AttachStream;
    private List<String> FilePathList;
}