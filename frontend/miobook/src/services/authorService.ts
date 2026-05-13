import apiClient from "./ApiClient";

export interface Author {
  name: string;
  penName: string;
  nationality: string;
  born?: string;
  died?: string;
}

class AuthorService {
  getAuthors() {
    return apiClient.get("/authors").then((res) => res.data);
  }

  getAuthor(id: number) {
    return apiClient.get<Author>(`/authors/${id}`).then((res) => res.data);
  }

  addAuthor(author: Author) {
    return apiClient
      .post("/authors", { ...author, username: "admin1" })
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new AuthorService();
