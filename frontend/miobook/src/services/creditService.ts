import apiClient from "./ApiClient";

class CreditService {
  addCredit(credit: number) {
    return apiClient
      .post("/credits", { username: "li_wei", credit })
      .then((res) => res.data)
      .catch((err) => {
        throw err.response.data;
      });
  }
}

export default new CreditService();
