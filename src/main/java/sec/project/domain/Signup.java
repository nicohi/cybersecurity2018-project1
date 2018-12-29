package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Signup extends AbstractPersistable<Long> {

    private String name;
    private String address;
	private String secret;

    public Signup() {
        super();
    }

    public Signup(String name, String address, String secret) {
        this();
        this.name = name;
        this.address = address;
        this.secret = secret;
    }

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSecret() {
		return secret;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
