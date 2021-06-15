package com.cpssurplus.services

import com.cpssurplus.domains.forms.OrderForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class MailClient {

    private JavaMailSender mailSender
    private MailContentBuilder contentBuilder

    @Autowired
    MailClient(JavaMailSender mailSender, MailContentBuilder contentBuilder){
        this.mailSender = mailSender
        this.contentBuilder = contentBuilder
    }

    void sendOrderNotification(OrderForm orderForm) throws MailException {
        MimeMessagePreparator messagePreparator = {
            MimeMessageHelper messageHelper = new MimeMessageHelper(it)
            messageHelper.setFrom("info@agricon.uk")
            messageHelper.setTo("info@agricon.uk")
            messageHelper.setSubject("New inquiry for order #" + orderForm.orderId)
            String content = contentBuilder.buildNewOrderRequest(orderForm)
            messageHelper.setText(content, true)
        }

        mailSender.send(messagePreparator)
    }

    void sendCustomerOrderNotification(OrderForm orderForm) throws MailException, HttpClientErrorException {
        MimeMessagePreparator messagePreparator = {
            MimeMessageHelper messageHelper = new MimeMessageHelper(it)
            messageHelper.setFrom("info@agricon.uk")
            messageHelper.setTo(orderForm.email)
            messageHelper.setSubject("Agricon inquiry confirmation #" + orderForm.orderId)
            String content = contentBuilder.buildNewCustomerOrderConfirmation(orderForm)
            messageHelper.setText(content, true)
        }

        mailSender.send(messagePreparator)
    }
}
