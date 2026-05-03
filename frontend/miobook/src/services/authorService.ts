import apiClient from "./ApiClient";

export interface Author {
  name: string;
  penName: string;
  nationality: string;
  born: string;
  died: string;
}

class AuthorService {
  getAuthors() {
    return apiClient.get("/authors").then((res) => res.data);
  }
}

export default new AuthorService();
