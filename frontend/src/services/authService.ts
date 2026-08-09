import apiClient from "./apiClient";
import { AddUserRequest, User } from "./userService";

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

interface GoogleAuthRequest {
  code: string;
  nonce: string;
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

  googleAuth(request: GoogleAuthRequest) {
    return apiClient
      .post("/auth/google", request)
      .then((res) => res.data)
      .catch((err) => {
        throw Error(err.response.data.error);
      });
  }

  logout() {
    return apiClient.post("/auth/logout").then((res) => res.data);
  }

  get() {
    return apiClient
      .get("/auth")
      .then((res) => res.data)
      .catch((err) => {
        throw new Error(err.response.error);
      });
  }
}

export default new AuthService();
