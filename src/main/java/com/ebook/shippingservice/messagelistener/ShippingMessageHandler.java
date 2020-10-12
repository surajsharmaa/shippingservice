package com.ebook.shippingservice.messagelistener;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.ebook.api.message.ShippingMessage;

@Component
public class ShippingMessageHandler {

  @JmsListener(destination = "shipping")
  public void receiveMessage(ShippingMessage email) {
    System.out.println("Received <" + email.getBody() + ">");
  }

}
