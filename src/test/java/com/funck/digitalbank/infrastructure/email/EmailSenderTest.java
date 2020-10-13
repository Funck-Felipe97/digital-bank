package com.funck.digitalbank.infrastructure.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class EmailSenderTest {

    @InjectMocks
    private EmailSenderDefault emailSender;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Deve enviar um email com as iformações corretas passadas por parâmetro")
    public void testSend() {
        // given
        var email = Email.builder().mensagem("Uma mensagem bonita").titulo("Hello").
                emitente("banco@gmail.com").destinatario("destinatario@gmail.com").build();

        // when
        emailSender.send(email);

        // then
        var simpleMailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        verify(javaMailSender).send(simpleMailMessageCaptor.capture());

        var emailEnviado = simpleMailMessageCaptor.getValue();

        assertAll("email", () -> {
            assertEquals("destinatario@gmail.com", emailEnviado.getTo()[0]);
            assertEquals("banco@gmail.com", emailEnviado.getFrom());
            assertEquals("Uma mensagem bonita", emailEnviado.getText());
            assertEquals("Hello", emailEnviado.getSubject());
        });
    }
}
