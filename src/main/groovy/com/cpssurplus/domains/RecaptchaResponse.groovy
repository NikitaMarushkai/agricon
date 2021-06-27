package com.cpssurplus.domains

import com.fasterxml.jackson.annotation.JsonProperty

class RecaptchaResponse {
    Boolean success;
    @JsonProperty("challenge_ts")
    Date challengeTs;
    String hostname;
    @JsonProperty("error_codes")
    List<String> errorCodes;
    Double score;
}
