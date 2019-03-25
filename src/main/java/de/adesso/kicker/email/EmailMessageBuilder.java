package de.adesso.kicker.email;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@RequiredArgsConstructor

class EmailMessageBuilder {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final ServletContext servletContext;
    private final TemplateEngine templateEngine;

    String build(Map<String, Object> attributes, String template) {
        var context = new WebContext(request, response, servletContext, LocaleContextHolder.getLocale(), attributes);
        return templateEngine.process(template, context);
    }
}
