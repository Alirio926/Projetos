package com.squallsoft.api.config;


import org.apache.catalina.Context;
import org.apache.catalina.webresources.ExtractingRoot;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedTomcatConfiguration {

  @Bean
  TomcatServletWebServerFactory tomcatFactory() {
    return new TomcatServletWebServerFactory() {

      @Override
      protected void postProcessContext(Context context) {
        context.setResources(new ExtractingRoot());
      }
    };
  }

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainerCustomizer() {
    return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {

      @Override
      public void customize(TomcatServletWebServerFactory container) {
        container.addContextCustomizers(
            new TomcatContextCustomizer() {
              @Override
              public void customize(Context cntxt) {
                cntxt.setReloadable(false);
              }
            });
      }
    };
  }
}