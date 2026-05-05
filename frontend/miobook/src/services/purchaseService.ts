import apiClient from "./ApiClient";

class PurchaseService {
  getAll(username: string) {
    return apiClient.get(`/purchases/${username}`).then((res) => res.data);
  }
}

export default new PurchaseService();
