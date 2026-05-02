import apiClient from "./ApiClient";

class AuthService {
  login<T>(loginRequest: T) {
    return apiClient.post("/auth/login", loginRequest);
  }
}

export default new AuthService();
