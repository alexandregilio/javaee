package br.com.teste.jsf.ejb;

import javax.ejb.EJB;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@EJB(name = "collaborateurFacadeLocal", beanInterface = UserFacade.class)
public class UserFacadeImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return new User(userName, null, null);
	}

}
