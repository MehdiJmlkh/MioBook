import apiClient from "./ApiClient";

class UserService {
  create<T>(newUser: T) {
    return apiClient.post("/users", newUser);
  }
}

export default new UserService();
