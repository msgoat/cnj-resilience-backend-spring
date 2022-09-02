package group.msg.at.cloud.cloudtrain.adapter.rest.in;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;

/**
 * Adds a {@link CommonsRequestLoggingFilter} logging all inbound HTTP requests and outbound HTTP responses.
 */
@Configuration
public class RequestTracingFilterConfiguration {

    @Bean
    public FilterRegistrationBean<CommonsRequestLoggingFilter> requestLoggingFilter() {
        FilterRegistrationBean<CommonsRequestLoggingFilter> result = new FilterRegistrationBean<>();
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(false);
        result.setFilter(filter);
        result.addUrlPatterns("/api/*");
        return result;
    }
}
