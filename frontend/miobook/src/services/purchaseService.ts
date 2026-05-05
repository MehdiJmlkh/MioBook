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
}

export default new PurchaseService();
