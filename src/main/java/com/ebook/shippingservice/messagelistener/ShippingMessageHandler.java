package com.ebook.shippingservice.messagelistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ebook.api.message.ShippingMessage;

@Component
public class ShippingMessageHandler {
	@Autowired
	private JavaMailSender javaMailSender;

	@JmsListener(destination = "shipping")
	public void receiveMessage(ShippingMessage shippingMsg) {
		System.out.println("Sending shipping request to vendor for orderId: <" + shippingMsg.getOrderId() + ">");
		try {
			sendEmail(shippingMsg);
		} catch (Exception e) {
			System.out.println("Error with sending email.");
		}
	}

	public void sendEmail(ShippingMessage shippingMsg) {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("surajsapkota1@gmail.com", "srosesuraj@yahoo.com");

		msg.setSubject("Ship Order Request!");
		msg.setText("Please ship following order in the given shipping address.\n\n" + "Order information:\n"
				+ "Order Id:" + shippingMsg.getOrderId() + "\n" + "SKU:" + shippingMsg.getSku() + "\n" + "Quantity:"
				+ shippingMsg.getQuantity() + "\n\n"

				+ "Shipping Details:\n" + shippingMsg.getRecipient_first_name() + " " + shippingMsg.getRecipient_last_name() + "\n"
				+ shippingMsg.getRecipient_address1() + "\n" + shippingMsg.getRecipient_city() + ", " + shippingMsg.getRecipient_state()
				+ ", " + shippingMsg.getRecipient_zip_code() + "\n" + "Phone: " + shippingMsg.getRecipient_phone_number() + "\n" + "Email: " + shippingMsg.getRecipient_email() + "\n\n"

				+ "Contact us if you have any questions about your order.\n\n Ebook"

		);

		javaMailSender.send(msg);

	}
}
