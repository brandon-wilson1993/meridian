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
    CREATE_ACCOUNT_DEBIT("""
            {
            	"accountType": "DEBIT"
            }
            """),
    CREATE_CARD_CREDIT("""
            {
            	"accountId": %s,
            	"cardNumber": "4532123456789012",
            	"cardType": "CREDIT"
            }
            """),
    CREATE_CARD_DEBIT("""
            {
            	"accountId": %s,
            	"cardNumber": "5532123456789012",
            	"cardType": "DEBIT"
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
