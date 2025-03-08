package fr.imt.auth_api.configuration;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Configuration
public class JwtConfig {

    @Value("${jwt.private.file:#{null}}")
    private String jwtSecretFilePath;

    @Bean
    public PrivateKey jwtPrivateKey() throws Exception {
        if (jwtSecretFilePath == null) {
            return null;
        }

        KeyFactory factory = KeyFactory.getInstance("RSA");

        Resource resource = new ClassPathResource(jwtSecretFilePath);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream());

        PemReader pemReader = new PemReader(reader);
        PemObject pemObject = pemReader.readPemObject();

        byte[] content = pemObject.getContent();

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
        return factory.generatePrivate(privateKeySpec);
    }

}
