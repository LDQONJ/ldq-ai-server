package work.daqian.myai.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import work.daqian.myai.exception.BizIllegalException;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendSimpleMail(String to, String title, String htmlContent, String textContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom("ai@ldq.li", "LDQ's AI");
            helper.setTo(to);
            helper.setSubject(title);
            MimeMultipart multipart = new MimeMultipart("alternative");
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(textContent, "UTF-8", "plain");
            textPart.setHeader("Content-Transfer-Encoding", "quoted-printable");
            multipart.addBodyPart(textPart);
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=UTF-8");
            multipart.addBodyPart(htmlPart);
            mimeMessage.setContent(multipart);
            mimeMessage.saveChanges(); // 先生成完整默认头
            mimeMessage.removeHeader("MIME-Version"); // 彻底删掉带旧大小写的标头
            mimeMessage.addHeader("MIME-Version", "1.0"); // 重新添加全大写的标头
            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BizIllegalException("邮件发送失败");
        }
    }
}
