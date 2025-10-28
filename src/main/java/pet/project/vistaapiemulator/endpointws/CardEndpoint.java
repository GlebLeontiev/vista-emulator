package pet.project.vistaapiemulator.endpointws;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pet.project.vistaapiemulator.model.soap.dto.GetCardRequest;
import pet.project.vistaapiemulator.service.FuelCardService;
import service.cards.tch.com.types.WSCard;

@Endpoint
@RequiredArgsConstructor
public class CardEndpoint {

    private static final String NAMESPACE_URI = "http://com.tch.cards.service";

    private final FuelCardService fuelCardService;

//
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CardManagementEP_getCard")
//    @ResponsePayload
//    public WSCard getCard(@RequestPayload GetCardRequest request) {
//        // доступ к request.getClientId() и request.getCardNumber()
//        return null;
//    }

}
