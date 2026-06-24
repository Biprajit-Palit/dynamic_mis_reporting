import { useEffect } from "react";

import DynamicField from "./DynamicField";

function getParentFilterName(field) {
  if (field.depends_on) {
    return field.depends_on;
  }

  if (
    field.name === "course_id" &&
    field.options?.some((option) => option.parentId != null)
  ) {
    return "department_id";
  }

  return undefined;
}

export default function DynamicFilterForm({
  filters,
  setFilters,
  config,
  onSearch,
}) {
  useEffect(() => {
    setFilters((prev) => {
      let changed = false;
      const updated = { ...prev };

      config.input_filters.forEach((field) => {
        const parentKey = getParentFilterName(field);
        if (!parentKey || !updated[field.name]) {
          return;
        }

        const parentValue = updated[parentKey];
        if (!parentValue) {
          delete updated[field.name];
          changed = true;
          return;
        }

        const isValid = field.options?.some(
          (option) =>
            Number(option.parentId) === Number(parentValue) &&
            Number(option.value) === Number(updated[field.name]),
        );

        if (!isValid) {
          delete updated[field.name];
          changed = true;
        }
      });

      return changed ? updated : prev;
    });
  }, [config, setFilters]);

  function updateField(name, value) {
    setFilters((prev) => {
      const updated = { ...prev };

      if (value === "") {
        delete updated[name];
      } else {
        updated[name] = value;
      }

      config.input_filters.forEach((field) => {
        const parentKey = getParentFilterName(field);
        if (parentKey === name) {
          delete updated[field.name];
        }
      });

      return updated;
    });
  }

  return (
    <div className="mb-8 rounded-2xl border border-border bg-surface p-6 shadow-card">
      <h2 className="mb-6 text-xl font-semibold text-text">Filters</h2>

      <div className="grid grid-cols-1 gap-6 md:grid-cols-2 xl:grid-cols-3">
        {config.input_filters.map((field) => (
          <div key={field.name}>
            <label className="mb-2 block text-sm font-medium text-text">
              {field.label}
            </label>

            <DynamicField
              field={field}
              value={filters[field.name] || ""}
              filters={filters}
              onChange={updateField}
            />
          </div>
        ))}
      </div>

      <div className="mt-8 flex gap-4">
        <button
          type="button"
          onClick={() => {
            if (
              filters.from_date &&
              filters.to_date &&
              filters.from_date > filters.to_date
            ) {
              alert("From Date cannot be greater than To Date");
              return;
            } onSearch()
          }}
          className="rounded-xl bg-primary px-6 py-3 font-medium text-white transition hover:bg-primary-hover"
        >
          Search
        </button>

        <button
          type="button"
          onClick={() => setFilters({})}
          className="rounded-xl border border-border bg-surface px-6 py-3 font-medium text-text transition hover:bg-app"
        >
          Clear
        </button>
      </div>
    </div>
  );
}
