package ca.nexapp

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty

class Configuration : io.dropwizard.Configuration() {

    @JsonProperty
    @NotEmpty
    lateinit var version: String
}