package fr.imt.auth_api.configuration;

import fr.imt.auth_api.configuration.exceptions.NoJwtSecretFilePathException;
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
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class JwtConfig {

    @Value("${jwt.private.file:#{null}}")
    private String jwtPrivateKeyFilePath;

    @Value("${jwt.public.file:#{null}}")
    private String jwtPublicKeyFilePath;

    @Bean
    public PrivateKey jwtPrivateKey() throws Exception {
        byte[] privateKeyContent = readFile(jwtPrivateKeyFilePath);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyContent);
        return factory.generatePrivate(privateKeySpec);
    }

    @Bean
    public PublicKey jwtPublicKey() throws Exception {
        byte[] publicKeyContent = readFile(jwtPublicKeyFilePath);
        KeyFactory factory = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyContent);
        return factory.generatePublic(publicKeySpec);
    }

    private byte[] readFile(String filePath) throws Exception {
        if (filePath == null) {
            throw new NoJwtSecretFilePathException();
        }

        Resource resource = new ClassPathResource(filePath);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream());

        PemReader pemReader = new PemReader(reader);
        PemObject pemObject = pemReader.readPemObject();

        return pemObject.getContent();
    }

}
