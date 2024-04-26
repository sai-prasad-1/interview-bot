package io.levantate.interviewbot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.aiplatform.v1beta1.EndpointName;
import com.google.cloud.aiplatform.v1beta1.PredictResponse;
import com.google.cloud.aiplatform.v1beta1.PredictionServiceClient;
import com.google.cloud.aiplatform.v1beta1.PredictionServiceSettings;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;

public class AIPredictionHelper {
        // Get a text prompt from a supported text model
        public static List<String> extractQuestions(PredictResponse predictResponse) {
                List<String> questions = new ArrayList<>();
                Value content = predictResponse.getPredictions(0).getStructValue().getFieldsOrThrow("content");
                String contentString = content.getStringValue();
                System.out.println(contentString);
                if (contentString != null && !contentString.isEmpty()) {
                    // Split the content string into individual questions
                    String[] questionArray = contentString.split("\\n");
                    for (String question : questionArray) {
                        // Remove leading digits and period
                        String cleanedQuestion = question.replaceFirst("^\\d+\\.\\s*", "");
                        questions.add(cleanedQuestion);
                    }
                }
                return questions;
            }
        public static List<String> predictTextPrompt(
                        String prompt
                       )
                        throws IOException {
                                String instance = "{ \"prompt\": "
                                + "\""+ prompt+"\"}";
                                String parameters = "{\n"
                                + "  \"temperature\": 0.2,\n"
                                + "  \"maxOutputTokens\": 256,\n"
                                + "  \"topP\": 0.95,\n"
                                + "  \"topK\": 40\n"
                                + "}";
                                String project = "miniproject-interviewchatbot";
                                String location = "us-central1";
                                String publisher = "google";
                                String model = "text-bison@001";
                String endpoint = String.format("%s-aiplatform.googleapis.com:443", location);
                 // Load credentials from the credentials file
       
        

                PredictionServiceSettings predictionServiceSettings = PredictionServiceSettings.newBuilder()
                                .setEndpoint(endpoint).build();

                // Initialize client that will be used to send requests. This client only needs
                // to be created
                // once, and can be reused for multiple requests.
                try (PredictionServiceClient predictionServiceClient = PredictionServiceClient
                                .create(predictionServiceSettings)) {
                        final EndpointName endpointName = EndpointName.ofProjectLocationPublisherModelName(project,
                                        location, publisher, model);

                        // Initialize client that will be used to send requests. This client only needs
                        // to be created
                        // once, and can be reused for multiple requests.
                        Value.Builder instanceValue = Value.newBuilder();
                        JsonFormat.parser().merge(instance, instanceValue);
                        List<Value> instances = new ArrayList<>();
                        instances.add(instanceValue.build());

                        // Use Value.Builder to convert instance to a dynamically typed value that can
                        // be
                        // processed by the service.
                        Value.Builder parameterValueBuilder = Value.newBuilder();
                        JsonFormat.parser().merge(parameters, parameterValueBuilder);
                        Value parameterValue = parameterValueBuilder.build();

                        PredictResponse predictResponse = predictionServiceClient.predict(endpointName, instances,
                                        parameterValue);
                        System.out.println("Predict Response");
                        List<String> convertedQuestions=  extractQuestions(predictResponse);
                        System.out.println(convertedQuestions);
                        return convertedQuestions;
                }
        }

}
