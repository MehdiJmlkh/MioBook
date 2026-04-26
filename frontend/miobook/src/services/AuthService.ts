import apiClient from "./api-client";

class AuthService {
  login<T>(loginRequest: T) {
    return apiClient.post("/auth/login", loginRequest);
  }
}

export default new AuthService();