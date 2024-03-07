package com.glimoveis.emailsending.services;

import com.glimoveis.emailsending.models.EmailModel;
import com.glimoveis.emailsending.models.StatusEmail;
import com.glimoveis.emailsending.repositories.EmailRepository;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private EmailRepository emailRepository;
    private JavaMailSender javaMailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender javaMailSender){
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

    public void envioBoasVindas(EmailModel emailModel, String nomeUsuario) {
        final String emailRemetente = "gabrieldamasceno.bad@gmail.com";
        final String assunto = "Obrigado por fazer cadastro em nosso site";
        final String conteudo = "Olá " + nomeUsuario + " ficamos felizes que você tenha feito cadastro em nosso site. Fique a vontade e aproveite as melhores casas de Franca!";

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailRemetente);
            message.setTo(emailModel.getEmailDestinatario());
            message.setSubject(assunto);
            message.setText(conteudo);
            javaMailSender.send(message);

            emailModel.setEmailRemetente(emailRemetente);
            emailModel.setAssunto(assunto);
            emailModel.setConteudo(conteudo);
            emailModel.setStatusEmail(StatusEmail.ENVIADO);
        }catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERRO);
        }finally {
            emailRepository.save(emailModel);
        }
    }
}
