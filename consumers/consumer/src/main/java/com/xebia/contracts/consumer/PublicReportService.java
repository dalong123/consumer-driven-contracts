package com.xebia.contracts.consumer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public interface PublicReportService {
    @Nonnegative long Request(@Nonnegative long timestamp);
}
