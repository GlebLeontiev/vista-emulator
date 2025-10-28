package pet.project.vistaapiemulator.endpointws;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pet.project.vistaapiemulator.service.FuelCardService;

// JAXB-типы (пакеты замените на ваши, если отличаются)
import service.cards.tch.com.types.WSCard;
import service.cards.tch.com.types.WSPolicy;
import service.cards.tch.com.types.WSCardSummaryReq;
import service.cards.tch.com.types.WSLocationSearch;

// Массивы (пакеты замените на ваши, если отличаются)
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSTransactionArray;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSCardSummaryArray;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSLocationArray;
import service.cards.tch.com.types.arrays.com.tch.cards.model.WSPolicyDescriptionArray;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.namespace.QName;

@Endpoint
@Component
@RequiredArgsConstructor
public class CardManagementDomEndpoint {

    // Namespaces из вашего WSDL
    private static final String NS_SVC = "http://com.tch.cards.service";
    private static final String NS_TYPES = "http://com.tch.cards.service/types";
    private static final String NS_ARRAYS_MODEL = "http://com.tch.cards.service/types/arrays/com/tch/cards/model";

    // Пакеты JAXB (замените, если у вас другие)
    private static final String PACK_TYPES = "service.cards.tch.com.types";
    private static final String PACK_ARRAYS = "service.cards.tch.com.types.arrays.com.tch.cards.model";

    private static final JAXBContext JC_TYPES;
    private static final JAXBContext JC_ARRAYS;

    static {
        try {
            JC_TYPES = JAXBContext.newInstance(PACK_TYPES);
            JC_ARRAYS = JAXBContext.newInstance(PACK_ARRAYS);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to init JAXBContext", e);
        }
    }

    private final FuelCardService service;

    // ---------- login ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "login")
    @ResponsePayload
    public Source login(@RequestPayload Element request) throws Exception {
        String user = childText(request, "user");
        String password = childText(request, "password");
        String token = service.login(user, password);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "loginResponse");
        doc.appendChild(resp);

        Element result = elem(doc, NS_SVC, "result");
        result.setTextContent(token != null ? token : "qweerty123");
        resp.appendChild(result);

