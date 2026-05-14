import apiClient from "./apiClient";

class CreditService {
  get(username: string) {
    return apiClient.get(`/credits/${username}`).then((res) => res.data);
  }

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
