package fr.imt.auth_api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

@Configuration
@Slf4j
public class JwtConfig {

    @Value("${jwt.private.file:#{null}}")
    private String jwtSecretFilePath;

    @Value("classpath:certs/jwt_private.pem")
    private String privateKey;

    @Bean
    public PrivateKey jwtPrivateKey() throws Exception {
        if (jwtSecretFilePath == null && privateKey == null) {
            return null;
        }

        File file;

        if (jwtSecretFilePath == null) {
            file = ResourceUtils.getFile(privateKey);
        } else {
            file = new File(jwtSecretFilePath);
        }

        KeyFactory factory = KeyFactory.getInstance("RSA");

        try (FileReader keyReader = new FileReader(file);
             PemReader pemReader = new PemReader(keyReader)) {

            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
            return factory.generatePrivate(privKeySpec);
        }
    }

}
