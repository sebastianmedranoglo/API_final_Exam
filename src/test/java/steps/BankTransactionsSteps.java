package steps;

import base.BaseApi;
import base.model.BankTransaction;
import base.services.BankTransactionsService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.assertj.core.api.Assertions.assertThat;

public class BankTransactionsSteps {

    @Steps
    BankTransactionsService bankTransactionsService;

    @Given("I get the response from the endpoint {string}")
    public void iGetTheResponseFromTheEndpoint(String endpoint) {
        bankTransactionsService.sendGetRequest(endpoint);
    }

    @Given("I get the response from the endpoint")
    public void iGetTheResponseFromTheEndpoint() {
        bankTransactionsService.sendGetRequest("https://628d251da3fd714fd03fffa2.mockapi.io/bank/api/v1/account");
    }

    @Given("I get the response from the endpoint file with key {string}")
    public void iGetTheResponseFromTheEndpointWithKey(String key) {
        bankTransactionsService.sendGetRequest(new BaseApi().getEndpointByKey(key));
    }

    @Given("I get the endpoint by resource {string}")
    public void iGetEndpointByResource(String key) {
        bankTransactionsService.sendRequestByGet(new BaseApi().getEndpointByKey(key));
    }


    @Then("I get the response code equals to {}")
    public void iGetTheResponseCodeEqualsTo(int responseCode) {
        restAssuredThat(response -> response.statusCode(responseCode));
    }


    @When("I create a new bank transaction using POST request body string {string}")
    public void iSendAPOSTQueryToCreateANewBankTransaction(String requestBody) {
        bankTransactionsService.sendPostQuery(requestBody);
    }

    @When("I create a new bank transaction using resources with key {string}")
    public void iSendTheResponseFromTheEndpointFileWithKey(String key) {
        bankTransactionsService.sendPostQueryWithKey("create");
    }

    @When("I DELETE a bank transaction by id")
    public void iDeleteBankTransactionByIdDataTable(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            assertThat(bankTransactionsService.sendDeleteQueryById(Integer.parseInt(columns.get("id"))).equals("200"));
        }
    }

    @Then("I DELETE the last bank transaction created")
    public void iDeleteTheLastBankTransactionCreated() {
        assertThat(bankTransactionsService.sendDeleteQueryById(bankTransactionsService.getLastCreatedBankAccount().getId()).equals("200"));
    }

    @Then("I UPDATE the bank transaction by id with information")
    public void iUpdateTheBankByIdWithInformation(DataTable dataTable) {
        BankTransaction bankBody = new BankTransaction();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            bankBody.setName(columns.get("name"));
            bankBody.setLastName(columns.get("lastName"));
            bankBody.setAccountNumber(Integer.parseInt(columns.get("accountNumber")));
            bankBody.setAmount(Double.parseDouble(columns.get("amount")));
            bankBody.setTransactionType(columns.get("transactionType"));
            bankBody.setEmail(columns.get("email"));
            bankBody.setActive(Boolean.parseBoolean(columns.get("active")));
            bankBody.setCountry(columns.get("country"));
            bankBody.setTelephone(columns.get("telephone"));
            bankBody.setId(Integer.parseInt(columns.get("id")));

            assertThat(bankTransactionsService.updateBankTransactionById(bankBody, Integer.parseInt(columns.get("id"))).equals("200"));
        }
    }

    @When("I DELETE all of them if exist")
    public void iDeleteAllOfThemIfExist(){
        bankTransactionsService.deleteAllBankAccounts();
    }

    @Then("I shouldn't see any bank account")
    public void iShouldNotSeeAnyBankAccount(){
        List<BankTransaction> bankAccounts = bankTransactionsService.getBankAccountListFromService();
        assertThat(bankAccounts.size()>=0);
    }

    @When("I POST new bank accounts with random data, avoiding duplicated email")
    public void iPostNewBankAccountsWithRandomDataAvoidingDuplicatedEmails(DataTable dataTable){
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        rows.forEach(columns->
                bankTransactionsService.sendPostQueryWithBody(
                        new BankTransaction(Integer.parseInt(columns.get("id")),columns.get("name"),columns.get("lastName")
                                ,Integer.parseInt(columns.get("accountNumber")),Double.parseDouble(columns.get("amount")),columns.get("transactionType")
                                ,bankTransactionsService.randomString()+columns.get("email"),Boolean.parseBoolean(columns.get("active")),columns.get("country")
                                ,columns.get("telephone"))));
    }

    @When("I DELETE duplicate email accounts")
    public void iDeleteDuplicateEmailAccounts(){
        bankTransactionsService.deleteDuplicateEmailAccounts();
    }

    @Then("I shouldn't see any duplicated email accounts")
    public void iShouldNotSeeAnyDuplicatedEmailAccounts(){
        assertThat(!bankTransactionsService.isDuplicatedEmailAccounts());
    }

    @When("I UPDATE account number {} and id {}")
    public void iUpdateAccountNumberWithId(int accountNumber, int id){
        List<BankTransaction> bankTransaction= bankTransactionsService.getBankAccountListFromService();
        bankTransaction.stream().filter(p->p.getId()==id).findFirst().orElse(null).setAccountNumber(accountNumber);
        bankTransactionsService.updateBankTransactionById(bankTransaction.stream().filter(p->p.getId()==id)
                .findFirst().orElse(null),id);
    }

    @Then ("I should see account number {} and id {}")
    public void iShouldSeeAccountNumber(int accountNumber, int id){
        List<BankTransaction> bankTransaction= bankTransactionsService.getBankAccountListFromService();
        assertThat( bankTransaction.stream().filter(p->p.getId()==id).findFirst().orElse(null).getAccountNumber()==accountNumber);
    }
}
