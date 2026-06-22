import { api } from "./api";

export async function searchStudents(filters) {
  console.log(filters);

  const response = await api.post("/students/search", filters);

  return response.data;
}

export async function exportStudentCSV(filters) {
  const response = await api.post("/students/export/csv", filters, {
    responseType: "blob",
  });

  const url = window.URL.createObjectURL(response.data);
  const link = document.createElement("a");

  link.href = url;
  link.download = "students.csv";
  link.click();

  window.URL.revokeObjectURL(url);
}

export async function exportStudentPDF(filters) {
  const response = await api.post("/students/export/pdf", filters, {
    responseType: "blob",
  });

  const url = window.URL.createObjectURL(response.data);
  const link = document.createElement("a");

  link.href = url;
  link.download = "students.pdf";
  link.click();

  window.URL.revokeObjectURL(url);
}
