package com.notepad.NotepadHandsOn.suma;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    private MessageSource messageSource;
    
    public Controller(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Ragul";
    }

    @GetMapping("/hello-bean")
    public HelloBean helloBean() {
        return new HelloBean("Hello Ragul K");
    }

    @GetMapping("/welcome/{name}")
    public HelloBean welcomeBean(@PathVariable String name) {
        return new HelloBean(String.format("welcome , %s", name));
    }

    @GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized() {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale );
		
		//return "Hello World V2"; 
		
		//1:
		//2:
//		- Example: `en` - English (Good Morning)
//		- Example: `nl` - Dutch (Goedemorgen)
//		- Example: `fr` - French (Bonjour)
//		- Example: `de` - Deutsch (Guten Morgen)

	}
}
