package com.example.springbootdemo.entity.Dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "imap-config")
@Data
public class ImapConfig {
   //@Value("ImapConfig.protocol")
private String protocol;
   // @Value("ImapConfig.host")
    private String host;
   // @Value("ImapConfig.port")
    private String port;
   // @Value("ImapConfig.enableSsl")
    private String enableSsl;
   // @Value("ImapConfig.userName")
    private String userName;
    //@Value("ImapConfig.pwd")
    private String pwd;
    //@Value("ImapConfig.emailTile")
    private String subjectKeyWord;
}