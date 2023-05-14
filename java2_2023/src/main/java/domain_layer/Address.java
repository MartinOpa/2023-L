package domain_layer;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

//@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Address implements UserType<Address> {
	@Getter @Setter (AccessLevel.PUBLIC) String street;
	@Getter @Setter (AccessLevel.PUBLIC) String city;
	@Getter @Setter (AccessLevel.PUBLIC) String postalCode;
    
	public Address() {}
	
    public Address(String street, String city, String postalCode) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }
    
    public String getStreet() {
        return this.street;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public String getPostalCode() {
        return this.postalCode;
    }

	@Override
	public Class<Address> returnedClass() {
		return Address.class;
	}

	@Override
	public boolean equals(Address x, Address y) throws HibernateException {
		return ((x == y) || (x != null && y != null && x.equals(y)));
	}

	@Override
	public int hashCode(Address x) throws HibernateException {
		return x != null ? x.hashCode() : 0;
	}

	@Override
	public Address deepCopy(Address value) throws HibernateException {
		return value == null ? null : value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Address value) throws HibernateException {
		Object deepCopy = deepCopy(value);
		if (!(deepCopy instanceof Serializable)) {
			return (Serializable) deepCopy;
		}
		return null;
	}

	@Override
	public Address assemble(Serializable cached, Object owner) throws HibernateException {
		return deepCopy((Address) cached);
	}

	@Override
	public Address replace(Address original, Address target, Object owner) throws HibernateException {
		return deepCopy(original);
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Address value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
	    if (value == null) {
	        st.setNull(index, Types.VARCHAR);
	        st.setNull(index + 1, Types.VARCHAR);
	        st.setNull(index + 2, Types.VARCHAR);
	    } else {
	        Address address = (Address) value;
	        st.setString(index,address.getStreet());
	        st.setString(index+1,address.getCity());
	        st.setString(index+2,address.getPostalCode());
	    }
	}

	@Override
	public int getSqlType() {
		return Types.VARCHAR;
	}

	@Override
	public Address nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner)
			throws SQLException {
	    String street = rs.getString(0);

	    if (rs.wasNull())
	        return null;

	    String city = rs.getString(1);
	    String postalCode = rs.getString(2);

	    return new Address(street, city, postalCode);
	}

}
