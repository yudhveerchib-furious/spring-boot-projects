package com.example.GEmailReceiver.service;

import com.example.GEmailReceiver.entity.Email;
import jakarta.annotation.PostConstruct;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Service
public class GemailReceiverService {

    private List<Email> allEmails = new ArrayList<>();
    private int lastMessageCount = -1;
    private boolean intialFetch = false;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @PostConstruct
    public void initialMails() {
        fetchAllMails();
        intialFetch = true;
    }

    @Scheduled(fixedRate = 10000)
    public void newlyReceivedMails() {
       if(!intialFetch) {
           initialMails();
           return;
       }
        Store store = null;
        Folder inboxFolder = null;
       try {
           System.out.println("1. Starting mail fetch...");

           Properties props = new Properties();
           props.put("mail.store.protocol", "imap");
           props.put("mail.imap.host", "imap.gmail.com");
           props.put("mail.imap.port", "993");
           props.put("mail.imap.ssl.enable", "true");

           System.out.println("2. Properties created");

           Session session = Session.getInstance(props);

           System.out.println("3. Session created");

           store = session.getStore("imap");

           System.out.println("4. Store created");

           store.connect(
                   emailUsername,
                   emailPassword
           );

           System.out.println("5. Gmail connected!");

           inboxFolder = store.getFolder("INBOX");
           inboxFolder.open(Folder.READ_ONLY);

           System.out.println("7. Inbox opened");
           int currentMailsCount = inboxFolder.getMessageCount();
           if(currentMailsCount > lastMessageCount) {
               Message[] newMessages = inboxFolder.getMessages(lastMessageCount + 1,currentMailsCount);
               System.out.println("new mails count "+ newMessages);

               for (Message message : newMessages) {

                   Email email = new Email();

                   email.setFrom(
                           InternetAddress.toString(message.getFrom())
                   );

                   email.setSubject(message.getSubject());

                   email.setRevecivedDate(
                           message.getReceivedDate()
                   );

                   email.setContent(
                           getTextFromMessage(message)
                   );

                   allEmails.add(0,email);
               }
                lastMessageCount = currentMailsCount;
           } else {
               System.out.println("No new mails");
           }
           inboxFolder.close(false);
           store.close();
       }catch (Exception e) {
           System.out.println("Error in fetching mails" +  e);
       }

    }

    private void fetchAllMails() {
        Store store = null;
        Folder inboxFolder = null;


        try {
            System.out.println("1. Starting mail fetch...");

            Properties props = new Properties();
            props.put("mail.store.protocol", "imap");
            props.put("mail.imap.host", "imap.gmail.com");
            props.put("mail.imap.port", "993");
            props.put("mail.imap.ssl.enable", "true");

            System.out.println("2. Properties created");

            Session session = Session.getInstance(props);

            System.out.println("3. Session created");

            store = session.getStore("imap");

            System.out.println("4. Store created");

            store.connect(
                    emailUsername,
                    emailPassword
            );

            System.out.println("5. Gmail connected!");

            inboxFolder = store.getFolder("INBOX");

            System.out.println("6. Inbox found");

            inboxFolder.open(Folder.READ_ONLY);

            System.out.println("7. Inbox opened");

            int totalMessages = inboxFolder.getMessageCount();

            int start = Math.max(1, totalMessages - 19);

            Message[] allMails = inboxFolder.getMessages(start, totalMessages);

            for (Message message : allMails) {

                Email email = new Email();

                email.setFrom(
                        InternetAddress.toString(message.getFrom())
                );

                email.setSubject(message.getSubject());

                email.setRevecivedDate(
                        message.getReceivedDate()
                );

                email.setContent(
                        getTextFromMessage(message)
                );

                allEmails.add(email);
            }

            lastMessageCount = inboxFolder.getMessageCount();

            System.out.println("9. Emails loaded successfully!");

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {
                if (inboxFolder != null && inboxFolder.isOpen()) {
                    inboxFolder.close(false);
                }

                if (store != null && store.isConnected()) {
                    store.close();
                }

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
    public String getTextFromMessage(Message message) throws Exception {

        if (message.isMimeType("text/plain")) {

            return (String) message.getContent();

        } else if (message.isMimeType("multipart/*")) {

            MimeMultipart mimeMultipart =
                    (MimeMultipart) message.getContent();

            return getTextFromMimeMultiPart(mimeMultipart);
        }

        return "";
    }

    public String getTextFromMimeMultiPart(
            MimeMultipart mimeMultipart
    ) throws Exception {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < mimeMultipart.getCount(); i++) {

            BodyPart bodyPart =
                    mimeMultipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/plain")) {

                sb.append(bodyPart.getContent());
            }
        }

        return sb.toString();
    }

    public List<Email> getEmails() {
        return allEmails;
    }
}