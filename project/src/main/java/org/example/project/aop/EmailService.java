package org.example.project.aop;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.database.config.DataSourceConfiguration;
import org.example.project.database.model.Product;
import org.example.project.database.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Service
@RequiredArgsConstructor
@Transactional
@EnableConfigurationProperties(EmailPropertiesConfiguration.class)
public class EmailService {

    private final EmailPropertiesConfiguration properties;
    private static int totalPrice = 0;

    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @Async
    public void sendEmail(String email, String shoppingCart){
        Map<Integer, Integer> cart = getMap(shoppingCart);

        String receipt = generateReceipt(cart);
        receipt = receipt.replaceAll("\n", "<br>");

        Properties prop = setProperties();

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getLogin(), properties.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(properties.getLogin()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Your receipt from Optical House");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(receipt, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private Map<Integer, Integer> getMap(String shoppingCart) {
        try {
            return objectMapper.readValue(shoppingCart, new TypeReference<Map<Integer, Integer>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse shopping cart data", e);
        }
    }

    private Properties setProperties(){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mail.ru");
        prop.put("mail.smtp.port", "587");
        return prop;
    }


    private String generateReceipt(Map<Integer, Integer> cart){
        List<List<String>> list = generateColumns(cart);

        int[] maxLengths = maxLengthColumns(list);

        StringBuilder sb = new StringBuilder();

        for (List<String> row : list) {
            for (int i = 0; i < row.size(); i++) {
                sb.append(String.format("%-" + (maxLengths[i] + 2) + "s", row.get(i)));
            }
            sb.append("\n");
        }

        sb.append("\nОбщая стоимость: ").append(totalPrice);

        return sb.toString();
    }
    private List<List<String>> generateColumns(Map<Integer, Integer> cart){
        List<List<String>> list = new ArrayList<>();

        for (int key: cart.keySet()){
            Product product = productRepository.findById(key);
            int quantity = cart.get(key);
            int price = quantity * product.getCost();

            totalPrice += price;

            List<String> columnData = new ArrayList<>();
            columnData.add(product.getName());
            columnData.add(String.valueOf(quantity));
            columnData.add(String.valueOf(price));

            list.add(columnData);
        }

        return list;
    }

    private int[] maxLengthColumns(List<List<String>> list){
        int[] maxLengths = new int[list.get(0).size()];

        for (List<String> row : list) {
            for (int i = 0; i < row.size(); i++) {
                if (row.get(i).length() > maxLengths[i]) {
                    maxLengths[i] = row.get(i).length();
                }
            }
        }

        return maxLengths;
    }
}