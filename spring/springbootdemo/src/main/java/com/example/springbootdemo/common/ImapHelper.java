package com.example.springbootdemo.common;

import com.example.springbootdemo.entity.Dto.ImapConfig;
import com.example.springbootdemo.entity.EmailInfoDto;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName MailUtil
 * @Description TODO
 * @Author Frank XA Zhang
 * @Date 2/2/2021 9:54 PM
 * @Version 1.0
 **/
@Component
public class ImapHelper {
    //    @Value("CalendarMail.from")
//    private String from;

    @Value("${ImapConfig.protocol}")
    private String protocol;
    @Value("${ImapConfig.host}")
    private String host;
    @Value("${ImapConfig.port}")
    private String port;
    @Value("${ImapConfig.enableSsl}")
    private String enableSsl;
    @Value("${ImapConfig.userName}")
    private String userName;
    @Value("${ImapConfig.pwd}")
    private String pwd;
    @Value("${ImapConfig.emailTile}")
    private String subjectKeyWord;
    private MimeMessage mimeMessage = null;
//
@Resource
ImapConfig _config;

    public List<EmailInfoDto> ReadMail() {
       System.out.println(_config);
        List<EmailInfoDto> dtoList = new ArrayList<EmailInfoDto>();
        String host ="imap.163.com";
        try {
            Properties prop = System.getProperties();
            prop.put("mail.store.protocol", "imap");
            //prop.put("mail.imap.host", "imap.exmail.qq.com");
            prop.put("mail.imap.host", host);
            prop.put("mail.imap.port", "143");
            prop.put("mail.smtp.starttls.enable", "false");
            //prop.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(prop, null);

//            URLName urln = new URLName("imap", "imap.exmail.qq.com", 993, null,
//                    RmdeskConfig.euser, RmdeskConfig.epassword);
//            Store store = session.getStore(urln);

            //store.connect();
            Store store = session.getStore("imaps");
            store.connect(host,RmdeskConfig.euser, RmdeskConfig.epassword);

            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱
            folder.open(Folder.READ_WRITE);
            //获取未读邮件
            Message[] messages = folder.getMessages(folder.getMessageCount() - folder.getUnreadMessageCount() + 1, folder.getMessageCount());


            parseMessage(messages, dtoList); //解析邮件
            //释放资源
            if (folder != null) folder.close(true);
            if (store != null) store.close();
            System.out.println("读取成功。。。。。。。。。。。。");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return dtoList;

    }

    //https://blog.csdn.net/u010407050/article/details/46876375
    //https://blog.csdn.net/zoufaxiang/article/details/50847965
    private void parseMessage(Message[] messages, List<EmailInfoDto> dtoList) throws MessagingException, IOException, Exception {
        if (messages == null || messages.length < 1)
            throw new MessagingException("未找到要解析的邮件!");

        // 解析所有邮件
        for (int i = 0, count = messages.length; i < count; i++) {
            MimeMessage msg = (MimeMessage) messages[i];

            EmailInfoDto dto = new EmailInfoDto();

            ParseEmail pmm = new ParseEmail(msg);

            String subject = pmm.getSubject();
            if (!subject.contains("电子发票")) {
                continue;
            }
            dto.setSubject(subject);
            dto.setSendDate(pmm.getSentDate());
            dto.setReplySign(pmm.getReplySign());
            dto.setIsNew(pmm.isNew());
            dto.setHasAttach(pmm.isContainAttach((Part) messages[i]));
            dto.setFrom(pmm.getFrom());
            dto.setMessageId(pmm.getMessageId());
            //   pmm.getMailContent((Part) messages[i]);
            if(pmm.isContainAttach(msg)) {
                pmm.setAttachPath("c:\\Users\\jack d li\\Documents\\temp");
                //保存文件，且返回文件名
                dto.setFilePathList(pmm.saveAttachMent((Part) messages[i]));
            }

            System.out.println("Message " + i + " sentdate: " + msg.getSentDate());

            msg.setFlag(Flags.Flag.SEEN, true);
            dto.setEmailCC(pmm.getEmailCC());

            dtoList.add(dto);
            // Log(msg);
        }
    }

    private void Log(Object msg) {
        System.out.println(msg.toString());
    }

}

@Data
class RmdeskConfig {
    public static String euser = "dayday119122@163.com";
    public static String epassword = "ETXOYZDEYQPJMQWK";

//    public static String euser = "jack@danggui.fun";
//    public static String epassword = "P@ss1234!!";
}



@SuppressWarnings("all")
class ParseEmail {

    private MimeMessage mimeMessage = null;
    private String saveAttachPath = ""; //附件下载后的存放目录
    private StringBuffer bodytext = new StringBuffer();//存放邮件内容
    private String dateformat = "yyyy-MM-dd HH:mm"; //默认的日前显示格式

