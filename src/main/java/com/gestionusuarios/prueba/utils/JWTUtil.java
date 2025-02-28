package com.gestionusuarios.prueba.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${security.jwt.secret}")
    private String key;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    public String create(String id, String subject) {
        if (key == null || key.isEmpty()) {
            throw new IllegalStateException("JWT secret key is not set");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        if (ttlMillis > 0) {
            builder.setExpiration(new Date(nowMillis + ttlMillis));
        }

        return builder.compact();
    }

    public String getValue(String jwt) {
        return parseClaims(jwt).getSubject();
    }

    public String getKey(String jwt) {
        return parseClaims(jwt).getId();
    }

    private Claims parseClaims(String jwt) {
        if (key == null || key.isEmpty()) {
            throw new IllegalStateException("JWT secret key is not set");
        }

            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(jwt)
                    .getBody();
    }


}
