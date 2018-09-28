package com.example.demo.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.example.demo.common.config.EmailConfig;
import com.example.demo.service.EmailService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private EmailConfig emailConfig;
	@Autowired
	private JavaMailSender mailSender;

	// 发送邮件的模板引擎
	@Autowired
	private FreeMarkerConfigurer configurer;

	@Override
	public void sendSimpleMail(String sendTo, String titel, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendAttachmentsMail(String sendTo, String titel, String content, List<Pair<String, File>> attachments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTemplateMail(String sendTo, String titel, Map<String, Object> content,
			List<Pair<String, File>> attachments) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendTemplateMail(Object params, String title, String templateName) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(emailConfig.getEmailFrom());
			helper.setTo(InternetAddress.parse(emailConfig.getEmailFrom()));// 发送给谁
			helper.setSubject("【" + title + "-" + LocalDate.now() + " " + LocalTime.now().withNano(0) + "】");// 邮件标题
			Map<String, Object> model = new HashMap<>();
			model.put("params", params);
			try {
				Template template = configurer.getConfiguration().getTemplate(templateName);
				try {
					String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
					helper.setText(text, true);
					mailSender.send(mimeMessage);
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
