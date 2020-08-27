package drr.security.oa;

public interface OALoginService {

	OALoginResponse login(LoginRequest loginRequest) throws Exception;
}
