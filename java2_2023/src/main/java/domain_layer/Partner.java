package domain_layer;

public class Partner extends Client {
    private static final long serialVersionUID = 3494232104983623504L;

	Partner(int ID, String login, String firstName, String lastName, Address address, int phone) {
        super(ID, login, firstName, lastName, address, phone);
        this.accountType = 1;
    }
}
