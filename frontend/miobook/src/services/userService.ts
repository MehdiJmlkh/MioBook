import apiClient from "./ApiClient";

export interface AddUserRequest {
  username: string;
  password: string;
  email: string;
  address: {
    country: string;
    city: string;
  };
  role: string;
}

export interface User {
  username: string;
  email: string;
  role: "CUSTOMER" | "ADMIN";
}

class UserService {
  create(newUser: AddUserRequest) {
    return apiClient
      .post("/users", newUser)
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new UserService();
