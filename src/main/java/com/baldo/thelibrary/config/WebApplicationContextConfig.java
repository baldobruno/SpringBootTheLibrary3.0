package com.baldo.thelibrary.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.baldo.thelibrary")
public class WebApplicationContextConfig implements WebMvcConfigurer {
	
	private ApplicationContext applicationContext;
	
    public void setApplicationContext(final ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }
	// @Bean
	// public InternalResourceViewResolver getInternalResourceViewResolver()
	// {
	// InternalResourceViewResolver resolver = new
	// InternalResourceViewResolver();
	// resolver.setViewClass(JstlView.class);
	//
	// resolver.setPrefix("/WEB-INF/view/");
	// resolver.setSuffix(".html");
	//
	// return resolver;
	// }
	
	 @Bean
	    public SpringResourceTemplateResolver templateResolver(){
	        // SpringResourceTemplateResolver automatically integrates with Spring's own
	        // resource resolution infrastructure, which is highly recommended.
	        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
	        templateResolver.setApplicationContext(this.applicationContext);
	        templateResolver.setPrefix("/WEB-INF/view/");
	        templateResolver.setSuffix(".html");
	        // HTML is the default value, added here for the sake of clarity.
	        templateResolver.setTemplateMode(TemplateMode.HTML);
	        // Template cache is true by default. Set to false if you want
	        // templates to be automatically updated when modified.
	        templateResolver.setCacheable(true);
	        return templateResolver;
	    }

	    @Bean
	    public SpringTemplateEngine templateEngine(){
	        // SpringTemplateEngine automatically applies SpringStandardDialect and
	        // enables Spring's own MessageSource message resolution mechanisms.
	        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	        templateEngine.setTemplateResolver(templateResolver());
	        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
	        // speed up execution in most scenarios, but might be incompatible
	        // with specific cases when expressions in one template are reused
	        // across different data types, so this flag is "false" by default
	        // for safer backwards compatibility.
	        templateEngine.setEnableSpringELCompiler(true);
	        return templateEngine;
	    }
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}

	// @Override //senza questo metodo non posso abilitare i tiles
	// public void configureViewResolvers(ViewResolverRegistry registry)
	// {
	// TilesViewResolver viewResolver = new TilesViewResolver();
	// registry.viewResolver(viewResolver);
	// }

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
