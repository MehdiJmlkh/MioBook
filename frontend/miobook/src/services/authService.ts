import apiClient from "./apiClient";
import { AddUserRequest, User } from "./userService";

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

class AuthService {
  login(loginRequest: LoginRequest) {
    return apiClient
      .post<LoginResponse>("/auth/login", loginRequest)
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }

  logout() {
    return apiClient.post("/auth/logout").then((res) => res.data);
  }

  get() {
    return apiClient.get("/auth").then((res) => res.data);
  }
}

export default new AuthService();