        return new DOMSource(doc);
    }

    // ---------- logout ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "logout")
    @ResponsePayload
    public Source logout(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");
        service.logout(clientId);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "logoutResponse");
        doc.appendChild(resp);
        return new DOMSource(doc);
    }

    // ---------- getCard ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "getCard")
    @ResponsePayload
    public Source getCard(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");
        String cardNumber = childText(request, "cardNumber");

        WSCard card = service.getCard(clientId, cardNumber);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "getCardResponse");
        doc.appendChild(resp);
        Element result = elem(doc, NS_SVC, "result");
        resp.appendChild(result);

        // <result><ns2:WSCard>...</ns2:WSCard></result>
        marshalAsChild(JC_TYPES, new QName(NS_TYPES, "WSCard"), WSCard.class, card, result);

        return new DOMSource(doc);
    }

    // ---------- setCard ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "setCard")
    @ResponsePayload
    public Source setCard(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");
        Element cardElem = childElement(request, "card"); // rpc параметр "card"
        WSCard card = unmarshalParam(JC_TYPES, cardElem, WSCard.class);
        service.setCard(clientId, card);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "setCardResponse");
        doc.appendChild(resp);
        return new DOMSource(doc);
    }

    // ---------- getTransactions ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "getTransactions")
    @ResponsePayload
    public Source getTransactions(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");
        String begStr = childText(request, "begDate");
        String endStr = childText(request, "endDate");

        XMLGregorianCalendar beg = toXmlCal(begStr);
        XMLGregorianCalendar end = toXmlCal(endStr);

        WSTransactionArray array = service.getTransactions(clientId, beg, end);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "getTransactionsResponse");
        doc.appendChild(resp);
        Element result = elem(doc, NS_SVC, "result");
        resp.appendChild(result);

        marshalAsChild(JC_ARRAYS, new QName(NS_ARRAYS_MODEL, "WSTransactionArray"),
                WSTransactionArray.class, array, result);

        return new DOMSource(doc);
    }

    // ---------- getCardSummaries ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "getCardSummaries")
    @ResponsePayload
    public Source getCardSummaries(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");
        Element reqElem = childElement(request, "request");
        WSCardSummaryReq req = unmarshalParam(JC_TYPES, reqElem, WSCardSummaryReq.class);

        WSCardSummaryArray array = service.getCardSummaries(clientId, req);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "getCardSummariesResponse");
        doc.appendChild(resp);
        Element result = elem(doc, NS_SVC, "result");
        resp.appendChild(result);

        marshalAsChild(JC_ARRAYS, new QName(NS_ARRAYS_MODEL, "WSCardSummaryArray"),
                WSCardSummaryArray.class, array, result);

        return new DOMSource(doc);
    }

    // ---------- getPolicy ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "getPolicy")
    @ResponsePayload
    public Source getPolicy(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");
        int policyNumber = Integer.parseInt(childText(request, "policyNumber"));

        WSPolicy policy = service.getPolicy(clientId, policyNumber);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "getPolicyResponse");
        doc.appendChild(resp);
        Element result = elem(doc, NS_SVC, "result");
        resp.appendChild(result);

        marshalAsChild(JC_TYPES, new QName(NS_TYPES, "WSPolicy"), WSPolicy.class, policy, result);

        return new DOMSource(doc);
    }

    // ---------- searchLocation ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "searchLocation")
    @ResponsePayload
    public Source searchLocation(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");
        Element searchElem = childElement(request, "search");
        WSLocationSearch search = unmarshalParam(JC_TYPES, searchElem, WSLocationSearch.class);

        WSLocationArray array = service.searchLocation(clientId, search);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "searchLocationResponse");
        doc.appendChild(resp);
        Element result = elem(doc, NS_SVC, "result");
        resp.appendChild(result);

        marshalAsChild(JC_ARRAYS, new QName(NS_ARRAYS_MODEL, "WSLocationArray"),
                WSLocationArray.class, array, result);

        return new DOMSource(doc);
    }

    // ---------- getPolicyDescriptions ----------
    @PayloadRoot(namespace = NS_SVC, localPart = "getPolicyDescriptions")
    @ResponsePayload
    public Source getPolicyDescriptions(@RequestPayload Element request) throws Exception {
        String clientId = childText(request, "clientId");

        WSPolicyDescriptionArray array = service.getPolicyDescriptions(clientId);

        Document doc = newDoc();
        Element resp = elem(doc, NS_SVC, "getPolicyDescriptionsResponse");
        doc.appendChild(resp);
        Element result = elem(doc, NS_SVC, "result");
        resp.appendChild(result);

        marshalAsChild(JC_ARRAYS, new QName(NS_ARRAYS_MODEL, "WSPolicyDescriptionArray"),
                WSPolicyDescriptionArray.class, array, result);

        return new DOMSource(doc);
    }

    // ----------------- helpers -----------------

    private static Document newDoc() throws ParserConfigurationException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setNamespaceAware(true);
        return f.newDocumentBuilder().newDocument();
    }

    private static Element elem(Document doc, String ns, String local) {
        return doc.createElementNS(ns, local);
    }

    private static String childText(Element parent, String localName) {
        Element e = childElement(parent, localName);
        return e != null ? e.getTextContent() : null;
    }

    private static Element childElement(Element parent, String localName) {
        for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                String ln = e.getLocalName() != null ? e.getLocalName() : e.getNodeName();
                if (localName.equals(ln)) return e;
            }
        }
        return null;
    }

    private static XMLGregorianCalendar toXmlCal(String text) throws Exception {
        return text == null || text.isBlank() ? null : DatatypeFactory.newInstance().newXMLGregorianCalendar(text);
    }

    private static <T> T unmarshalParam(JAXBContext ctx, Element element, Class<T> type) throws Exception {
        if (element == null) return null;
        Unmarshaller u = ctx.createUnmarshaller();
        // Работает даже если имя узла не совпадает с @XmlRootElement (rpc-обёртка)
        JAXBElement<T> jb = u.unmarshal(element, type);
        return jb.getValue();
    }

    private static <T> void marshalAsChild(JAXBContext ctx, QName qname, Class<T> type, T value, Element parent) throws Exception {
        if (value == null) return;
        Marshaller m = ctx.createMarshaller();
        m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        JAXBElement<T> jb = new JAXBElement<>(qname, type, value);
        // создаём временный документ, чтобы получить корневой элемент результата
        Document tmp = newDoc();
        m.marshal(jb, tmp);
        // Вставляем как поддерево
        parent.appendChild(parent.getOwnerDocument().importNode(tmp.getDocumentElement(), true));
    }
}
