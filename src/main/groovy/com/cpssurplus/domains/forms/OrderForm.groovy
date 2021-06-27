package com.cpssurplus.domains.forms

import com.cpssurplus.domains.enums.Countries

class OrderForm {
    Integer orderId
    String title
    String subject
    String firstName
    String lastName
    String email
    String phone
    String message
    String token

    String getName() {
        String.format("%s %s %s", title, firstName, lastName)
    }
}
