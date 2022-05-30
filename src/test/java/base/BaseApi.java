package base;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This Class contains all methods that get all data or information from API for testing
 * based on JSON files into the resources/api folder
 */
public class BaseApi {

    public static final String ENDPOINTS_JSON = "src/test/resources/api/endpoints/endpoints.json";
    public static final String UPDATE_BANK_TRANSACTION = "src/test/resources/api/requests/update_bank_transaction.json";

    /**
     * This method read JSON files without array/nested structure
     *
     * @return JSONObject
     */
    private JSONObject jsonFileToJsonObject(String file) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject dataObject = (JSONObject) obj;
            return dataObject;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method returns the value from a JSON file based on a key
     *
     * @param key (JSON file key)
     * @return String (key value form JSON file)
     */
    public String getEndpointByKey(String key) {
        return (String) jsonFileToJsonObject(ENDPOINTS_JSON).get(key);
    }

    /**
     * This method creates, update or delete bank transactions based on a JSON file filtering by key
     *
     * @param action
     * @return String
     */
    public String createRequestByJsonFile(String action) {
        if (action.equalsIgnoreCase("update")) {
            return jsonFileToJsonObject(UPDATE_BANK_TRANSACTION).toString();
        }
        return null;
    }
}