    public ParseEmail(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    /**
     * 获得发件人的地址和姓名
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null)
            from = "";
        String personal = address[0].getPersonal();
        if (personal == null)
            personal = "";
        String fromaddr = personal + "<" + from + ">";
        return fromaddr;
    }

    public String getEmailCC() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
        String from = address[0].getAddress();
        if (from == null)
            from = "";
        String personal = address[0].getPersonal();
        if (personal == null)
            personal = "";
        String fromaddr = personal + "<" + from + ">";
        return fromaddr;
    }

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
     */
    public String getMailAddress(String type) throws Exception {
        String mailaddr = "";
        String addtype = type.toUpperCase();
        InternetAddress[] address = null;
        if (addtype.equals("TO") || addtype.equals("CC") || addtype.equals("BCC")) {
            if (addtype.equals("TO")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
            } else if (addtype.equals("CC")) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
            }
            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String email = address[i].getAddress();
                    if (email == null)
                        email = "";
                    else {
                        email = MimeUtility.decodeText(email);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null)
                        personal = "";
                    else {
                        personal = MimeUtility.decodeText(personal);
                    }
                    String compositeto = personal + "<" + email + ">";
                    mailaddr += "," + compositeto;
                }
                mailaddr = mailaddr.substring(1);
            }
        } else {
            throw new Exception("Error emailaddr type!");
        }
        return mailaddr;
    }

    /**
     * 获得邮件主题
     */
    public String getSubject() throws MessagingException {
        String subject = "";
        try {
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
            if (subject == null)
                subject = "";
        } catch (Exception exce) {
        }
        return subject;
    }

    /**
     * 获得邮件发送日期
     */
    public String getSentDate() throws Exception {
        Date sentdate = mimeMessage.getSentDate();
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        return format.format(sentdate);
    }

    /**
     * 获得邮件正文内容
     */
    public String getBodyText() {
        return bodytext.toString();
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     */
    public void getMailContent(Part part) throws Exception {
        String contenttype = part.getContentType();
        int nameindex = contenttype.indexOf("name");
        boolean conname = false;
        if (nameindex != -1)
            conname = true;
        System.out.println("CONTENTTYPE: " + contenttype);
        if (part.isMimeType("text/plain") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        } else {
        }
    }

    /**
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
     */
    public boolean getReplySign() throws MessagingException {
        boolean replysign = false;
        String needreply[] = mimeMessage
                .getHeader("Disposition-Notification-To");
        if (needreply != null) {
            replysign = true;
        }
        return replysign;
    }

    /**
     * 获得此邮件的Message-ID
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }

    /**
     * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】
     */
    public boolean isNew() throws MessagingException {
        boolean isnew = false;
        Flags flags = ((Message) mimeMessage).getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        System.out.println("flags's length: " + flag.length);
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isnew = true;
                System.out.println("seen Message.......");
                break;
            }
        }
        return isnew;
    }

    /**
     * 判断此邮件是否包含附件
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachflag = false;
        String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE))))
                    attachflag = true;
                else if (mpart.isMimeType("multipart/*")) {
                    attachflag = isContainAttach((Part) mpart);
                } else {
                    String contype = mpart.getContentType();
                    if (contype.toLowerCase().indexOf("application") != -1)
                        attachflag = true;
                    if (contype.toLowerCase().indexOf("name") != -1)
                        attachflag = true;
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            attachflag = isContainAttach((Part) part.getContent());
        }
        return attachflag;
    }

    public void GetAttachStream(Part part) {
        //AttachStream
        //fileName
    }

    /**
     * 【保存附件】
     */
    public List<String> saveAttachMent(Part part) throws Exception {
        List<String> filePathList = new ArrayList<String>();
        String fileName = "";
        String fullPath = "";
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                if ((disposition != null)
                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition
                        .equals(Part.INLINE)))) {
                    fileName = mpart.getFileName();
                    //utf-8也要解码
//                    if (fileName.toLowerCase().indexOf("gb2312") != -1) {
//                        fileName = MimeUtility.decodeText(fileName);
//                    }
                    fileName = MimeUtility.decodeText(fileName);
                    fullPath = saveFile(fileName, mpart.getInputStream());
                    filePathList.add(fullPath);
                } else if (mpart.isMimeType("multipart/*")) {
                    saveAttachMent(mpart);
                } else {
                    fileName = mpart.getFileName();
                    if ((fileName != null)
                            && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
                        fileName = MimeUtility.decodeText(fileName);
                        fullPath = saveFile(fileName, mpart.getInputStream());
                        filePathList.add(fullPath);
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttachMent((Part) part.getContent());
        }

        return filePathList;
    }

    /**
     * 【设置附件存放路径】
     */

    public void setAttachPath(String attachpath) {
        this.saveAttachPath = attachpath;
    }

    /**
     * 【设置日期显示格式】
     */
    public void setDateFormat(String format) throws Exception {
        this.dateformat = format;
    }

    /**
     * 【获得附件存放路径】
     */
    public String getAttachPath() {
        return saveAttachPath;
    }

    /**
     * 【真正的保存附件到指定目录里】
     */
    private String saveFile(String fileName, InputStream in) throws Exception {
        String fullPath = "";
        String osName = System.getProperty("os.name");
        String storedir = getAttachPath();
        String separator = "";
        if (osName == null)
            osName = "";
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storedir == null || storedir.equals(""))
                storedir = "c:\\tmp";
        } else {
            separator = "/";
            storedir = "/tmp";
        }
        File storefile = new File(storedir + separator + fileName);
        System.out.println("storefile's path: " + storefile.toString());
        fullPath = storefile.toString();
        // for(int i=0;storefile.exists();i++){
        // storefile = new File(storedir+separator+fileName+i);
        // }
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storefile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("文件保存失败!");
        } finally {
            bos.close();
            bis.close();
        }
        return fullPath;
    }

}
