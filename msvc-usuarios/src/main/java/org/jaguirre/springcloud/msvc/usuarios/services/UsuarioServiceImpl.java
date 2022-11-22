package org.jaguirre.springcloud.msvc.usuarios.services;

import org.jaguirre.springcloud.msvc.usuarios.models.dto.PlaceSearch;
import org.jaguirre.springcloud.msvc.usuarios.models.dto.RespsonseSearch;
import org.jaguirre.springcloud.msvc.usuarios.models.dto.Result;
import org.jaguirre.springcloud.msvc.usuarios.models.entity.Usuario;
import org.jaguirre.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.host.baseurl}")
    private String apiHost;

    @Value("${api.host.header.key}")
    private String apiHeaderKey;


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return repository.porEmail(email);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }


    @Override
    public RespsonseSearch buscarApiExterna() {
        long startTime = System.currentTimeMillis();
        PlaceSearch place = buscarApi();
        RespsonseSearch respsonseSearch = new RespsonseSearch();

        respsonseSearch.setResponseCode(place.getResponseCode());
        respsonseSearch.setDescription(place.getDescription());
        respsonseSearch.setElapsedTime(System.currentTimeMillis() - startTime);

        Result result = new Result();
        result.setRegisterCount(place.getResult().getItems().size());
        respsonseSearch.setResult(result);

        return respsonseSearch;

    }


    private PlaceSearch buscarApi() {
        String encryptText = null;

        try {
            encryptText = encryptForDES("1-9", "ionix123456");
        } catch (Exception e) {
            e.printStackTrace();
        }

        restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                    throws IOException {
                request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                return execution.execute(request, body);
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.add("x-api-key", apiHeaderKey);
        HttpEntity<Object> entity=new HttpEntity<Object>(headers);

        ResponseEntity<PlaceSearch> response = restTemplate.exchange(apiHost + encryptText, HttpMethod.GET, entity,
                new ParameterizedTypeReference<PlaceSearch>() {});

        return response.getBody();
    }

    private static String encryptForDES(String souce, String key) throws InvalidKeyException,
            NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key1 = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key1, sr);
        byte encryptedData[] = cipher.doFinal(souce.getBytes("UTF-8"));
        String base64Str = new BASE64Encoder().encode(encryptedData);

        return base64Str;
    }
}
