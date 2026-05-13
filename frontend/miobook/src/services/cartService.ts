import apiClient from "./ApiClient";

interface AddItemRequest {
  username?: string;
  title?: string;
}

interface AddBorrowedItemRequest {
  username?: string;
  title?: string;
  days: number;
}

class CartService {
  getCart(username: string) {
    return apiClient.get(`/carts/${username}`).then((res) => res.data);
  }

  addItem(request: AddItemRequest) {
    return apiClient
      .post("/carts/items", request)
      .then((res) => res.data)
      .catch((err) => {
        throw new Error(err.response.data.error);
      });
  }

  addBorrowedItem(request: AddBorrowedItemRequest) {
    return apiClient
      .post("/carts/borrowed-items", request)
      .then((res) => res.data)
      .catch((err) => {
        throw new Error(err.response.data.error);
      });
  }
}

export default new CartService();
