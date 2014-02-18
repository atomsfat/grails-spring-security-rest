package com.odobo.grails.plugin.springsecurity.rest

import grails.plugins.rest.client.RestResponse
import spock.lang.Specification

class ValidateEndpointSpec extends AbstractRestSpec {

    void "calling /api/validate with a valid token returns a JSON representation"() {
        given:
        RestResponse authResponse = sendCorrectCredentials()
        String token = authResponse.json.token

        when:
        def response = restBuilder.get("${baseUrl}/api/validate") {
            header 'X-Auth-Token', token
        }

        then:
        response.status == 200
        response.json.username == 'jimi'
        response.json.token
        response.json.roles.size() == 2
    }

    void "calling /api/validate with an invalid token returns 403"() {
        when:
        def response = restBuilder.get("${baseUrl}/api/validate") {
            header 'X-Auth-Token', 'something-else'
        }

        then:
        response.status == 403
    }

    void "calling /api/validate without token returns 400"() {
        when:
        def response = restBuilder.get("${baseUrl}/api/validate")

        then:
        response.status == 400
    }


}