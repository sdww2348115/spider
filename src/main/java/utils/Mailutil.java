package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by sdww on 2016/4/28.
 */
public class Mailutil {
    // 邮件发送协议
    private final static String PROTOCOL = "smtp";

    // SMTP邮件服务器
    private final static String HOST = "smtp.163.com";

    // SMTP邮件服务器默认端口
    private final static String PORT = "25";

    // 是否要求身份认证
    private final static String IS_AUTH = "true";

    // 是否启用调试模式（启用调试模式可打印客户端与服务器交互过程时一问一答的响应消息）
    private final static String IS_ENABLED_DEBUG_MOD = "true";

    // 发件人
    private static String from = "sdww8591@163.com";

    // 收件人
    private static String to = "wx2348115@sina.com";

    // 初始化连接邮件服务器的会话信息
    private static Properties props = null;

    static {
        props = new Properties();
        props.setProperty("mail.transport.protocol", PROTOCOL);
        props.setProperty("mail.smtp.host", HOST);
        props.setProperty("mail.smtp.port", PORT);
        props.setProperty("mail.smtp.auth", IS_AUTH);
        props.setProperty("mail.debug",IS_ENABLED_DEBUG_MOD);
    }

    public static void main(String[] args) throws Exception {
        // 发送文本邮件
        new Mailutil().sendTextEmail("test");
    }

    class MyAuthenticator extends Authenticator {
        private String username;
        private String passwd;
        public MyAuthenticator(String username, String passwd) {
            this.username = username;
            this.passwd = passwd;
        }
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, passwd);
        }
    }

    /**
     * 发送简单的文本邮件
     */
    public void sendTextEmail(String text) throws Exception {
        // 创建Session实例对象
        Session session = Session.getDefaultInstance(props, new MyAuthenticator("sdww8591@163.com", "wangxuan22"));

        // 创建MimeMessage实例对象
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(from));
        // 设置邮件主题
        message.setSubject("新教师招聘消息提醒");
        // 设置收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        // 设置发送时间
        message.setSentDate(new Date());
        // 设置纯文本内容为邮件正文
        message.setText(text);
        // 保存并生成最终的邮件内容
        message.saveChanges();

        // 获得Transport实例对象
        Transport transport = session.getTransport();
        // 打开连接
        transport.connect("sdww8591@163.com", "wangxuan22");
        // 将message对象传递给transport对象，将邮件发送出去
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭连接
        transport.close();
    }

}
