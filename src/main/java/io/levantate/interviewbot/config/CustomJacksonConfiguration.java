package io.levantate.interviewbot.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.cloud.aiplatform.v1beta1.PredictResponse;
import io.levantate.interviewbot.utils.serializer.PredictResponseSerializer;

@Configuration
public class CustomJacksonConfiguration {

    @Bean
    public SimpleModule customModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(PredictResponse.class, new PredictResponseSerializer());
        return module;
    }
}
