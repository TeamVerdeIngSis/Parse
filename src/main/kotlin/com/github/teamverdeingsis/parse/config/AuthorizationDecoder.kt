package com.github.teamverdeingsis.parse.config

import com.nimbusds.jwt.JWTParser

class AuthorizationDecoder {

    companion object {
        fun decode(authorization: String): String {
            val token = authorization.removePrefix("Bearer ")
            val decodedJWT = JWTParser.parse(token)
            return decodedJWT.jwtClaimsSet.subject
        }
    }
}
