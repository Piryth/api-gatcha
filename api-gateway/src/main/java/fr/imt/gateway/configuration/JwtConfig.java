package fr.imt.gateway.configuration;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class JwtConfig {

    @Value("${jwt.public.file:#{null}}")
    private String jwtSecretFilePath;

    @Value("classpath:certs/jwt_public.pem")
    private String publicKey;

    @Bean
    public PublicKey jwtPublicKey() throws Exception {
        if (jwtSecretFilePath == null && publicKey == null) {
            return null;
        }

        File file;

        if (jwtSecretFilePath == null) {
            file = ResourceUtils.getFile(publicKey);
        } else {
            file = new File(jwtSecretFilePath);
        }

        KeyFactory factory = KeyFactory.getInstance("RSA");

        try (FileReader keyReader = new FileReader(file);
             PemReader pemReader = new PemReader(keyReader)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);
            return (RSAPublicKey) factory.generatePublic(pubKeySpec);
        }
    }

}
