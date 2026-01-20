package integration.com.meridian.data;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public enum TestDataServiceBody {

    CREATE_ACCOUNT_CHECKING("""
            {
            	"accountType": "CHECKING"
            }
            """),
    CREATE_ACCOUNT_CREDIT("""
            {
            	"accountType": "CREDIT"
            }
            """),
    CREATE_ACCOUNT_TRADING("""
            {
            	"accountType": "TRADING"
            }
            """),
    CREATE_ACCOUNT_SAVINGS("""
            {
            	"accountType": "SAVINGS"
            }
            """),
    CREATE_USER("""
            {
            	"firstName": "Testing",
            	"lastName": "Name",
            	"username": "%s"
            }
            """);

    private final String body;

    TestDataServiceBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public String getBody(String stringToAdd) {
        return body.formatted(stringToAdd);
    }
}
