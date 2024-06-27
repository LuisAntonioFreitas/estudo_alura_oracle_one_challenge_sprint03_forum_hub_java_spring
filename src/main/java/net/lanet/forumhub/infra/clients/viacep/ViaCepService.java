package net.lanet.forumhub.infra.clients.viacep;

import net.lanet.forumhub.infra.utilities.ConsumerApiUtil;
import net.lanet.forumhub.infra.utilities.ConvertsDataUtil;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class ViaCepService implements IViaCepService {
    private final String URL_BASE = "https://viacep.com.br";

    @Override
    public Optional<ViaCepDtoResponse> findCep(String cep) {
        String search = URLEncoder.encode(cep, StandardCharsets.UTF_8).trim();
        String json = ConsumerApiUtil.getData(URL_BASE.concat("/ws/%s/json".formatted(search)));

        if (!json.isEmpty() && !json.trim().equalsIgnoreCase("")) {
            ViaCepDtoResponse response =  ConvertsDataUtil.getDataJsonToClass(json, ViaCepDtoResponse.class);
            return Optional.of(response);
        }
        return Optional.empty();
    }
}
