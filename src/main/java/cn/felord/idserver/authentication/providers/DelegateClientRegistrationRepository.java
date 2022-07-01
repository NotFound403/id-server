package cn.felord.idserver.authentication.providers;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DelegateClientRegistrationRepository implements ClientRegistrationRepository {
    private Function<String, ClientRegistration> delegate;
    private final Map<String, ClientRegistration> clientRegistrationMap = new HashMap<>();


    public void setDelegate(Function<String, ClientRegistration> delegate) {
        this.delegate = delegate;
    }

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        if (clientRegistrationMap.containsKey(registrationId)) {
            return clientRegistrationMap.get(registrationId);
        }
        return delegate.apply(registrationId);
    }

    public Map<String, ClientRegistration> getClientRegistrationMap() {
        return clientRegistrationMap;
    }

    public void addClientRegistration(ClientRegistration clientRegistration) {
        clientRegistrationMap.putIfAbsent(clientRegistration.getRegistrationId(), clientRegistration);
    }

}
