import apiClient from "./apiClient";

export interface AddCreditRequest {
  username?: string;
  credit: number;
}

class CreditService {
  get(username?: string) {
    return apiClient.get(`/credits/${username}`).then((res) => res.data);
  }

  addCredit(request: AddCreditRequest) {
    return apiClient
      .post("/credits", {...request, credit: Math.round(request.credit * 100)})
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new CreditService();
