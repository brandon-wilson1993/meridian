package integration.com.meridian.data;

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
            	"lastName": "Name"
            }
            """);

    private final String body;

    TestDataServiceBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
