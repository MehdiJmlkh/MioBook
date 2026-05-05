import apiClient from "./ApiClient";

class CartService {
  getCart(username: string) {
    return apiClient.get(`/carts/${username}`).then((res) => res.data);
  }
}

export default new CartService();
