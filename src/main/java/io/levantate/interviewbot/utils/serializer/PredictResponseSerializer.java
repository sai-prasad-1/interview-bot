package io.levantate.interviewbot.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.cloud.aiplatform.v1beta1.PredictResponse;
import java.io.IOException;

public class PredictResponseSerializer extends JsonSerializer<PredictResponse> {

    @Override
    public void serialize(PredictResponse predictResponse, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        // Serialize the fields you want to include in the response
        // For example:
        jsonGenerator.writeFieldName("predictions");
        jsonGenerator.writeObject(predictResponse.getPredictionsList());

        jsonGenerator.writeEndObject();
    }
}
