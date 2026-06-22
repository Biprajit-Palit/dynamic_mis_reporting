import { useState } from "react";

export default function ResultTable({
  columns,
  data,
  onExportCSV,
  onExportPDF,
}) {
  const [csvLoading, setCsvLoading] = useState(false);
  const [pdfLoading, setPdfLoading] = useState(false);

  async function handleCSVExport() {
    try {
      setCsvLoading(true);
      await onExportCSV();
    } finally {
      setCsvLoading(false);
    }
  }

  async function handlePDFExport() {
    try {
      setPdfLoading(true);
      await onExportPDF();
    } finally {
      setPdfLoading(false);
    }
  }

  return (
    <div>
      {data.length > 0 && (
        <div className="mb-4 flex justify-end gap-3">
          <button
            type="button"
            disabled={csvLoading}
            onClick={handleCSVExport}
            className="rounded-xl bg-primary px-4 py-2 font-medium text-white transition hover:bg-primary-hover disabled:opacity-60"
          >
            {csvLoading ? "Exporting..." : "Export CSV"}
          </button>

          <button
            type="button"
            disabled={pdfLoading}
            onClick={handlePDFExport}
            className="rounded-xl border border-danger/30 bg-danger-soft px-4 py-2 font-medium text-danger transition hover:bg-danger/10 disabled:opacity-60"
          >
            {pdfLoading ? "Exporting..." : "Export PDF"}
          </button>
        </div>
      )}

      <div className="max-h-[90vh] overflow-auto rounded-2xl border border-border bg-surface shadow-card">
        <table className="w-full min-w-max text-left">
          <thead className="sticky top-0 z-10 border-b border-border bg-app">
            <tr>
              {columns.map((column) => (
                <th
                  key={column.column}
                  className="whitespace-nowrap px-6 py-4 text-xs font-semibold uppercase tracking-wider text-text-muted"
                >
                  {column.label}
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {data.map((row, index) => (
              <tr
                key={index}
                className="border-t border-border transition hover:bg-primary-soft/40"
              >
                {columns.map((column) => (
                  <td
                    key={column.column}
                    className="whitespace-nowrap px-6 py-4 text-sm text-text"
                  >
                    {row[column.column] ? String(row[column.column]) : "-"}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}
