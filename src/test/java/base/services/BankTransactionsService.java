package base.services;

import base.BaseApi;
import base.model.BankTransaction;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This Class contains all methods/actions needed by functions related to Bank transaction service
 */
public class  BankTransactionsService {

    /**
     * Logger by Log4j2 declaration and initialization
     */
    private static final Logger LOGGER = LogManager.getLogger(BankTransactionsService.class);

    /**
     * Constants definitions
     */
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String SUBSCRIPTION_CONTENT_TYPE = "application/json";

    /**
     * Local variables
     */
    private Response response;


    /**
     * This method send a GET request bases on an endpoint
     * Using get function from SerenityREST
     *
     * @param endpoint (String)
     */
    @Step("I get the endpoint by resource {string}")
    public void sendRequestByGet(String endpoint) {
        SerenityRest.get(endpoint);
    }

    /**
     * This method is used to send a GET request based on an endpoint
     *
     * @param endpoint (String)
     */
    @Step("I get the endpoint {string}")
    public void sendGetRequest(String endpoint) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .when()
                .get(endpoint);

        LOGGER.info("Send GET request --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }


    /**
     * This method send a POST query based on a body request
     *
     * @param bodyRequest (String map that should contain the body request)
     */
    @Step("I send a POST query to {string} with header {string} and body {string}")
    public void sendPostQuery(String bodyRequest) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(bodyRequest)
                .post(new BaseApi().getEndpointByKey("bankAccount_endpoint"));

        LOGGER.info("Send POST request --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method send a POST query based on a body from JSON resource
     *
     * @param endpoint
     *
     */
    @Step("I send a POST query using resource with key {key}")
    public void sendPostQueryWithKey(String endpoint) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(new BaseApi().createRequestByJsonFile("update"))
                .post(new BaseApi().getEndpointByKey(endpoint));

        LOGGER.info("Send POST Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method send a POST query based on a body as String
     *
     * @param body
     */
    @Step("I send a POST query using body")
    public void sendPostQueryWithBody(Object body) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(body)
                .post(new BaseApi().getEndpointByKey("bankAccount_endpoint"));

        LOGGER.info("Send POST Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
    }

    /**
     * This method DELETE a bank transaction by id returning the Response to compare the status code
     *
     * @param id
     * @return Response object to assert/compare response code
     */
    @Step("I send a DELETE query by id {int}")
    public Response sendDeleteQueryById(int id) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .when()
                .delete(new BaseApi().getEndpointByKey("bankAccount_endpoint") + "/" + id);

        LOGGER.info("Send DELETE Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());
        return response;
    }



    /**
     * This method returns the list of bank transactions from the main service with all contained elements
     *
     * @return List of bank transactions from class bank transaction
     */
    @Step("I get the list of bank transactions from service")
    public List<BankTransaction> getBankAccountListFromService() {
        return SerenityRest.lastResponse().jsonPath().getList(".", BankTransaction.class);
    }

    /**
     * This method returns the last bank transaction created with all content
     *
     * @return Last bank transaction created as bank transaction object
     */
    @Step("I Get last bank transaction from bank transaction list")
    public BankTransaction getLastCreatedBankAccount() {
        List<BankTransaction> bankTransactionListResponse = getBankAccountListFromService();
        return bankTransactionListResponse.get(bankTransactionListResponse.size() - 1);
    }

    /**
     * This method updates a Bank transaction by Id using body information
     *
     * @param body
     * @param id
     * @return Response Object - Response code
     */
    @Step("I UPDATE bank transaction by id using information")
    public Response updateBankTransactionById(Object body, int id) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(body)
                .put(new BaseApi().getEndpointByKey("bankAccount_endpoint") + "/" + id);

        LOGGER.info("Send UPDATE Query --- Time: " + response.getTime() + " -- Status code: " + response.getStatusCode() +
                " -- Session ID: " + response.getSessionId());

        return response;
    }
    /**
     * This method deletes all bank accounts
     *
     */
    @Step("I DELETE all bank accounts")
    public void deleteAllBankAccounts(){
        List<BankTransaction> bankAccounts = getBankAccountListFromService();
        bankAccounts.forEach(bankAccount->sendDeleteQueryById(bankAccount.getId()));
    }

    /**
     * This method generate random Strings
     * @return random String generated
     */
    public String  randomString(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * This method deletes duplicate email accounts
     *
     */
    public void deleteDuplicateEmailAccounts(){
        List<BankTransaction> bankTransactions = getBankAccountListFromService();
        List<BankTransaction> nonDuplicatedBankTransactions=bankTransactions.stream()
                .filter(distinctByKey(transaction->transaction.getEmail())).collect(Collectors.toList());
        bankTransactions.forEach(bankAccount->{
                BankTransaction transaction= nonDuplicatedBankTransactions.stream()
                                .filter(p->p.getId()==bankAccount.getId()).findAny().orElse(null);
                if(transaction==null){
                sendDeleteQueryById(bankAccount.getId());
                }
        });
    }

    /**
     * This method return if there are duplicate email accounts
     * @return true if there are duplicate email accounts
     */
    public boolean isDuplicatedEmailAccounts(){
        List<BankTransaction> bankTransactions = getBankAccountListFromService();
        List<BankTransaction> nonDuplicatedBankTransactions=bankTransactions.stream()
                .filter(distinctByKey(transaction->transaction.getEmail())).collect(Collectors.toList());
        return bankTransactions.size()>nonDuplicatedBankTransactions.size();
    }

    /**
     * This method is a stateful predicate for distinct object by keys
     */
    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
