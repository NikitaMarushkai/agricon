package com.cpssurplus.services

import com.cpssurplus.domains.RecaptchaResponse
import com.cpssurplus.domains.forms.OrderForm
import groovy.json.JsonOutput
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class MailClient {

    private JavaMailSender mailSender
    private MailContentBuilder contentBuilder
    private RestTemplate restTemplate

    @Value('${recaptcha.site}')
    private String captchaUrl;
    @Value('${recaptcha.secret}')
    private String captchaSecret;

    private final Double TRUST_SCORE = 0.8;

    @Autowired
    MailClient(JavaMailSender mailSender, MailContentBuilder contentBuilder, RestTemplate restTemplate){
        this.mailSender = mailSender
        this.contentBuilder = contentBuilder
        this.restTemplate = restTemplate
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

    boolean checkRecaptcha(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
        HttpEntity<String> entity = new HttpEntity<>(headers)
        Map params = [secret: captchaSecret, response: token]
        String url = captchaUrl + '?' + params.collect { k, v -> "$k=$v"}.join('&')
        ResponseEntity<RecaptchaResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, RecaptchaResponse.class)
        return response.body.getScore() >= TRUST_SCORE
    }
}
