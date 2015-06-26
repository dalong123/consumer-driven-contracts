package com.xebia.contracts.consumer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnegative;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class RestPublicReportService implements PublicReportService {
    private final String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public RestPublicReportService(String url) {
        baseUrl = url + "/public/report";
    }

    @Override
    public long Request(@Nonnegative long timestamp) {
        String url = String.format("%s/%d", baseUrl, timestamp);
        return restTemplate.getForObject(url, Response.class).getScans().length;
    }

    private static final class Response {
        private final Scan[] scans;

        @JsonCreator
        public Response(@JsonProperty("scan") Scan[] scans) {
            this.scans = scans;
        }

        public Scan[] getScans() {
            return scans;
        }
    }

    private static final class Scan {
        private final double overallScore;
        private final long timestamp;

        public Scan(@JsonProperty("overallScore") double overallScore,
                    @JsonProperty("timestamp") long timestamp) {
            this.overallScore = overallScore;
            this.timestamp = timestamp;
        }
    }
}