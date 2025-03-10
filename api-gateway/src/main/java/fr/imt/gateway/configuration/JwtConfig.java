package fr.imt.gateway.configuration;

import fr.imt.gateway.configuration.exceptions.NoJwtSecretFilePathException;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class JwtConfig {

    @Value("${jwt.public.file:#{null}}")
    private String jwtSecretFilePath;

    @Bean
    public PublicKey jwtPublicKey() throws Exception {
        if (jwtSecretFilePath == null) {
            throw new NoJwtSecretFilePathException();
        }

        KeyFactory factory = KeyFactory.getInstance("RSA");

        Resource resource = new ClassPathResource(jwtSecretFilePath);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream());

        PemReader pemReader = new PemReader(reader);
        PemObject pemObject = pemReader.readPemObject();

        byte[] content = pemObject.getContent();

        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(content);
        return factory.generatePublic(publicKeySpec);
    }

}
