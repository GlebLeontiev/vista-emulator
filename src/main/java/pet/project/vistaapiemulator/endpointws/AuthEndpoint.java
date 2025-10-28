package pet.project.vistaapiemulator.endpointws;

import jakarta.xml.bind.Element;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pet.project.vistaapiemulator.exceptions.UnauthorizedException;
import pet.project.vistaapiemulator.model.soap.dto.LoginRequest;
import pet.project.vistaapiemulator.model.soap.dto.LoginResponse;
import pet.project.vistaapiemulator.model.soap.dto.LogoutRequest;
import pet.project.vistaapiemulator.service.AuthService;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

@Endpoint
@RequiredArgsConstructor
public class AuthEndpoint {

    private static final String NAMESPACE_URI = "http://com.tch.cards.service";

    private final AuthService authService;

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "login")
//    @ResponsePayload
//    public Source login(Element request) {
//        System.out.println(request);
//        return new DOMSource();
//    }
//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CardManagementEP_logout")
//    @ResponsePayload
//    public void logout(@RequestPayload LogoutRequest request) {
//        if (request == null || request.getClientId() == null){
//            throw new UnauthorizedException();
//        }
//        authService.invalidateClientId(request.getClientId());
//    }


}
