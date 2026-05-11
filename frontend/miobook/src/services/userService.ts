import apiClient from "./ApiClient";

export interface User {
  username: string;
  password: string;
  email: string;
  address: {
    country: string;
    city: string;
  };
  role: string;
}

class UserService {
  create(newUser: User) {
    return apiClient
      .post("/users", newUser)
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new UserService();
