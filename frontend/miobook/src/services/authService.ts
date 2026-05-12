import apiClient from "./ApiClient";

export interface LoginRequest {
  username: string;
  password: string;
}

class AuthService {
  login(loginRequest: LoginRequest) {
    return apiClient
      .post("/auth/login", loginRequest)
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }

  get() {
    return apiClient.get("/auth").then((res) => res.data);
  }
}

export default new AuthService();
