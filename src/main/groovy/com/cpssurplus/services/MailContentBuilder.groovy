package com.cpssurplus.services

import com.cpssurplus.domains.enums.Countries
import com.cpssurplus.domains.forms.OrderForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

/**
 * Created by unlim_000 on 24.02.2017.
 */

@Service
class MailContentBuilder {

    private TemplateEngine templateEngine

    @Autowired
    MailContentBuilder(TemplateEngine templateEngine){
        this.templateEngine = templateEngine
    }

    String buildNewOrderRequest(OrderForm orderForm) {
        Context context = new Context()
        context.setVariable("orderId", orderForm.orderId)
        context.setVariable("email", orderForm.email)
        context.setVariable("phone", orderForm.phone)
        context.setVariable("name", orderForm.name)
        context.setVariable("message", orderForm.message)
        return templateEngine.process("mail/newOrderMailTemplate", context)
    }

    String buildNewCustomerOrderConfirmation(OrderForm orderForm) throws HttpClientErrorException {
        Context context = new Context()
        context.setVariable("name", orderForm.name)
        context.setVariable("order", orderForm.orderId)
        return templateEngine.process("mail/customerOrderConfirmationTemplate", context)
    }
}
