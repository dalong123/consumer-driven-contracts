package com.xebia.contracts.consumer;

import au.com.dius.pact.consumer.ConsumerPactBuilder;
import au.com.dius.pact.consumer.ConsumerPactTest;
import au.com.dius.pact.model.PactFragment;
import org.apache.http.entity.ContentType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestPublicReportServiceContractTest extends ConsumerPactTest {
    @Override
    protected PactFragment createFragment(ConsumerPactBuilder.PactDslWithProvider builder) {
        return builder
            .given("A report with score 7.4 and timestamp 1435231207 and a report with score 5.6 and timestamp 1435317608")
            .uponReceiving("a request for report generation")
                .path("/api/public/report/1435231207")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body("{\"scan\": [{\"overallScore\": 7.4, \"timestamp\": 1435231207}, {\"overallScore\": 5.6, \"timestamp\": 1435317608}]}", ContentType.APPLICATION_JSON)
            .toFragment();
    }

    @Override
    protected String providerName() {
        return "Public Report Service";
    }

    @Override
    protected String consumerName() {
        return "Batch Report";
    }

    @Override
    protected void runTest(String url) {
        PublicReportService client = new RestPublicReportService(url);
        long amount = client.Request(1435231207);
        assertEquals("The returned amount should be exactly what we set in the spec", 2, amount);
    }
}
