import { BookStatus } from "../queries/useBookStatus";
import apiClient from "./ApiClient";

class PurchaseService {
  getAll(username: string) {
    return apiClient.get(`/purchases/${username}`).then((res) => res.data);
  }

  getPurchasedBooks(username: string) {
    return apiClient
      .get(`/purchases/${username}/books`)
      .then((res) => res.data);
  }

  getPurchasedBookStatus(username?: string, bookId?: number) {
    return apiClient
      .get<BookStatus>(`/purchases/${username}/books/${bookId}/status`)
      .then((res) => res.data);
  }
}

export default new PurchaseService();
