import { api } from "./api";

export async function getReport(id) {
  const response = await api.get(`/reports/${id}`);

  return response.data;
}
