export default function Pagination({
  page,
  totalPages,
  size,
  onPageChange,
  onSizeChange,
}) {
  function getPages() {
    const pages = [];

    if (totalPages <= 7) {
      for (let i = 0; i < totalPages; i++) {
        pages.push(i);
      }

      return pages;
    }

    pages.push(0);

    if (page <= 3) {
      pages.push(1, 2, 3, 4, "...", totalPages - 1);
      return pages;
    }

    if (page >= totalPages - 4) {
      pages.push("...");

      for (let i = totalPages - 5; i < totalPages; i++) {
        pages.push(i);
      }

      return pages;
    }

    pages.push("...", page - 1, page, page + 1, "...", totalPages - 1);

    return pages;
  }

  return (
    <div className="mt-6 flex flex-col items-center justify-between gap-4 rounded-xl border border-border bg-surface px-5 py-4 shadow-card sm:flex-row">
      <div className="flex items-center gap-2">
        <button
          type="button"
          disabled={page === 0}
          onClick={() => onPageChange(page - 1)}
          className="rounded-lg border border-border bg-surface px-4 py-2 text-sm font-medium text-text transition hover:bg-app disabled:cursor-not-allowed disabled:opacity-40"
        >
          Prev
        </button>

        <div className="flex gap-1">
          {getPages().map((num, index) =>
            num === "..." ? (
              <span
                key={`dots-${index}`}
                className="px-3 py-2 text-text-muted"
              >
                ...
              </span>
            ) : (
              <button
                key={`page-${num}`}
                type="button"
                onClick={() => onPageChange(num)}
                className={`h-9 w-9 rounded-lg text-sm font-semibold transition ${
                  page === num
                    ? "bg-primary text-white shadow-card"
                    : "border border-border bg-surface text-text hover:bg-app"
                }`}
              >
                {num + 1}
              </button>
            ),
          )}
        </div>

        <button
          type="button"
          disabled={page === totalPages - 1}
          onClick={() => onPageChange(page + 1)}
          className="rounded-lg border border-border bg-surface px-4 py-2 text-sm font-medium text-text transition hover:bg-app disabled:cursor-not-allowed disabled:opacity-40"
        >
          Next
        </button>
      </div>

      <div className="flex items-center gap-3 text-sm text-text-muted">
        <span>Rows:</span>

        <select
          name="pageSize"
          aria-label="Records per page"
          value={size}
          onChange={(e) => onSizeChange(Number(e.target.value))}
          className="cursor-pointer rounded-lg border border-border bg-input px-4 py-2 text-text outline-none focus:border-primary focus:ring-2 focus:ring-primary/20 [color-scheme:light]"
        >
          <option value={5}>5</option>
          <option value={10}>10</option>
          <option value={20}>20</option>
          <option value={50}>50</option>
        </select>
      </div>
    </div>
  );
}
